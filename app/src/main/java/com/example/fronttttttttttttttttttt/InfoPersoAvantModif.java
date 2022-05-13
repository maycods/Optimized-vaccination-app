package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class InfoPersoAvantModif extends Activity {
private Button modifier,generer;
private ImageButton retourpp;
private TextView nom,mail,tel,dose,type,rdv,motdp;

//TODO AFFICHER INFO  PERSO DE BDD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_perso_avant_modif);

        Intent i =new Intent(InfoPersoAvantModif.this,Info_Perso.class);

        DocumentReference reference;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String currentId;
        nom=findViewById(R.id.nom);
        tel=findViewById(R.id.tel);
        mail=findViewById(R.id.mail);
        dose=findViewById(R.id.dos);
        type=findViewById(R.id.typ);
        rdv=findViewById(R.id.date);
        motdp=findViewById(R.id.mdpp);
        currentId=user.getUid();

        reference=db.collection("user").document(currentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            nom.setText(task.getResult().getString("Nom et Prenom"));
                            tel.setText(task.getResult().getString("Numero de Telephone"));
                            mail.setText(task.getResult().getString("Email"));
                            dose.setText(task.getResult().get("Nombre de doses",Integer.class).toString());
                            type.setText(task.getResult().getString("Type de vaccin"));
                            motdp.setText(task.getResult().getString("Mot de passe"));

                            i.putExtra("nomprenom" ,task.getResult().getString("Nom et Prenom"));
                            i.putExtra("email" ,task.getResult().getString("Email"));
                            i.putExtra("tele" ,task.getResult().getString("Numero de Telephone"));
                            i.putExtra("mdp" ,task.getResult().getString("Mot de passe"));

                        }else{
                            Toast.makeText(getApplicationContext(),"jcpo",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    modifier =(Button) findViewById(R.id.modifier);
  //  generer=(Button) findViewById(R.id.generer);
    retourpp=(ImageButton)findViewById(R.id.retourP);
        retourpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Menu.class));
            }
        });
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(i);

            }
        });

    }
}
