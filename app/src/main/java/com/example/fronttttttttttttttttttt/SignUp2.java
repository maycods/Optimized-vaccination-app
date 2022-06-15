package com.example.fronttttttttttttttttttt;


import static java.lang.Integer.parseInt;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;


public class SignUp2 extends Activity implements AdapterView.OnItemSelectedListener {
    private Button inscrit ,verif;
    private ImageButton retourS2;
   private Spinner typevac, dose ;
    private RadioGroup vacc;
    private  FirebaseFirestore db;
    private String spinnerChoiceT=null;
    private  Integer spinnerChoiceD=0;
    ArrayAdapter<CharSequence> adapter2;
    int iii=0,j=0;
    private FirebaseAuth mAuth;
    ArrayList<String> a=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String nompre = getIntent().getStringExtra("nom_prenom");
        String email = getIntent().getStringExtra("email");
        String tel = getIntent().getStringExtra("telephone");
        String mdp = getIntent().getStringExtra("mdp");
        String dnn = getIntent().getStringExtra("dn");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);
        dose=findViewById(R.id.nbrdoses);
        typevac=findViewById(R.id.vactype);
        vacc=findViewById(R.id.vacciné);
        inscrit =findViewById(R.id.inscrit);
        mAuth = FirebaseAuth.getInstance();
        retourS2 =findViewById(R.id.retourS2);
        db=  FirebaseFirestore.getInstance();
        verif=findViewById(R.id.verif);

        Spinner spinner = findViewById(R.id.vactype);
        spinner.setEnabled(false);
       spinner.setClickable(false);
       verif=findViewById(R.id.verif);
     db.collection("Vaccin").document("IDV").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult().exists() && task.getResult() != null){

                a.addAll ((ArrayList<String>) task.getResult().get("TypeVaccin"));

                }else {
                    Log.d("kooooooooo","l");
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinn, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = findViewById(R.id.nbrdoses);
        spinner2.setEnabled(false);
        spinner2.setClickable(false);

        adapter2= ArrayAdapter.createFromResource(this,R.array.nombre, R.layout.spinn);
        adapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

//
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
                           spinnerChoiceD = 0;
                           spinnerChoiceT = null;
                       }
                   }
            }
        });

        inscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser m = mAuth.getCurrentUser();

                                m.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(view.getContext(), "L'email de confirmation a été envoyé", Toast.LENGTH_LONG).show();
                                            inscrit.setVisibility(View.GONE);
                                            verif.setVisibility(View.VISIBLE);
                                            j++;
                                        }
                                    }
                                });
      }else {
             Toast.makeText(getApplicationContext(),"Un compte avec cette adresse email existe deja",Toast.LENGTH_LONG).show();
          }
                    }

                });

            }
        });
        verif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(iii==0){ Toast.makeText(view.getContext(), "Appuyer une 2eme fois pour confirmer que vous avez bien appuyé sur le lien l'email", Toast.LENGTH_LONG).show();mAuth.getCurrentUser().reload();iii++;}
                if(iii==1){
                    mAuth.getCurrentUser().reload();
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        HashMap<String, Object> user = new HashMap<String, Object>();
                        user.put("Nom et Prenom", nompre);
                        user.put("Email", email);
                        user.put("Mot de passe", mdp);
                        user.put("Numero de Telephone", tel);
                        user.put("Date de naissance", dnn);
                        user.put("Nombre de doses", spinnerChoiceD);
                        user.put("Type de vaccin", spinnerChoiceT);
                        user.put("Nombre de chance", 3);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").document(mAuth.getUid())
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(getApplicationContext(), "le compte a été crée avec succes",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp2.this,Menu.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Fail", "Error", e);
                                        Toast.makeText(getApplicationContext(), "Une erreur a eu lieu",
                                                Toast.LENGTH_SHORT).show();

                                }
                                });

                    }else{
                        Toast.makeText(getApplicationContext(), "Vous n'avez pas confirmer votre adresse l'email", Toast.LENGTH_LONG).show();
                    }
                }}
        });
        retourS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                if(j==1 ){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   user.delete();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("spner","ici");
        Spinner t1 =(Spinner) adapterView;
        Spinner t2 =(Spinner) adapterView;
        if (t1.getId()== R.id.nbrdoses) {
            spinnerChoiceD = parseInt((String) adapterView.getItemAtPosition(i));
        }if(t2.getId() == R.id.vactype) {
                spinnerChoiceT = (String) adapterView.getItemAtPosition(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    @Override
    public void  onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        inscrit.setVisibility(View.GONE);
        verif.setVisibility(View.VISIBLE);

    }

}
