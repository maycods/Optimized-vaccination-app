package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUp2 extends SignUp implements AdapterView.OnItemSelectedListener /*, View.OnClickListener*/{
    private Button inscrit;
    private ImageButton retourS2;
   private Spinner typevac, dose ;
    private RadioGroup vacc;
    private  FirebaseFirestore db;
    private String spinnerChoiceT;
    private  Integer spinnerChoiceD;

    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String nom_prenom = getIntent().getStringExtra("nom_prenom");
        String email = getIntent().getStringExtra("email");
        String telephone = getIntent().getStringExtra("telephone");
        String mdp = getIntent().getStringExtra("mdp");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);
       dose=findViewById(R.id.nbrdoses);
        typevac=findViewById(R.id.vactype);
        vacc=findViewById(R.id.vacciné);
        inscrit =findViewById(R.id.inscrit);
        mAuth = FirebaseAuth.getInstance();
        retourS2 =findViewById(R.id.retourS2);
        db=  FirebaseFirestore.getInstance();

        Spinner spinner = findViewById(R.id.vactype);
        spinner.setEnabled(false);
       spinner.setClickable(false);
        adapter= ArrayAdapter.createFromResource(this,
                R.array.vaccines, R.layout.spinn);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner2 = findViewById(R.id.nbrdoses);
        spinner2.setEnabled(false);
        spinner2.setClickable(false);

        adapter2= ArrayAdapter.createFromResource(this,
                R.array.nombre, R.layout.spinn);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vacc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                   if(i== R.id.oui){
                       typevac.setEnabled(true);
                       dose.setEnabled(true);
                       typevac.setBackgroundColor(0x00000000);
                       dose.setBackgroundColor(0x00000000);
                       typevac.setBackgroundResource(R.drawable.bouton2);
                       dose.setBackgroundResource(R.drawable.bouton2);

                        spinner2.setAdapter(adapter2);
                        spinner2.setOnItemSelectedListener(SignUp2.this);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(SignUp2.this);
                }else{
                       if(i== R.id.non){
                           typevac.setEnabled(false);
                           typevac.setAdapter(null);
                          dose.setAdapter(null);
                           dose.setEnabled(false);
                           typevac.setBackgroundResource(R.drawable.bouton);
                           dose.setBackgroundResource(R.drawable.bouton);


                       }
                   }
            }
        });
        inscrit.setOnClickListener(new View.OnClickListener() {//TODO SIGN UP
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            HashMap<String, Object> user = new HashMap<String, Object>();
                            User U =new User();
                           String j =  Integer.toString(User.nbrU) ;


                            user.put("NomPrenom", nom_prenom);
                            user.put("telephone", telephone);
                            user.put("Email", email);
                            user.put("Mot de passe ", mdp);
                            user.put("Nombre de doses:",spinnerChoiceD);
                            user.put("type de vaccin",spinnerChoiceT);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("User").document(mAuth.getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "The user has been registered ",
                                                    Toast.LENGTH_SHORT).show();


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
        });
        retourS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.equals(adapter)) {
            spinnerChoiceD = (Integer) adapterView.getItemAtPosition(i);
        }/*else{if(adapterView.equals(adapter2)) {
                spinnerChoiceT = (String) adapterView.getItemAtPosition(i);
            }}
*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nomprenoms:
            case R.id.mails:
            case R.id.tels:
            case R.id.mdp:
            case R.id.mdps:
            case R.id.nbrdoses:
            case R.id.vactype:
        }
    }*/


}
