package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.Menu.SCROLL_DELTA;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;

// TODO: 22/05/2022 confirmer
public class AdminPage2 extends Activity   {
    private ImageButton L,r;
    private HorizontalScrollView scrollView;
    public static  int SCROLL;
    private EditText npick;
    private Button cfrm;
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;
    FirebaseFirestore db;
    ArrayList<Hopital> list;
    MyAdapter myAdapter;
    DisplayMetrics displayMetric = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin2);
       npick=findViewById(R.id.hourpicker);
        cfrm=findViewById(R.id.inscrittt);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);
        recyclerView=findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

     recyclerView2=findViewById(R.id.recy2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        recyclerView3=findViewById(R.id.recy3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        recyclerView4=findViewById(R.id.recy4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<Hopital>();
        myAdapter=new MyAdapter(AdminPage2.this ,list,-1);

        recyclerView.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,0);

        recyclerView2.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,1);

        recyclerView3.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,2);

        recyclerView4.setAdapter(myAdapter);
        EventChangeListener();
        cfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a;
                if(npick.getValue() == 0){
                  a=999;
                }else{
                a=npick.getValue()-1;}*/

               /* db.collection("Hopital").document().update("Type de Vaccin",a).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"modifié avec success",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"une erreur a eu lieu",Toast.LENGTH_LONG).show();
                    }
                });*/
                }


        });
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = scrollView.getScrollX();
                if(x - SCROLL_DELTA >= 0) {
                    scrollView.scrollTo(x-SCROLL, scrollView.getScrollY());
                }return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxAmount = 4000;

                int x =scrollView.getScrollX();
                if(x + SCROLL_DELTA <= maxAmount) {
                    scrollView.scrollTo(x+SCROLL, scrollView.getScrollY());
                }
                return;
            }
        });
    }
private void EventChangeListener(){
        db.collection("Hopital").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("kop", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                           // if (dc.getType() == DocumentChange.Type.ADDED) {
                                list.add(dc.getDocument().toObject(Hopital.class));

                           // }
                            myAdapter.notifyDataSetChanged();
                        }
                    }

        });
}


}
