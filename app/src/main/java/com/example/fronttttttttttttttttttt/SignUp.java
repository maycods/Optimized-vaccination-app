package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends Activity {
    private Button suivant ;
    private ImageButton retourS;
   protected EditText nomprenom ,mail , tel , mdp , mdpc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup1);

        mail=(EditText)findViewById(R.id.mails);
        tel=(EditText)findViewById(R.id.tels);
        nomprenom=(EditText)findViewById(R.id.nomprenoms);
        mdp=(EditText)findViewById(R.id.mdps);
        mdpc=(EditText)findViewById(R.id.mdpcs);


        suivant =(Button) findViewById(R.id.suivant);
        retourS =(ImageButton) findViewById(R.id.retourS);


        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignUp2.class));
            }
        });
        retourS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }
        });
    }




}


