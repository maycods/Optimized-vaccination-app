package com.example.fronttttttttttttttttttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;

public class notifAdmin extends AppCompatActivity {
TextView nume;
Button okk;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_admin);
        nume=findViewById(R.id.num);
        okk=findViewById(R.id.ok);
        db = FirebaseFirestore.getInstance();
        String id = getIntent().getStringExtra("iduser");
        db.collection("user").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    nume.setText(String.valueOf(task.getResult().get("Numero de Telephone")));
                }
            }
        });
        okk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(notifAdmin.this, adminmenu.class));
            }
        });
    }
}