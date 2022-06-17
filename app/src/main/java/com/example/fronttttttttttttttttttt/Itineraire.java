package com.example.fronttttttttttttttttttt;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class    Itineraire extends FragmentActivity {

    private ImageButton L,r;
    private Button annuler,itineraire;
    RecyclerView recyclerView;
    DisplayMetrics displayMetric = new DisplayMetrics();
    public static  int SCROLL;
    AdapterV myAdapter;
    ArrayList<Vaccin> V ;
    FirebaseFirestore db;
    FirebaseUser user;
    int pos=0;
    String currentId;
    ArrayList<String> a=new ArrayList<String>();



    private FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment mapFragment;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        setContentView(R.layout.itineraire);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        annuler=(Button)findViewById(R.id.annuler);
        itineraire=(Button)findViewById(R.id.itin);
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);


        RecyclerView recyclerView=findViewById(R.id.recydose);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

         db = FirebaseFirestore.getInstance();
        user =FirebaseAuth.getInstance().getCurrentUser();
        currentId=user.getUid();

        V = new ArrayList<Vaccin>();
        myAdapter=new AdapterV(Itineraire.this ,V,db);
        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        annuler.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Itineraire.this,Ambulence.class));

        }
    });
        itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Itineraire.this, Map.class));
            }
        });
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=-1){
                    recyclerView.scrollToPosition(pos);
                    pos--;}
                return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=V.size()){
                    recyclerView.scrollToPosition(pos);
                    pos++;}
                return;
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    private void EventChangeListener(){

        db.collection("Vaccin").document("IDV").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists() && task.getResult() != null){
                    a.addAll ((ArrayList<String>) task.getResult().get("TypeVaccin"));

                    db.collection("Ambulancier").document(currentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (int i = 0; i < a.size(); i++) {
                                    db.collection("Rendez-vous").whereEqualTo("AMB", task.getResult().get("id"))
                                            /*.whereEqualTo("dateR", timestamp)*/.whereEqualTo("Type de vaccin", a.get(i)).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                            int nb=0;

                                            String typedeV=null;
                                            for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                                                com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) documentChange.getDocument().get("dateR");
                                                LocalDate aujourhui = LocalDate.now();
                                                LocalDate g  =LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(timestamp.toDate())) ;
                                                if(aujourhui.isEqual(g)){

                                                typedeV = documentChange.getDocument().get("Type de vaccin").toString();
                                                nb++;}
                                            }
                                            Log.d("vaccccc",String.valueOf(typedeV));
                                            if(typedeV!=null){
                                                Vaccin TV = new Vaccin(typedeV,nb);
                                                V.add(TV);
                                                myAdapter.notifyDataSetChanged();
                                            }


                                        }
                                    });

                                }
                            }
                        }
                    });
                }
            }
        });



    }


}
