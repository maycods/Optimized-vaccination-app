package com.example.fronttttttttttttttttttt;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class AdminPage2 extends Activity   {
    private ImageButton L,r;
    public static  int SCROLL;
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4,recyf;
    FirebaseFirestore db;
    ArrayList<Hopital> list;
    MyAdapter myAdapter;
    MyAdapter2 myAdapter2;
    int pos=0;
    DisplayMetrics displayMetric = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin2);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);

        recyf=findViewById(R.id.recyfok);
        recyf.setHasFixedSize(false);
        recyf.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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


        myAdapter=new MyAdapter(AdminPage2.this ,list,-1,db);

        recyclerView.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,7,db);

        recyclerView2.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,1,db);

        recyclerView3.setAdapter(myAdapter);
        myAdapter=new MyAdapter(AdminPage2.this ,list,2,db);

        recyclerView4.setAdapter(myAdapter);

        myAdapter2=new MyAdapter2(AdminPage2.this ,list,db);
        recyf.setAdapter(myAdapter2);
        EventChangeListener();

        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=-1){
                    recyf.scrollToPosition(pos);
                    pos--;}
                return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=list.size()){
                recyf.scrollToPosition(pos);
                pos++;}
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
                           if (dc.getType() == DocumentChange.Type.ADDED) {
                                list.add(dc.getDocument().toObject(Hopital.class));
                           }
                            myAdapter.notifyDataSetChanged();
                            myAdapter2.notifyDataSetChanged();
                        }
                    }

        });
}


}
