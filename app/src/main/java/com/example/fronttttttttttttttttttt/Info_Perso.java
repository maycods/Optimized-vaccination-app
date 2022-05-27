package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Info_Perso extends Activity {
    private ImageButton retour;
    private Button confirmer;
    private EditText tel,mdp,dtt,lio;
    private  DocumentReference reference;
    String currentId;
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

       retour= (ImageButton)findViewById(R.id.retourPM);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tell= tel.getText().toString().trim();
                String mdpp= mdp.getText().toString().trim();

                            db.collection("user").document(currentId).update("Mot de passe",mdpp,"Numero de Telephone",tell).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"modifié avec success",Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"une erreur a eu lieu",Toast.LENGTH_LONG).show();
                                }
                            });}

        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Info_Perso.this,InfoPersoAvantModif.class));
            }
        });
}}
