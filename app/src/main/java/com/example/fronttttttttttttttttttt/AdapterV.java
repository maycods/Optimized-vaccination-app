package com.example.fronttttttttttttttttttt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterV extends RecyclerView.Adapter<AdapterV.MyViewHolder>{
  Context context;
  ArrayList<Vaccin> list;
  FirebaseFirestore db;

  public AdapterV(Context context, ArrayList<Vaccin> list, FirebaseFirestore db) {
    this.context = context;
    this.list = list;
    this.db = db;
  }

  @NonNull
  @Override
  public AdapterV.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View V = LayoutInflater.from(context).inflate(R.layout.item5,parent,false);
    return new AdapterV.MyViewHolder(V);
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterV.MyViewHolder holder, int position) {
          Vaccin V = list.get(position);
          holder.nbPer.setText(Integer.toString(V.nb) +" Personnes");
          holder.TV1.setText(V.VT);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public static  class MyViewHolder extends RecyclerView.ViewHolder {
      TextView nbPer,TV1;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        nbPer =  itemView.findViewById(R.id.nbPer);
        TV1 =  itemView.findViewById(R.id.TV1);
    }
  }
}
