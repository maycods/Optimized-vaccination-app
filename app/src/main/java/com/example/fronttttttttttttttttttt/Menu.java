package com.example.fronttttttttttttttttttt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.ImageButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {//TODO AFFICHER DE LA BD LES VACCINS
    private Button profilee, prev, num, Rdv;
    private ImageButton  notif,L,r;
    private static final int REQUEST_CALL = 1;
    private HorizontalScrollView scrollView;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    FirebaseFirestore db;
    public static  int SCROLL_DELTA;// Pixel.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        db = FirebaseFirestore.getInstance();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCROLL_DELTA = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetrics.widthPixels/3 +35, getResources().getDisplayMetrics());
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        notif = (ImageButton) findViewById(R.id.notif);
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);
        profilee = (Button) findViewById(R.id.profil);
        Rdv = (Button) findViewById(R.id.rdv);
        num = (Button) findViewById(R.id.numvert);
        prev = (Button) findViewById(R.id.prevention);
        notif.setEnabled(false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();
        db.collection("Rendez-vous").whereEqualTo("IDP",currentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            com.google.firebase.Timestamp timestamp = (Timestamp) document.get("dateR");
                          LocalDate g  =LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(timestamp.toDate())) ;
                            LocalDate JJ = LocalDate.now().plusDays(1);
                            Log.d("lplplp",String.valueOf(System.currentTimeMillis()));
                            if(g.isEqual(JJ)/* && System.currentTimeMillis()/1000< Long.parseLong("46800") */&& !(boolean) document.get("confR")){

                                notif.setEnabled(true);
                                notif.setImageResource (R.drawable.ic_outline_notifications_active_25);
                                notif.setOnClickListener(new View.OnClickListener() { //TODO NOTIFF
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=  new Intent(Menu.this, NotifR.class);
                                        startActivity(intent);
                                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                                                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                                    }
                                });
                            }
                        }
            }
        }

        });

        profilee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, InfoPersoAvantModif.class));
            }
        });
        Rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Rendez_vous.class));
            }
        });
        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callnumber();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.who.int/fr/emergencies/diseases/novel-coronavirus-2019/advice-for-public")));
            }
        });
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = scrollView.getScrollX();
                if(x - SCROLL_DELTA >= 0) {
                   scrollView.scrollTo(x-SCROLL_DELTA, scrollView.getScrollY());
                }return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxAmount = 2000;

                int x =scrollView.getScrollX();
                if(x + SCROLL_DELTA <= maxAmount) {
                 scrollView.scrollTo(x+SCROLL_DELTA, scrollView.getScrollY());
                }
                return;
            }
        });
    }

    private void callnumber() {
        String number = "3030";
        Intent intent = new Intent(Intent.ACTION_CALL);

        if (ActivityCompat.checkSelfPermission(Menu.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Menu.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
        intent.setData(Uri.parse("tel:" + number));
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

    @Override
    public void onBackPressed() {


            Intent a = new Intent(Menu.this,MainActivity.class);
            startActivity(a);


    }
}

