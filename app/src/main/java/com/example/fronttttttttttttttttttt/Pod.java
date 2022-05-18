package com.example.fronttttttttttttttttttt;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Pod {

        private int idP;
        private LinkedList<Individu> ListIndividus = new LinkedList <Individu>();
        private LatLng[] temp;
        public Pod (int i)
        {
            idP=i;
        }
        public Pod (LinkedList<Individu> ListIndividus)
        {
            this.ListIndividus = ListIndividus;

        }

        public LinkedList<Individu> getListIndividus()
        {

            return ListIndividus;

        }

        public void setListIndividus(LinkedList<Individu> ListIndividus)
        {
            this.ListIndividus = ListIndividus;
        }

        public LatLng[] getTemp()
        {
            return temp;
        }
        public Individu randomI()
        {
            int A= (int)(Math.random()*(ListIndividus.size()-1));
            return ListIndividus.get(A);
        }

        public Individu bestI(Object M[][])
        {
            Individu Meuilleur = ListIndividus.get(0);
            for(Individu I : ListIndividus)
            {
                if(I.coutIndiv < Meuilleur.coutIndiv)
                {
                    Meuilleur= I;
                }
            }
            return Meuilleur;
        }

        public float moyenneP(Object M[][])
        {
            float moyenne=0;

            if(ListIndividus.size()!=0){
                for(Individu I: ListIndividus)
                {
                    moyenne=moyenne+I.coutIndiv;
                }
                moyenne=(float) moyenne/ListIndividus.size();
            }

            return moyenne;

        }

        public void TrieI(Object M[][])
        {

            Collections.sort(ListIndividus, new Comparator<Individu>() {
                @Override
                public int compare(Individu individu, Individu t1) {
                    return individu.coutIndiv.compareTo(t1.coutIndiv);
                }
            });
        }
//
//    public void calculeTempMayalydia( long tStart)
//    {
//        int moyenne=0;
//        if (ListIndividus.size()!=0)
//        {
//
//             for(Individu I:ListIndividus)
//        {
//               int A=I.getSolution().length-1;
//              Long t=System.currentTimeMillis()-tStart;
//              moyenne+= A*(Math.sin((2*Math.PI/I.L)*ListIndividus.indexOf(I))*Math.cos((2*Math.PI/I.T)*t));
//        }
//
//        moyenne = moyenne/ListIndividus.size();
//        }
//        temp = ListIndividus.get(moyenne).getSolution();
//    }

        public void calculeTemp( long tStart ) {
            //A : represente une valeur maximum d'un ensemble de données , dans notre cas j'ai pris les latitude et longitude maximum des bord algeriens
            double Alongitude = 11.108203125000015;
            double Alatitude = 37.0923828125;
            Log.d("hohoho",String.valueOf(ListIndividus.size()));
            //How does it work in reality
            //on parcours toute les solution ( les individus) et on calcule la moyenne des positions dans les solutions
            if (ListIndividus.size() != 0) {
                Log.d("tagtag",String.valueOf(ListIndividus.get(0).getSolution()[0]));
                temp = new LatLng[Individu.A];
                getTemp()[0] =ListIndividus.get(0).getSolution()[0];

                //Recuperer la taille de la solution , dans ce cas on modifie de la taille 1 à length-1
                int size = ListIndividus.get(0).getSolution().length;
                for (int i = 1; i < size - 1; i++) {
                    double moyenneLatitude = 0, moyenneLongitude = 0;
                    for (Individu I : ListIndividus) {
                        Long t = System.currentTimeMillis() - tStart;
                        moyenneLatitude += Alatitude * (Math.sin((2 * Math.PI / I.L) * (I.getSolution()[i]).latitude) * Math.cos((2 * Math.PI / I.T) * t));
                        moyenneLongitude += Alongitude * (Math.sin((2 * Math.PI / I.L) * (I.getSolution()[i]).longitude) * Math.cos((2 * Math.PI / I.T) * t));
                    }
                    temp[i] =new LatLng(moyenneLatitude / (size - 2), moyenneLongitude / (size - 2));

                }temp[size - 1] =ListIndividus.get(0).getSolution()[0];
                LatLng [] sol = new LatLng[0];
                Pod p =new Pod(ListIndividus);
                Clan c= new Clan(-5);
                c.addPod(p);
                temp=c.sumSol(temp,sol);
            }
        }

        public void addIndividu(Individu newI) {
            this.ListIndividus.add(newI);
        }


}
