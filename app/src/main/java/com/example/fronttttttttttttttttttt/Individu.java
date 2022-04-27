package com.example.fronttttttttttttttttttt;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Individu {

        final  private int id;
        public static int A;
        private LatLng[] solution;//[A]
        private int velocity =0;
        public  int  fmin=0;
        public  int fmax=1;
        public  int L=1000;
        public  int T=100;
        public Integer coutIndiv=0;

        public int getId() {
            return id;
        }

        public static int S=1500;

        public LatLng[] getSolution()
        {
            return solution;
        }

        public void setSolution(LatLng[] solution,Object[][] M)
        {
            this.solution = solution;
            calculCout(M);
        }

        public int getVelocity()
        {
            return velocity;
        }

        public void setVelocity(int velocity)
        {
            this.velocity = velocity;
        }

        public Individu(LatLng[] solution,int i,int Ifmin,int Ifmax,int IL,int IT,Object[][] M)
        {
            fmin=Ifmin;
            fmax=Ifmax;
            L=IL;
            T=IT;
            this.solution = solution;
            id=i;

            calculCout(M);
        }
        public void calculCout( Object M[][])
        {
            int i=0;
            Integer cout=0;

            while(i<solution.length-1){
                cout += (Integer) M[rechercheIndexD(solution[i],M)][rechercheIndexD(solution[i+1],M)];
                i++;
            }
            coutIndiv=cout;

        }

        public double f()
        {
            return fmin+Math.random()*(fmax-fmin);

        }

        public void Intensification(Population pop,Object M[][] )
        {
            int i=0;

            if(Math.random()<0.5)
            {
                // echolocation
                Log.d("echolocation",String.valueOf(i));
                velocity= (int) (velocity+f()*Distance(pop.searchC(this).P.bestI(M).getSolution(),solution) +
                        f()*Distance(pop.searchC(this).C.bestP(M).getSolution(),solution)+
                        f()*Distance(pop.bestC(M).getSolution(),solution));

                solution=twoOpt(solution, (int) (Math.random()*(solution.length-2)),velocity);
                this.calculCout(M);//todo JE PENSE mtn que on a variable cout ndirou kima l algo on met a jour les cout fl algo principale directe

            }
            else
            {
                Log.d("Cooperation",String.valueOf(i));
                //Cooperation

                ArrayList<SwapElement> swapResult = swapP( pop.searchC(this).P.getTemp(),pop.searchC(this).P.bestI(M).getSolution());
                swiper(swapResult,solution);
                this.calculCout(M);
            }
        }

        //
        public  ArrayList<SwapElement> swapP(LatLng[] solu1,LatLng[] solu2){
            int i=0,j=0;
            LinkedList<LatLng> dupl1 = new LinkedList<LatLng>(Arrays.asList(solu1.clone()));
            ArrayList<SwapElement> swapResult = new ArrayList<SwapElement>();
            // for(int p=0;p< solu1.length;p++){
            //   Log.d("pppppp",String.valueOf(dupl1.size()));
            //}
            while(j<solu2.length){

                if(dupl1.get(i) != solu2[j]) {

                    while (dupl1.get(i).latitude != solu2[j].latitude || dupl1.get(i).longitude != solu2[j].longitude) {
                        i++; Log.d("pppppp",String.valueOf(dupl1.get(i)));
                    }

                    SwapElement k =new SwapElement(j,i);

                    swapResult.add(k);
                    LatLng   t = dupl1.get(j);
                    dupl1.set(j,solu1[i]);
                    dupl1.set(i,t) ;
                }
                j++;i=j;
            }

            return swapResult;
        }
        public void swiper(ArrayList<SwapElement> swapseq , LatLng[] solu){

            for (int i=0;i<swapseq.size();i++){
                LatLng   t =solu[swapseq.get(i).getA()] ;
                solu[swapseq.get(i).getA()]=solu[swapseq.get(i).getB()];
                solu[swapseq.get(i).getB()]=t;
            }
        }
        public  int Distance(LatLng[] S,LatLng[] Z){
            int result=0;
            for(int i =0;i<S.length;i++){
                if(S[i]!=Z[i]){
                    result++;
                }
            }
            return result;
        }
        public LatLng[] twoOpt(LatLng[] aller , int i , int velocity ){//Lydia's Comment :aller : solution , i : random number in range 0 lenght-2( supprimer le début et la fin)
            LatLng[] result = new LatLng[aller.length];
            int k,som=0;
            //  Log.d("hna 1",String.valueOf(aller.length));
            for(int f=0;f<aller.length;f++)
            {
                result[f]=aller[f];
            }
            for(int f=0;f<aller.length-1;f++)
            {
                if(f<(i-1) || f>(i+1))
                {
                    som++;
                }
            }
            int valeur =(velocity%som)+1;
            if( aller[i]!=aller[aller.length-1] & valeur<=som  )
            {
                k=0;
                for(int j=1;j<aller.length-1;j++){
                    Log.d("k",String.valueOf(k));

                    if(aller[i]!=aller[j] &  aller[j]!=aller[i+1] & ( i==0 ||  aller[j]!=aller[i-1]) ){
                        k=k+1;
                        if(k==valeur){
                            Log.d("i",String.valueOf(i));
                            Log.d("j",String.valueOf(valeur));

                            result[i+1]=aller[j];
                            result[j]=aller[i+1];

                            Log.d("accepter",String.valueOf(i));
                            return result;
                        }}

                }
            }
            Log.d("pas accepter",String.valueOf(i));
            return result;
        }
        public int rechercheIndexD(LatLng aller,Object M[][]){
            int j;
            for(j=0;j<=solution.length-1;j++){
                if(M[j][0]==aller){
                    return j;
                }
            }
            return -1;
        }



}
