package com.example.fronttttttttttttttttttt;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class adminmenu extends AppCompatActivity {

private Button amb,user,dose,affamb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);
        amb=findViewById(R.id.plusA);
        user=findViewById(R.id.plusU);
        dose=findViewById(R.id.doshp);
        affamb=findViewById(R.id.affamb);
amb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //  startActivity(new Intent(adminmenu.this,adminmenu.class ));
    }
});
dose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(adminmenu.this,AdminPage2.class ));
    }
});
 user.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        // startActivity(new Intent(adminmenu.this,adminmenu.class ));
     }
 });
    affamb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(adminmenu.this,AdminPage.class ));
        }
    });
        }
}