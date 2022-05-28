package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.RDVV.fillRDV;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;

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
import java.util.HashMap;
import java.util.List;

 public class AdapterR extends RecyclerView.Adapter<AdapterR.MyViewHolder>{
    Context context;
    ArrayList<RDVV> list;
    private ArrayAdapter<String> dataAdapter;
    FirebaseFirestore db;





    public AdapterR(Context context, ArrayList<RDVV> list,FirebaseFirestore db) {
        this.context = context;
        this.list = list;
        this.db=db;
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
        holder.chaterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Rendez-vous").document(RA.getIDR()).update("AMB",holder.it.get(0));
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
                Log.d("itemmmm", String.valueOf(item));
                ArrayList<String> K = new ArrayList<>();
                K.add(item);
                it.clear();
                it.addAll(K);
                Log.d("itemmmm", String.valueOf(it));

                npick.setSelection(fillRDV().indexOf(item));


            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    }



}