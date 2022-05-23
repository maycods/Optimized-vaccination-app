package com.example.fronttttttttttttttttttt;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RDVV {
    String IDR,typdV , LOC;
     ArrayList<String> listV=new ArrayList<String>();




    Timestamp dateR;

    public String getIDR() {
        return IDR;
    }

    public String getTypdV() {
        return typdV;
    }

    public String getLOC() {
        return LOC;
    }

    public RDVV(String IDR , String typdV, String LOC) {
        this.IDR = IDR;
        this.typdV = typdV;
        this.LOC = LOC;

    }

    public ArrayList<String> getListV() {
        return listV;
    }
    public static ArrayList<String> fillRDV(){
        ArrayList<String> listV=new ArrayList<>();
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("Ambulancier").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    String NOM = documentChange.getDocument().getData().get("Nom et Prenom").toString();
                    listV.add(NOM);
                }
            }
        });
        return listV;

    }
    public void setListV(ArrayList<String> listV) {
        this.listV = listV;
    }

    public RDVV() {

    }


    public Timestamp getDateR() {
        return dateR;
    }
}
