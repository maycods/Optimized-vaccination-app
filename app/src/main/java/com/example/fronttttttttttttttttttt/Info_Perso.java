package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Info_Perso extends Activity {
    private ImageButton retour;
    private Button confirmer;
    private EditText tel,mdp,dtt,lio;
    private  DocumentReference reference;
    String currentId;
    List<Address> list ;
    boolean a =true;
    Date date = null;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infosperso);

        String telo = getIntent().getStringExtra("tele");
        String mdpo = getIntent().getStringExtra("mdp");
        String  endr = getIntent().getStringExtra("endroit");
        String dat = getIntent().getStringExtra("datee");

        dtt=findViewById(R.id.dtedit);
        lio=findViewById(R.id.lieuedit);
        tel=findViewById(R.id.teledit);
        mdp=findViewById(R.id.mdpedit);
        confirmer=findViewById(R.id.comfirmer);
        dtt.setText(dat);
        lio.setText(endr);
        tel.setText(telo);
        mdp.setText(mdpo);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentId=user.getUid();
        reference=db.collection("user").document(currentId);

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        LocalDate  aujourhui = LocalDate.parse(dateFormat.format(new Date()));

       retour= (ImageButton)findViewById(R.id.retourPM);
        if(dat== null) {
            lio.setHint("rendez-vous pas encore pris");
            dtt.setHint("rendez-vous pas encore pris");
            lio.setEnabled(false);
            dtt.setEnabled(false);
        }

        confirmer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String tell= tel.getText().toString().trim();
                String mdpp= mdp.getText().toString().trim();
                String loc= lio.getText().toString().trim();
                String dt= dtt.getText().toString().trim();

//
                if(tell.isEmpty()  ){
                    tel.setError("Champ obligatoire");
                    tel.requestFocus();
                    return;
                } else{
                    if(!Patterns.PHONE.matcher(tell).matches()){
                        tel.setError("Numero de telephone invalide");
                        tel.requestFocus();
                        return;
                    }
                }
                if(mdpp.isEmpty()  ){
                    mdp.setError("Champ obligatoire");
                    mdp.requestFocus();
                    return;
                }
                if(mdpp.length()<6 ){
                    mdp.setError("Mot de passe trop court");
                    mdp.requestFocus();
                    return;
                }
                //
                if(dat!=null){
                    try {
                        LocalDate.parse(dt);
                    } catch (DateTimeParseException dtpe) {
                        dtt.setError("Date invalide");
                        dtt.requestFocus();
                        return;
                    }}
                Geocoder geocoder=new Geocoder(getApplicationContext());
                try {
                    list = geocoder.getFromLocationName(loc, 1);
                    if(list.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Adresse invalide", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Adresse invalide", Toast.LENGTH_LONG).show();
                    return;
                }


                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(dt);
                } catch (ParseException e) { e.printStackTrace(); }

                if(aujourhui.isAfter(LocalDate.parse(dt)) || aujourhui.isEqual(LocalDate.parse(dt)) || aujourhui.plusDays(1).isEqual(LocalDate.parse(dt))){
                    Toast.makeText(getApplicationContext(), "Il est trop tard pour prendre rendez-vous à cette date", Toast.LENGTH_LONG).show();
                    return;
                }

                    db.collection("Rendez-vous").whereEqualTo("IDP", currentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                String idd = task.getResult().getDocuments().get(0).getId();
        //localisationn
                                    GeoPoint a = new GeoPoint(list.get(0).getLatitude(), list.get(0).getLongitude());
                                    db.collection("Rendez-vous").document(idd).update("Localisation", a);
        //dateee
                                   Timestamp timestamp = new Timestamp(date);
                                    db.collection("Rendez-vous").document(idd).update( "dateR", timestamp);

                                db.collection("user").document(currentId).update("Mot de passe",mdpp,"Numero de Telephone",tell).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        user.updatePassword(mdpp);
                                        Toast.makeText(getApplicationContext(), "Modifié avec success", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Une erreur a eu lieu",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Une erreur a eu lieu", Toast.LENGTH_LONG).show();
                            }
                        }
                    });





            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Info_Perso.this,InfoPersoAvantModif.class));
            }
        });
}}
