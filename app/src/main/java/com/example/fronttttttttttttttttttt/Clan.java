package com.example.fronttttttttttttttttttt;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class Clan {

        private int idC;
        private LinkedList<Pod> ListPods = new LinkedList <Pod>();
        public Clan(LinkedList<Pod> listPods) { ListPods = listPods; }
        public Clan (int i)
        {
            idC=i;
        }
        public LinkedList<Pod> getListPods() { return ListPods; }

        public void setListPods(LinkedList<Pod> listPods) { ListPods = listPods; }

        // public Pod randomP(){
        //     return ListPods.get((int)Math.random()*(ListPods.size()-1));
        // }
        public Individu randomP()
        {
            return ListPods.get((int)(Math.random()*(ListPods.size()-1))).randomI();
        }

        public Individu bestP(Object M[][]){
            Pod  best= ListPods.get(0);
            for(Pod a : ListPods){
                if(a.bestI(M).coutIndiv < best.bestI(M).coutIndiv){
                    best= a;
                }
            }
            return best.bestI(M);
        }

        public void triePod(Object M[][]){
            Pod t;
            for(int i=0;i<ListPods.size();i++){
                for(int j=i+1;j<ListPods.size();j++){
                    if( ListPods.get(i).moyenneP(M) > ListPods.get(j).moyenneP(M) ){
                        t=ListPods.get(i);
                        ListPods.set(i, ListPods.get(j));
                        ListPods.set(j ,t);
                    }

                }
            }
        }

        public void diversificationP(Individu rdmpop,Individu rdmclan,Object M[][])
        {
            Pod P= this.ListPods.getLast();
            double a,b;
            LatLng sol1 [] = new LatLng[rdmpop.getSolution().length];
            LatLng sol2 []=new LatLng[rdmclan.getSolution().length];
            for(Individu d : P.getListIndividus()) {
                a = Math.random();
                b = Math.random();
                ArrayList<LatLng> solPop = new ArrayList<LatLng>(), solClan = new ArrayList<LatLng>();
                sol1[0]=rdmclan.getSolution()[0];
                sol2[0] =rdmclan.getSolution()[0];
                for (int i = 1; i < rdmpop.getSolution().length-1; i++) {

                    LatLng A = new LatLng(a * rdmpop.getSolution()[i].latitude, a * rdmpop.getSolution()[i].longitude);
                    LatLng B = new LatLng(b * rdmclan.getSolution()[i].latitude, b * rdmclan.getSolution()[i].longitude);

                    sol1[i]=A;
                    sol2[i] =B;
                }
                sol1[rdmclan.getSolution().length-1]=sol1[0];
                sol2[rdmclan.getSolution().length-1] =sol1[0];

                Individu I =new Individu(sumSol(sol1, sol2),2,rdmpop.fmin,rdmpop.fmax,rdmpop.L,rdmpop.T,M);

                if(d.coutIndiv > I.coutIndiv  ){
                    d.setSolution(I.getSolution(),M);

                }
            }
        }

        public LatLng[] sumSol(LatLng[] solu1, LatLng[] solu2) {
            Vector<LatLng> sommesol = new Vector<LatLng>();
            double min,c;
            int j,k,p;

            LinkedList<LatLng> copiesol = new LinkedList<LatLng>(Arrays.asList(this.ListPods.get(0).getListIndividus().get(0).getSolution()));
            //remplir le vecteur somme:


            if(solu2.length != 0) {
                sommesol.add(copiesol.get(0));
                int m = 1;
                while (m < solu2.length - 1) {
                    sommesol.add(
                            new LatLng(solu1[m].latitude + solu2[m].latitude,
                                    solu1[m].longitude + solu2[m].longitude)
                    );
                    m++;
                }
                sommesol.add(copiesol.get(0));
            }else{
                for(int f=0;f< solu1.length;f++){
                    sommesol.add(new LatLng(solu1[f].latitude ,solu1[f].longitude) );
                }
            }
            //le faire correspondre a une vrai solution:

            copiesol.removeFirst();
            copiesol.removeLast();
            for (int i = 1; i < sommesol.size()-1; i++) {
                j = 0;
                while (j < copiesol.size() && sommesol.get(i) != copiesol.get(j)) {
                    j++;

                }
                if (j < copiesol.size()){
                    copiesol.remove(j);
                } else
                {
                    k = 0;
                    p = 0;

                    min = Math.sqrt(Math.pow(sommesol.get(i).longitude - copiesol.get(k).longitude, 2) +
                            Math.pow(sommesol.get(i).latitude - copiesol.get(k).latitude, 2));


                    while (k < copiesol.size() ) {

                        c=Math.sqrt(Math.pow(sommesol.get(i).longitude - copiesol.get(k).longitude, 2) +
                                Math.pow(sommesol.get(i).latitude - copiesol.get(k).latitude, 2));
                        if (c < min) {
                            min = c;
                            p = k;
                        }
                        k++;
                    }
                    sommesol.setElementAt(copiesol.get(p),i);
                    copiesol.remove(p);
                }
            }
            LatLng sol3 [] = new LatLng[sommesol.size()];
            for(int o=0;o<sommesol.size();o++){
                sol3[o]=sommesol.get(o);

            }

            return sol3;
        }


        public void addPod(Pod pod) {
            this.ListPods.add(pod);
        }






}
