package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends Activity {
    private Button suivant ;
    private ImageButton retourS;
   protected EditText nomprenom ,mail , tel , mdp , mdpc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup1);

        mail=(EditText)findViewById(R.id.mails);
        tel=(EditText)findViewById(R.id.tels);
        nomprenom=(EditText)findViewById(R.id.nomprenoms);
        mdp=(EditText)findViewById(R.id.mdps);
        mdpc=(EditText)findViewById(R.id.mdpcs);


        suivant =(Button) findViewById(R.id.suivant);
        retourS =(ImageButton) findViewById(R.id.retourS);


        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mail.getText().toString().trim();
                String tell= tel.getText().toString().trim();
                String mdp1= mdp.getText().toString().trim();
                String mdp2= mdpc.getText().toString().trim();
                String nompre= nomprenom.getText().toString().trim();



                if(nompre.isEmpty()  ){
                    nomprenom.setError("ce champ est obligatoire");
                    nomprenom.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    mail.setError("ce champ est obligatoire");
                    mail.requestFocus();
                    return;
                }
                else{
                      if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                          mail.setError("Fournissez une adresse mail valide");
                          mail.requestFocus();
                          return;
                      }
                }
                if(tell.isEmpty()  ){
                    tel.setError("ce champ est obligatoire");
                    tel.requestFocus();
                    return;
                } else{
                    if(!Patterns.PHONE.matcher(tell).matches()){
                        tel.setError("ce n'est pas un numero de telephone");
                        tel.requestFocus();
                        return;
                    }
                }
                if(mdp1.isEmpty()  ){
                    mdp.setError("ce champ est obligatoire");
                    mdp.requestFocus();
                    return;
                }
                if(mdp1.length()<6 ){
                    mdp.setError("ce champ est obligatoire");
                    mdp.requestFocus();
                    return;
                }
                if(!mdp2.equals(mdp1) ){
                    mdpc.setError("Le mot de passe ne matche pas");
                    mdpc.requestFocus();
                    return;
                }

                startActivity(new Intent(SignUp.this, SignUp2.class));
            }
        });
        retourS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }
        });
    }




}


