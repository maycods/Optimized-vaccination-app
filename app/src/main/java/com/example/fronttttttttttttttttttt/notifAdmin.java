package com.example.fronttttttttttttttttttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;

public class notifAdmin extends AppCompatActivity {
    TextView nume;
    Button okk;
    FirebaseFirestore db;
    private static final int REQUEST_CALL = 1;
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
                callnumber();
            }
        });
    }
    private void callnumber() {

        Intent intent = new Intent(Intent.ACTION_CALL);

        if (ActivityCompat.checkSelfPermission(notifAdmin.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(notifAdmin.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
            intent.setData(Uri.parse("tel:" + nume.getText()));
            startActivity(intent);}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callnumber();
            } else {
                Toast.makeText(this, "Permission Denined, go to the settings to allow it if you want", Toast.LENGTH_LONG).show();
            }
        }
    }

}