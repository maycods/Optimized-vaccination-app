package com.example.fronttttttttttttttttttt;

import static com.google.android.gms.tasks.Tasks.await;
import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SignUp2 extends Activity implements AdapterView.OnItemSelectedListener {
    private Button inscrit ,verif;
    private ImageButton retourS2;
   private Spinner typevac, dose ;
    private RadioGroup vacc;
    private  FirebaseFirestore db;
    private String spinnerChoiceT=null;
    private  Integer spinnerChoiceD=0;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    int iii=0;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        String nompre = getIntent().getStringExtra("nom_prenom");
        String email = getIntent().getStringExtra("email");
        String tel = getIntent().getStringExtra("telephone");
        String mdp = getIntent().getStringExtra("mdp");
        String Age = getIntent().getStringExtra("Age");

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
        adapter= ArrayAdapter.createFromResource(this, R.array.vaccines, R.layout.spinn);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = findViewById(R.id.nbrdoses);
        spinner2.setEnabled(false);
        spinner2.setClickable(false);
        adapter2= ArrayAdapter.createFromResource(this,R.array.nombre, R.layout.spinn);
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
                                            Toast.makeText(view.getContext(), "l'email de confirmation a ete envoyé", Toast.LENGTH_LONG).show();

                                            inscrit.setVisibility(View.GONE);
                                            verif.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });





      }else {
             Toast.makeText(getApplicationContext(),"vous avez deja creer un compte avec cette adresse email",Toast.LENGTH_LONG).show();
          }
                    }

                });

            }
        });
        verif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(iii==0){ Toast.makeText(view.getContext(), "appuyer une 2eme fois pour confirmer que vous avez bien appuyé sur le lien l'email", Toast.LENGTH_LONG).show();mAuth.getCurrentUser().reload();iii++;}
                if(iii==1){
                    mAuth.getCurrentUser().reload();
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        HashMap<String, Object> user = new HashMap<String, Object>();
                        user.put("Nom et Prenom", nompre);
                        user.put("Email", email);
                        user.put("Mot de passe", mdp);
                        user.put("Numero de Telephone", tel);

                        user.put("Age", Age);
                        user.put("Nombre de doses", spinnerChoiceD);
                        user.put("Type de vaccin", spinnerChoiceT);
                        user.put("Nombre de chance", 3);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").document(mAuth.getUid())
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "le compte a été crée ",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp2.this,Menu.class));
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
        retourS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        inscrit.setVisibility(View.GONE);
        verif.setVisibility(View.VISIBLE);

        // etc.
    }

}
