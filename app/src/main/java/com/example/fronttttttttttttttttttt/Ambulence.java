package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Ambulence extends Activity {
    private Button login;
    private ImageButton retouram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginambulancier);
        retouram =(ImageButton) findViewById(R.id.retourAm);
        login =(Button) findViewById(R.id.loginam);
        retouram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ambulence.this, MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ambulence.this, Itineraire.class));
            }
        });


    }
}
