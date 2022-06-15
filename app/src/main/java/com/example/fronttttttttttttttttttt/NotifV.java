package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;


public class NotifV extends Activity {
    private ImageButton close ;
    private Button oui,non;
    FirebaseFirestore db;
    TextView date;
    ListenerRegistration listenerReg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_v);
        oui=findViewById(R.id.btn_oui);
        non=findViewById(R.id.btn_non);
        close=(ImageButton)findViewById(R.id.close);
        db = FirebaseFirestore.getInstance();
        date=findViewById(R.id.textV2);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifV.this,Menu.class));
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifV.this,Menu.class));

                listenerReg = db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                            String IDR =documentSnapshots.getDocumentChanges().get(0).getDocument().getId();
                            String dd = (String) documentSnapshots.getDocumentChanges().get(0).getDocument().get("AMB");
                            String  typ= documentSnapshots.getDocumentChanges().get(0).getDocument().get("Type de vaccin").toString();
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            db.collection("Ambulancier").whereEqualTo("id",dd).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                  String IDH = (String) value.getDocumentChanges().get(0).getDocument().get("Hopital");
                                    db.collection("Hopital").document(IDH).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                String td;
                                                switch (typ){
                                                    case "AstraZeneka":
                                                        td="DoseAstra";
                                                        break;
                                                    case "Sinovac-CoronaVac":
                                                        td="DoseSinovac";
                                                        break;
                                                    case "Sputnik V":
                                                        td="DoseSpootnik";
                                                        break;
                                                    case "Johnson & Johnson":
                                                        td="DoseJohnson";
                                                        break;
                                                    default: td="nop";
                                                }
                                                Long nbrr = (Long) task.getResult().get(td)-1;
                                                db.collection("Hopital").document(IDH).update(td,nbrr);
                                            }
                                        }
                                    });
                                }
                            });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            db.collection("user").document(currentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        int nbD = Integer.parseInt(task.getResult().get("Nombre de doses").toString());
                                        db.collection("user").document(currentId).update("Nombre de doses",nbD+1);

                                    }
                                }
                            });
                            db.collection("Rendez-vous").document(IDR).update("confV",true);
                            db.collection("Rendez-vous").document(IDR).update("comfJJ",false);
                            db.collection("Rendez-vous").document(IDR).delete();

                        }

                });

            }
        });
        non.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifV.this,Menu.class));
                db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                            String IDR =documentChange.getDocument().getId();
                            db.collection("Rendez-vous").document(IDR).update("comfJJ",false);

                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (listenerReg != null) {
            listenerReg.remove();
        }
    }


}

