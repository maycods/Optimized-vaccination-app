package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends Activity {
    private Button loginad ;
    private ImageButton retourad;
    private EditText mail , code ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginadmin);
        retourad =(ImageButton) findViewById(R.id.retourAd);
        loginad =(Button) findViewById(R.id.loginad);
        mail=(EditText)findViewById(R.id.emailLU);
        code=(EditText)findViewById(R.id.codeLU);
        mAuth = FirebaseAuth.getInstance();

        retourad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, MainActivity.class));
            }
        });
        loginad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mail.getText().toString().trim();
                String code1= code.getText().toString().trim();

                if(email.isEmpty()){
                    mail.setError("Ce champ est obligatoire");
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
                if(code1.isEmpty() ){
                    code.setError("Ce champ est obligatoire");
                    code.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,code1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            DocumentReference reference;
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String currentId;
                            currentId=user.getUid();
                        db.collection("Admin").document(currentId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.getResult().exists()){
                                                Toast.makeText(Admin.this , "Vous etes connecté(e)",Toast.LENGTH_LONG).show();
                                                Intent i =new Intent(Admin.this,adminmenu.class);
                                                startActivity(i);
                                            }else{
                                                Toast.makeText(getApplicationContext(),"Email ou mot de passe incorrecte(s). Veuillez reessayer",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(Admin.this , "Email ou mot de passe incorrecte(s). Veuillez reessayer",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }
}
