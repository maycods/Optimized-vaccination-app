package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private Button loginad ,signup;
    private ImageButton retourad;
    private EditText mail , code ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginadmin);
        retourad =(ImageButton) findViewById(R.id.retourAd);
        loginad =(Button) findViewById(R.id.loginad);
        signup =(Button) findViewById(R.id.signupUU);
        mail=(EditText)findViewById(R.id.emailLU);
        code=(EditText)findViewById(R.id.codeLU);
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, SignupU.class));

            }
        });
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
                if(code1.isEmpty() ){
                    code.setError("ce champ est obligatoire");
                    code.requestFocus();
                    return;
                }
                Log.d("emil",String.valueOf(code1));
                Log.d("code",String.valueOf(email));
                mAuth.signInWithEmailAndPassword(email,code1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            DocumentReference reference;
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String currentId;
                            currentId=user.getUid();
                            reference=db.collection("Admin").document(currentId);
                            reference.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.getResult().exists()){
                                                Toast.makeText(Admin.this , "Vous ete connecte",Toast.LENGTH_LONG).show();
                                                Intent i =new Intent(Admin.this,AdminPage.class);
                                                startActivity(i);
                                            }else{
                                                Toast.makeText(getApplicationContext(),"ce n'est pas un compte d'un admin tgdeb",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(Admin.this , "Vous vous ete trompe d'email ou mot de passe . veuillez reessayer ",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }
}
