package com.example.fronttttttttttttttttttt;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class adminmenu extends AppCompatActivity {

private Button amb,user,dose,affamb;
ImageButton notf;
    FirebaseFirestore db;
    Date date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);
        amb=findViewById(R.id.plusA);
        user=findViewById(R.id.plusU);
        dose=findViewById(R.id.doshp);
        affamb=findViewById(R.id.affamb);
        notf=findViewById(R.id.notifad);
        notf.setEnabled(false);
        db = FirebaseFirestore.getInstance();


      date=new Date();
        Date yesterday = new Date(date.getTime() - (1000 * 60 * 60 * 24));
        yesterday.setSeconds(0);
        yesterday.setMinutes(0);
        yesterday.setHours(0);
        Timestamp timestamp = new Timestamp(yesterday.getTime());

        db.collection("Rendez-vous").whereLessThanOrEqualTo("dateR",timestamp).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(adminmenu.this,"notification");
                Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
                builder.setContentTitle("Vaccination non confirmé");
                builder.setContentText("Certains patients n'ont pas confirmer leurs vaccination");
                builder.setSmallIcon(R.drawable.usthblogo);
                builder.setAutoCancel(true);
                builder.setSound(alarmSound);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(adminmenu.this);
                managerCompat.notify(1,builder.build());
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                   if( documentChange.getDocument().get("confV").equals(false)){
                       notf.setEnabled(true);
                       notf.setImageResource (R.drawable.ic_outline_notifications_active_25);
                       String idu =  (String) documentChange.getDocument().get("IDP");
                       notf.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent=  new Intent(adminmenu.this, notifAdmin.class);
                               intent.putExtra("iduser" , idu);
                               startActivity(intent);
                               getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                                       WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                           }
                       });
                   }
                }

            }
        });




amb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(adminmenu.this,SignupA.class ));
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
         startActivity(new Intent(adminmenu.this,SignupU.class ));
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