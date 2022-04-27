package com.example.fronttttttttttttttttttt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private Button login1;
    private ImageButton retourL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login1 =(Button) findViewById(R.id.login1);
        retourL =(ImageButton) findViewById(R.id.retourL);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
        retourL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstP();
            }
        });

    }
    public void openMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    public void openFirstP(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
