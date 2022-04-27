package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Admin extends Activity {
    private Button loginad;
    private ImageButton retourad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginadmin);
        retourad =(ImageButton) findViewById(R.id.retourAd);
        loginad =(Button) findViewById(R.id.loginad);
        retourad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, MainActivity.class));
            }
        });
        loginad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this,AdminPage.class ));
            }
        });
    }
}
