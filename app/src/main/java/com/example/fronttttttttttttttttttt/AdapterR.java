package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.RDVV.fillRDV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AdapterR extends RecyclerView.Adapter<AdapterR.MyViewHolder> {
    Context context;
    ArrayList<RDVV> list;
    private ArrayAdapter<String> dataAdapter;


    public AdapterR(Context context, ArrayList<RDVV> list) {
        this.context = context;
        this.list = list;

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
        RDVV R = list.get(position);
        holder.RD.setText(R.getLOC());
        holder.npick.setAdapter(dataAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView RD;
        Spinner npick;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            RD = itemView.findViewById(R.id.rendezvous);
            npick=itemView.findViewById(R.id.ambs);
            npick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // On selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    // Showing selected spinner item
                    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

}