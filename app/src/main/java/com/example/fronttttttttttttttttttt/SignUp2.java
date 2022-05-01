package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ArrayAdapter<Integer> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);
       dose=findViewById(R.id.nbrdoses);
        typevac=findViewById(R.id.vactype);
        vacc=findViewById(R.id.vacciné);
        inscrit =findViewById(R.id.inscrit);
        retourS2 =findViewById(R.id.retourS2);


        Spinner spinner = findViewById(R.id.vactype);
        adapter= ArrayAdapter.createFromResource(this,
                R.array.vaccines, R.layout.spinn);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.nbrdoses);
        Integer[] items = new Integer[]{1,2,3};
       adapter2 = new ArrayAdapter<Integer>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, items);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        vacc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.oui://todo gerer les spinner les griser ou pas

                    case R.id.non:

                }
            }
        });
        inscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=  FirebaseFirestore.getInstance();
                String email= mail.getText().toString().trim();
                String tell= tel.getText().toString().trim();
                String mdp1= mdp.getText().toString().trim();
                String mdp2= mdpc.getText().toString().trim();
                String nompre= nomprenom.getText().toString().trim();

                if(tell.isEmpty()  ){
                    tel.setError("ce champ est obligatoire");
                }
                if(nompre.isEmpty()  ){
                  nomprenom.setError("ce champ est obligatoire");
                }
                if(mdp1.isEmpty()  ){
                    mdp.setError("ce champ est obligatoire");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    mail.setError("Fournissez une adresse mail valide");
                }
              /* if(!mdp2.equals(mdp1) ){
                    mdpc.setError("mot de passe ne match pas");
                }*/

                HashMap<String,Object> user=new HashMap<String,Object>();
                user.put("NomPrenom",nompre);
                user.put("telephone",tell);
                user.put("Email",email);
                user.put("Mot de passe ",mdp1);
                user.put("Nombre de doses:",spinnerChoiceD);
                user.put("type de vaccin",spinnerChoiceT);

                db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"jp",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUp2.this, Menu.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"lost",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        retourS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp2.this, SignUp.class));
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
