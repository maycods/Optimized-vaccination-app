package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.RDVV.fillRDV;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Value;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class SignupA extends Activity implements AdapterView.OnItemSelectedListener {
    private Button sinscrire ;
    private ImageButton retourS;
    protected EditText nomprenom ,mail , tel  , code , ageN;
    private Spinner SPH;
    private  FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String item;
    private HashMap<String, Object> AM = new HashMap<String, Object>();
    ArrayList<String> hopital = new ArrayList<>();
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
        SPH =(Spinner) findViewById(R.id.sphp);
        mAuth = FirebaseAuth.getInstance();
        db=  FirebaseFirestore.getInstance();



        db.collection("Hopital").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            hopital.add(dc.getDocument().getId());
                        }
                        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.spinn, hopital);
                        Adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        SPH.setAdapter(Adapter);
                    }
                });

        SPH.setOnItemSelectedListener(SignupA.this);
        sinscrire.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override

            public void onClick(View view) {
                String nompre= nomprenom.getText().toString().trim();
                String Age= ageN.getText().toString().trim();
                String email= mail.getText().toString().trim();
                String tell= tel.getText().toString().trim();
                String codeA= code.getText().toString().trim();

                if(nompre.isEmpty()){
                    nomprenom.setError("Champ obligatoire");
                    nomprenom.requestFocus();
                    return;
                }

                if(Age.isEmpty()  ){
                    ageN.setError("Champ obligatoire");
                    ageN.requestFocus();
                    return;
                }else{
                    try {
                        LocalDate.parse(Age);
                    } catch (DateTimeParseException dtpe) {
                        ageN.setError("Date invalide");
                        ageN.requestFocus();
                        return;
                    }

                }
                if(email.isEmpty()){
                    mail.setError("Champ obligatoire");
                    mail.requestFocus();
                    return;
                }
                else{
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        mail.setError("Adresse mail invalide");
                        mail.requestFocus();
                        return;
                    }
                }
                if(tell.isEmpty()  ){
                    tel.setError("Champ obligatoire");
                    tel.requestFocus();
                    return;
                } else{
                    if(!Patterns.PHONE.matcher(tell).matches()){
                        tel.setError("Numero de telephone invalide");
                        tel.requestFocus();
                        return;
                    }
                }
                if(codeA.isEmpty()  ){
                    code.setError("Champ obligatoire");
                    code.requestFocus();
                    return;
                }
                if(codeA.length()<6 ){
                    code.setError("Mot de passe trop court");
                    code.requestFocus();
                    return;
                }

            db.collection("Hopital").document(item).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){

                        String nomH=task.getResult().get("NomH").toString();
                        String nbA = task.getResult().get("nbA").toString();
                        Log.d("hna3",String.valueOf(nomH));
                        AM.put("Hopital",nomH);
                        AM.put("id",nomH+nbA);
            mAuth.createUserWithEmailAndPassword(email,codeA).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Log.d("hna4",String.valueOf(nomH));
                        AM.put("Nom et Prenom", nomprenom.getText().toString().trim());
                        AM.put("Email", mail.getText().toString().trim());
                        AM.put("Mot de passe", code.getText().toString().trim());
                        AM.put("Numero de Telephone", tel.getText().toString().trim());
                        AM.put("Date de Naissance", ageN.getText().toString().trim());
                        db.collection("Hopital").document(item).update("nbA",Integer.parseInt(nbA)+1);
                        Log.d("hna4",String.valueOf(AM.get("Email")));
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Ambulancier").document(mAuth.getUid())
                                .set(AM)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Le compte a été crée avec succes",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupA.this,adminmenu.class));
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
                        Toast.makeText(getApplicationContext(),"Un compte avec cette adresse email existe deja",Toast.LENGTH_LONG).show();
                    }
                }

            });
                    }
                }
            });





            }
        });
        retourS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupA.this, adminmenu.class));
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             item = (String) parent.getItemAtPosition(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}






