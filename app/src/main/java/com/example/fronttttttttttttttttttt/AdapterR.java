package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.RDVV.fillRDV;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class AdapterR extends RecyclerView.Adapter<AdapterR.MyViewHolder>  {
    Context context;
    ArrayList<RDVV> list;
    private ArrayAdapter<String> dataAdapter;
    FirebaseFirestore db;
    int i =1;
    ArrayList<String> listR = new ArrayList<>();



    public AdapterR(Context context, ArrayList<RDVV> list,FirebaseFirestore db,int i) {
        this.context = context;
        this.list = list;
        this.db=db;
        this.i=i;

        dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, RDVV.fillRDV());
        dataAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RDVV RA = list.get(position);
        holder.RD.setText(RA.getLOC());
        holder.npick.setAdapter(dataAdapter);
        holder.npick.setOnItemSelectedListener(holder);

        int i=1;
        holder.chaterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> pos =new ArrayList<>();
                db.collection("Ambulancier").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {
                            String IDA = documentChange.getDocument().getId();
                            String ID = documentChange.getDocument().get("id").toString();

                            listR.addAll ((ArrayList<String>)documentChange.getDocument().get("listR"));
                            if(listR.contains(RA.getIDR()) && ID != holder.it.get(holder.it.size()-1)){
                                Log.d("hna",String.valueOf(RA));
                                pos.add(1,IDA);
                            }
                            if (ID == holder.it.get(holder.it.size()-1) &&  !listR.contains(RA.getIDR())){
                                Log.d("hna11",String.valueOf(RA));
                                pos.add(0,IDA);
                                //pos.set(0,IDA);
                            }

                        }
                    }
                });
                if (pos.size()==2) {
                    db.collection("Ambulancier").document(pos.get(0)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<String> k = new ArrayList<>();
                                k.addAll((ArrayList<String>) task.getResult().get("listR"));
                                k.remove(k.indexOf(RA.getIDR()));
                                db.collection("Ambulancier").document(pos.get(1)).update("listR", k);
                            }
                        }
                    });
                }
         if(pos.size()==2){
             db.collection("Ambulancier").document(pos.get(1)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                     if (task.isSuccessful()){
                         ArrayList<String> k =new ArrayList<>();
                         k.addAll((ArrayList<String>)task.getResult().get("lisrR"));
                         k.add(RA.getIDR());
                         db.collection("Ambulancier").document(pos.get(0)).update("listR",k);
                     }
                 }
             });
         }


            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener{

        TextView RD;
        Spinner npick;
        ImageButton chaterr;
        ArrayList<String> it = new ArrayList<>();
        private String item=null;
        //RDVV RDA;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            RD = itemView.findViewById(R.id.rendezvous);
            npick=itemView.findViewById(R.id.ambs);
            chaterr=itemView.findViewById(R.id.chater1);
            npick.setOnItemSelectedListener(MyViewHolder.this);

        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Spinner Spinner =(Spinner) adapterView;
            if(Spinner.getId() == R.id.ambs) {
                item = (String) adapterView.getItemAtPosition(i);
                ArrayList<String> K = new ArrayList<>();
                K.add(item);
                it.addAll(K);
                npick.setSelection(fillRDV().indexOf(item));


            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    }



}