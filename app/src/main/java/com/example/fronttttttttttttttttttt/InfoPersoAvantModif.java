package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InfoPersoAvantModif extends Activity {
private Button modifier,generer;
private ImageButton retourpp;
//TODO AFFICHER INFO  PERSO DE BDD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_perso_avant_modif);
    modifier =(Button) findViewById(R.id.modifier);
  //  generer=(Button) findViewById(R.id.generer);
    retourpp=(ImageButton)findViewById(R.id.retourP);
        retourpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Menu.class));
            }
        });
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Info_Perso.class));

            }
        });

    }
}
