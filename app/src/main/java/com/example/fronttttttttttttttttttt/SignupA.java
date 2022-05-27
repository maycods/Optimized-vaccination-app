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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Value;

import java.util.HashMap;

public class SignupA extends Activity {
    private Button sinscrire ;
    private ImageButton retourS;
    protected EditText nomprenom ,mail , tel  , code , ageN,hop;

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
        hop=(EditText)findViewById(R.id.hopA);
        sinscrire =(Button) findViewById(R.id.sinscrireA);
        retourS =(ImageButton) findViewById(R.id.retourS1);
        mAuth = FirebaseAuth.getInstance();
        db=  FirebaseFirestore.getInstance();




        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String nompre= nomprenom.getText().toString().trim();
                String Age= ageN.getText().toString().trim();
                String email= mail.getText().toString().trim();
                String tell= tel.getText().toString().trim();
                String codeA= code.getText().toString().trim();
                String NH = hop.getText().toString().trim();
                if(nompre.isEmpty()){
                    nomprenom.setError("ce champ est obligatoire");
                    nomprenom.requestFocus();
                    return;
                }
                if(NH.isEmpty()){
                    hop.setError("ce champ est obligatoire");
                    hop.requestFocus();
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
                db.collection("Hopital").whereEqualTo("NomH",NH).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                            String H = documentChange.getDocument().getId();
                            String nom = documentChange.getDocument().get("NomH").toString();
                            String nb = documentChange.getDocument().get("nbA").toString();
                            Log.d("jjj",nom+nb);
                            if(H.isEmpty()){
                                hop.setError("Inserer une adresse valide");
                                hop.requestFocus();
                                return;
                            }
                            else {
                                mAuth.createUserWithEmailAndPassword(email,codeA).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){
                                            HashMap<String, Object> AM = new HashMap<String, Object>();
                                            AM.put("Nom et Prenom", nomprenom.getText().toString().trim());
                                            AM.put("Email", mail.getText().toString().trim());
                                            AM.put("Mot de passe", code.getText().toString().trim());
                                            AM.put("Numero de Telephone", tel.getText().toString().trim());
                                            AM.put("Age", ageN.getText().toString().trim());
                                            AM.put("Hopital", hop.getText().toString().trim());
                                            AM.put("id",nom+nb);
                                            db.collection("Hopital").document(H).update("nbA",Integer.parseInt(nb)+1);
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection("Ambulancier").document(mAuth.getUid())
                                                    .set(AM)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(getApplicationContext(), "le compte a été crée ",
                                                                    Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignupA.this,adminmenu.class));
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("Fail", "Error", e);


                                                        }
                                                    });
                                        }
                                    }

                                });
                            }

                        }
                    }});



            }
        });


        retourS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupA.this, adminmenu.class));
            }
        });
    }}






