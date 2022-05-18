package com.example.fronttttttttttttttttttt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button login1;
    private ImageButton retourL;
    private EditText mail , mdp ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login1 =(Button) findViewById(R.id.login1);
        retourL =(ImageButton) findViewById(R.id.retourL);
        mail=(EditText)findViewById(R.id.emailL);
        mdp=(EditText)findViewById(R.id.PasswL);
        mAuth = FirebaseAuth.getInstance();
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mail.getText().toString().trim();
                String mdp1= mdp.getText().toString().trim();

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
                if(mdp1.isEmpty() ){
                    mdp.setError("ce champ est obligatoire");
                    mdp.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,mdp1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Login.this , "rakgna",Toast.LENGTH_LONG).show();
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this , "Vous ete connecte",Toast.LENGTH_LONG).show();
                            Intent i =new Intent(Login.this,Menu.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(Login.this , "Vous vous ete trompe d'email ou mot de passe . veuillez reessayer ",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        retourL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstP();
            }
        });

    }




    public void openFirstP(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
