
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

public class AdapterH extends RecyclerView.Adapter<AdapterH.MyViewHolder> {

    Context context;
    ArrayList<Hopital> list;
    FirebaseFirestore db;


    public AdapterH(Context context, ArrayList<Hopital> list,FirebaseFirestore db) {
        this.context = context;
        this.list = list;
        this.db=db;
    }

    @NonNull
    @Override
    public AdapterH.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item4,parent,false);
        return  new AdapterH.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterH.MyViewHolder holder, int position) {
        Hopital hopital = list.get(position);
        holder.NomH.setText(hopital.NomH);
        holder.nbrA.setText(""+(int) hopital.nbA+" ambulanciers");
        holder.nbSP.setText(holder.nbSP.getText().toString()+" "+(int) hopital.DoseSpootnik);
        holder.nbAS.setText(holder.nbAS.getText().toString()+" "+(int) hopital.DoseAstra);
        holder.nbJJ.setText(holder.nbJJ.getText().toString()+" "+(int) hopital.DoseJohnson);
        holder.nbSN.setText(holder.nbSN.getText().toString()+" "+(int) hopital.DoseSinovac);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nbrA,NomH,nbSP,nbAS,nbJJ,nbSN;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            NomH = itemView.findViewById(R.id.nomH);
            nbrA = itemView.findViewById(R.id.nbAm);
            nbSP = itemView.findViewById(R.id.nbSP);
            nbAS = itemView.findViewById(R.id.nbAS);
            nbJJ = itemView.findViewById(R.id.nbJJ);
            nbSN = itemView.findViewById(R.id.nbSN);
        }
    }
}
