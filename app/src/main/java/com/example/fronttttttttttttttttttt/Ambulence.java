//package com.example.fronttttttttttttttttttt;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class Ambulence extends Activity {
//    private Button login , signup;
//    private ImageButton retouram;
//    private EditText mail , code ;
//    private FirebaseAuth mAuth;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.loginambulancier);
//        retouram =(ImageButton) findViewById(R.id.retourAm);
//        login =(Button) findViewById(R.id.loginam);
//        signup =(Button) findViewById(R.id.signupAA);
//        mail=(EditText)findViewById(R.id.emailLA);
//        code=(EditText)findViewById(R.id.codeLA);
//        DocumentReference reference;
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//
//        retouram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Ambulence.this, MainActivity.class));
//            }
//        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email= mail.getText().toString().trim();
//                String codeA= code.getText().toString().trim();
//                Log.d("tagtag",String.valueOf(email));
//                if(email.isEmpty()){
//                    mail.setError("ce champ est obligatoire");
//                    mail.requestFocus();
//                    return;
//                }
//                else{
//                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                        mail.setError("Fournissez une adresse mail valide");
//                        mail.requestFocus();
//                        return;
//                    }
//                }
//                if(codeA.isEmpty() ){
//                    code.setError("ce champ est obligatoire");
//                    code.requestFocus();
//                    return;
//                }
//                //reference=db.collection("Ambulancier").document();
//              // boolean ee = (FirebaseDatabase.getInstance().getReference("ambulancier/"+email)==null);
//                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//                Query query = rootRef.child("ambulancier").orderByChild("Email").equalTo(""+email);
//
//                if(query==null){
//                    mail.setError("cet email n'existe pas");
//                    mail.requestFocus();
//                    return;
//                }
//                mAuth.signInWithEmailAndPassword("messaoudene.25.lydia.25@gmail.com","1111111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                           // Toast.makeText(a.this , "Vous ete connecte",Toast.LENGTH_LONG).show();
//                            Intent i =new Intent(Ambulence.this,Itineraire.class);
//                            startActivity(i);
//                        }
//                        else {
//                            Toast.makeText(Ambulence.this , "Vous vous ete trompe d'email ou mot de passe . veuillez reessayer ",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//
//            }
//
//        });
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Ambulence.this, SignupA.class));
//
//            }
//        });
//
//
//    }
//}
package com.example.fronttttttttttttttttttt;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Ambulence extends AppCompatActivity {
    private Button login1,signup;
    private ImageButton retourL;
    private EditText mail , mdp ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginambulancier);
        login1 =(Button) findViewById(R.id.loginam);
        retourL =(ImageButton) findViewById(R.id.retourAm);
        mail=(EditText)findViewById(R.id.emailLA);
        mdp=(EditText)findViewById(R.id.codeLA);
        signup =(Button) findViewById(R.id.signupAA);
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
                boolean e;


             /*   mAuth.signInWithEmailAndPassword(email,mdp1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            DocumentReference reference;
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                            String currentId;
                            currentId=user.getUid();
                            reference=db.collection("Ambulancier").document(currentId);
                            reference.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.getResult().exists()){
                                                Toast.makeText(Ambulence.this , "Vous ete connecte",Toast.LENGTH_LONG).show();*/
                                                Intent i =new Intent(Ambulence.this,Itineraire.class);
                                                startActivity(i);
                                         /*   }else{
                                                Toast.makeText(getApplicationContext(),"ce n'est pas un compte d'ambulancier",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(Ambulence.this , "Vous vous ete trompe d'email ou mot de passe . veuillez reessayer ",Toast.LENGTH_LONG).show();
                        }
                    }
                });*/


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ambulence.this, SignupA.class));

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
