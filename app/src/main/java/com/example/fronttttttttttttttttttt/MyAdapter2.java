package com.example.fronttttttttttttttttttt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {

    Context context;
    ArrayList<Hopital> list;
    FirebaseFirestore db;


    public MyAdapter2(Context context, ArrayList<Hopital> list,FirebaseFirestore db) {
        this.context = context;
        this.list = list;
        this.db=db;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item3,parent,false);
            return  new MyAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Hopital hopital = list.get(position);
            holder.nomhs.setText(hopital.NomH);
            holder.nbrtot.setText("" + ((int) hopital.DoseAstra + (int) hopital.DoseSpootnik + (int) hopital.DoseJohnson + (int) hopital.DoseSinovac)+" doses au total");
            holder.nbrambu.setText("" + (int) hopital.nbA+" ambulances");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nbrambu,nbrtot,nomhs;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomhs = itemView.findViewById(R.id.nomhosp);
            nbrambu = itemView.findViewById(R.id.nbramb);
            nbrtot = itemView.findViewById(R.id.nbrtotal);
        }
    }
}
