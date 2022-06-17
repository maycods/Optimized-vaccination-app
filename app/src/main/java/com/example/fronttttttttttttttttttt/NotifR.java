package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;


public class NotifR extends Activity {
    private ImageButton close ;
    private Button comfirmer,annuler;
    FirebaseFirestore db;
    TextView date;
    ListenerRegistration listenerReg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif);
        comfirmer=findViewById(R.id.btn_com);
        annuler=findViewById(R.id.can);
        close=(ImageButton)findViewById(R.id.close);
        db = FirebaseFirestore.getInstance();
        date=findViewById(R.id.textV2);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifR.this,Menu.class));
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();

        db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    Timestamp timestamp = (Timestamp) documentChange.getDocument().get("dateR");
                    date.setText(new SimpleDateFormat("yyyy-MM-dd").format(timestamp.toDate())) ;
                }
            }
        });
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifR.this,Menu.class));
                listenerReg=   db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                           String IDR =documentChange.getDocument().getId();
                            db.collection("Rendez-vous").document(IDR).update("confR",true);
                        }
                    }
                    });

            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifR.this,Menu.class));
               db.collection("user").document(currentId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        db.collection("user").document(currentId).update("Nombre de chance",Integer.parseInt(String.valueOf(documentSnapshot.get("Nombre de chance")))-1);
                    }
                });


               listenerReg= db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                            String IDR =documentChange.getDocument().getId();
                            db.collection("Rendez-vous").document(IDR).delete();

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
