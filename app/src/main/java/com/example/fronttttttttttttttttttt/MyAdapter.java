package com.example.fronttttttttttttttttttt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
int i;
    Context context;
    ArrayList<Hopital> list;

    public MyAdapter(Context context, ArrayList<Hopital> list,int i) {
        this.context = context;
        this.list = list;
        this.i=i;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      Hopital hopital = list.get(position);
        holder.hopital.setText(hopital.getNomH());
        switch (i){
            case -1:
                holder.typev.setText("Spootnik");
                holder.npick.setText(String.valueOf(hopital.getDosSp()));

                break;
            case 0:
                holder.typev.setText("Astrazeneca");
                holder.npick.setText(String.valueOf(hopital.getDosA()));

                break;
            case 1:
                holder.typev.setText("Sinovac");
                holder.npick.setText(String.valueOf(hopital.getDosSi()));

                break;
            case 2:
                holder.typev.setText("johnson");
                holder.npick.setText(String.valueOf(hopital.getDosJ()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView hopital, typev;
        EditText npick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                   typev = itemView.findViewById(R.id.typp);
          hopital = itemView.findViewById(R.id.hop);
            npick=itemView.findViewById(R.id.hourpicker);

        }
    }
}
