package com.example.fronttttttttttttttttttt;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Itineraire extends Activity {//TODO AFFICHER NOMBRE DE DOSES
    private Button annuler,itineraire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itineraire);
    annuler=(Button)findViewById(R.id.annuler);
    itineraire=(Button)findViewById(R.id.itin);
    annuler.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Itineraire.this,Ambulence.class));

        }
    });
        itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Itineraire.this, Map.class));
            }
        });
    }


}
