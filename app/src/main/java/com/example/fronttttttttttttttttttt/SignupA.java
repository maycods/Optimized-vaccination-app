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
import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.NonParcelField;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Value;

import java.util.HashMap;

public class SignupA extends Activity {
    private Button sinscrire ,verif;
    private ImageButton retourS;
    protected EditText nomprenom ,mail , tel  , code , ageN;
    int iii=0;
    private  FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_ambulancier);
        mail=(EditText)findViewById(R.id.mailsA);
        tel=(EditText)findViewById(R.id.telsA);
        nomprenom=(EditText)findViewById(R.id.nomprenomsA);
        code=(EditText)findViewById(R.id.codeA);
        ageN=(EditText)findViewById(R.id.ageA);
        sinscrire =(Button) findViewById(R.id.sinscrireA);
        retourS =(ImageButton) findViewById(R.id.retourS1);
        mAuth = FirebaseAuth.getInstance();
        db=  FirebaseFirestore.getInstance();
        verif=findViewById(R.id.verif);



        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String nompre= nomprenom.getText().toString().trim();
                String Age= ageN.getText().toString().trim();
                String email= mail.getText().toString().trim();
                String tell= tel.getText().toString().trim();
                String codeA= code.getText().toString().trim();
  Log.d("email",String.valueOf(email));
                if(nompre.isEmpty()){
                    nomprenom.setError("ce champ est obligatoire");
                    nomprenom.requestFocus();
                    return;
                }
                if(Age.isEmpty()){
                    ageN.setError("ce champ est obligatoire");
                    ageN.requestFocus();
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
                if(codeA.isEmpty()  ){
                    code.setError("ce champ est obligatoire");
                    code.requestFocus();
                    return;
                }
                if(codeA.length()<6 ){
                    code.setError("le mot de passe est trop court");
                    code.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,codeA).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser m = mAuth.getCurrentUser();

                            m.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(view.getContext(), "l'email de confirmation a ete envoyé", Toast.LENGTH_LONG).show();

                                        sinscrire.setVisibility(View.GONE);
                                        verif.setVisibility(View.VISIBLE); }
                                }
                            });}
                        else { Toast.makeText(getApplicationContext(),"vous avez deja creer un compte avec cette adresse email",Toast.LENGTH_LONG).show(); }
                    }

                });

            }
        });
        verif.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iii==0){ Toast.makeText(view.getContext(), "appuyer une 2eme fois pour confirmer que vous avez bien appuyé sur le lien l'email", Toast.LENGTH_LONG).show();mAuth.getCurrentUser().reload();iii++;}
                if(iii==1){
                    mAuth.getCurrentUser().reload();
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        HashMap<String, Object> user = new HashMap<String, Object>();
                        user.put("Nom et Prenom", nomprenom.getText().toString().trim());
                        user.put("Email", mail.getText().toString().trim());
                        user.put("Mot de passe", code.getText().toString().trim());
                        user.put("Numero de Telephone", tel.getText().toString().trim());
                        user.put("Age", ageN.getText().toString().trim());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Ambulancier").document(mAuth.getUid())
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "le compte a été crée ",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupA.this,Itineraire.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Fail", "Error", e);


                                    }
                                });

                    }else{
                        Toast.makeText(getApplicationContext(), "Non vous navez pas verifié l email", Toast.LENGTH_LONG).show();
                    }
                }}
            });



//        retourS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             //   startActivity(new Intent(SignUp.this, MainActivity.class));
//            }
//        });
    }}







