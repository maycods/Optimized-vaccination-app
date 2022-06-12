package com.example.fronttttttttttttttttttt;

import android.content.Context;
import android.media.audiofx.LoudnessEnhancer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
int i;
    Context context;
    ArrayList<Hopital> list;
    FirebaseFirestore db;


    public MyAdapter(Context context, ArrayList<Hopital> list,int i,FirebaseFirestore db) {
        this.context = context;
        this.list = list;
        this.i=i;
        this.db=db;
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

            holder.hopital.setText(hopital.NomH);
            switch (i) {
                case -1:
                    holder.typev.setText("Spootnik");
                    holder.npick.setText("" + hopital.DoseSpootnik);
                    holder.chaterr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("Hopital").whereEqualTo("NomH", hopital.NomH).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        String IDR = documentChange.getDocument().getId();
                                        db.collection("Hopital").document(IDR).update("DoseSpootnik", Long.parseLong(holder.npick.getText().toString()));
                                        holder.chaterr.setImageResource(R.drawable.ic_baseline_check_25);
                                    }
                                }
                            });

                        }
                    });
                    break;
                case 7:
                    holder.typev.setText("Astrazeneca");
                    holder.npick.setText("" + hopital.DoseAstra);
                    holder.chaterr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("Hopital").whereEqualTo("NomH", hopital.NomH).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        String IDR = documentChange.getDocument().getId();
                                        db.collection("Hopital").document(IDR).update("DoseAstra", Long.parseLong(holder.npick.getText().toString()));
                                        holder.chaterr.setImageResource(R.drawable.ic_baseline_check_25);
                                    }
                                }
                            });

                        }
                    });
                    break;
                case 2:
                    holder.typev.setText("johnson");
                    holder.npick.setText("" + hopital.DoseJohnson);
                    holder.chaterr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("Hopital").whereEqualTo("NomH", hopital.NomH).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        String IDR = documentChange.getDocument().getId();
                                        db.collection("Hopital").document(IDR).update("DoseJohnson", Long.parseLong(holder.npick.getText().toString()));
                                        holder.chaterr.setImageResource(R.drawable.ic_baseline_check_25);
                                    }
                                }
                            });
                        }
                    });
                    break;
                case 1:
                    holder.typev.setText("Sinovac");
                    holder.npick.setText("" + hopital.DoseSinovac);
                    holder.chaterr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("Hopital").whereEqualTo("NomH", hopital.NomH).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        String IDR = documentChange.getDocument().getId();
                                        db.collection("Hopital").document(IDR).update("DoseSinovac", Long.parseLong(holder.npick.getText().toString()));
                                        holder.chaterr.setImageResource(R.drawable.ic_baseline_check_25);
                                    }
                                }
                            });
                        }
                    });
                    break;

            }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView hopital, typev,nbrambu,nbrtot,nomhs;
        EditText npick;
    ImageButton chaterr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                   typev = itemView.findViewById(R.id.typp);
                    hopital = itemView.findViewById(R.id.hop);
                    nomhs=itemView.findViewById(R.id.nomhosp);
                    nbrambu=itemView.findViewById(R.id.nbramb);
                    nbrtot=itemView.findViewById(R.id.nbrtotal);
                    npick=itemView.findViewById(R.id.hourpicker);
                    chaterr=itemView.findViewById(R.id.chater);
                  }
    }
}
