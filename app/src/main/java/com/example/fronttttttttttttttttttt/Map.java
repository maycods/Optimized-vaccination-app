package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
//TODO BOUTON JE SS ARRIV2 ET TT TESTER QD CARTE BANCAIRE PRETE
public class Map extends FragmentActivity implements OnMapReadyCallback, RoutingListener {
    GoogleMap map;
    int d=0;
    private List<Polyline> polylines;
    private Button arriv,okk;
    private TextView leT,T;
    private static final int[] COLORS = new int[]{R.color.purple_200};
    Population pop = new Population();
    LatLng A ,B,C,D;
    LatLng ListePositions [] ={A=new LatLng(	35.55,  6.17 ),B =new LatLng( 35.6976541, -0.6337376),C =new LatLng(35.6976541,5.6337376),D=new LatLng(36.4798683,2.8005677)};
    Object  M[][] = new Object[ListePositions.length+1][ListePositions.length+1];
    Vector<Integer> a = new Vector<Integer>(ListePositions.length*ListePositions.length-ListePositions.length);
    int [] Vtest = new int[]{2,3,1,1,7,9,2,4,10,4,8,7};
    LatLng sol[] = new LatLng[]{A,B,C,D,A};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

            polylines = new ArrayList<>();
        /*   SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);*/

           remplirM(M);
arriv=findViewById(R.id.arrived);
okk=findViewById(R.id.ok);
leT=findViewById(R.id.leté);
T=findViewById(R.id.adreTOtype);
arriv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        leT.setText("Le type de vaccin à administrer est");
        T.setText("Spootnik");
        okk.setVisibility(View.VISIBLE);
        arriv.setVisibility(View.GONE);
    }
});
okk.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        leT.setText("Votre prochaine destination est");
        T.setText("na naana nanana");
        okk.setVisibility(View.GONE);
        arriv.setVisibility(View.VISIBLE);
    }
});

        }
        public void remplirM(Object M[][]){
            int j,i,l=0;
            for( j=1; j<=ListePositions.length;j++) { M[0][j]=ListePositions[j-1]; }
            for( i=1; i<=ListePositions.length;i++){ M[i][0]=ListePositions[i-1]; }

            for( j=1; j<=ListePositions.length;j++) {
                for( i=1; i<=ListePositions.length;i++){
                    if(i!=j) {
                    //    getRouteTiMarker((LatLng) M[0][j],(LatLng) M[i][0]);
                        M[i][j]=Vtest[l];
                        l++;
                    }else{
                        M[i][j]=null;
                    }
                }
            }

        }
        @Override
        public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

            polylines = new ArrayList<>();//todo ici on veut circuit
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.addAll(route.get(0).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
           // polylines.add(polyline);


             a.add(route.get(0).getDurationValue());
            Toast.makeText(getApplicationContext(), "Route " + (1) + ": distance  " + route.get(0).getDistanceText() + ": duration  " + route.get(0).getDurationText(), Toast.LENGTH_LONG).show();
          d++;
         /*   if(polylines.size()>0) {
                for (Polyline poly : polylines) {
                    poly.remove();
                }
            }*/

 if(d==(ListePositions.length*ListePositions.length)-ListePositions.length){
     int k=0;
     for( int j=1; j<=ListePositions.length;j++) {
         for( int i=1; i<=ListePositions.length;i++){
             if(i!=j) {
                 M[i][j]=a.get(k);
                 k++;
             }else{
                 M[i][j]=null;
             }
         }
     } for( int j=0; j<=ListePositions.length;j++) {
         for( int i=0; i<=ListePositions.length;i++) {
             Log.d("success", String.valueOf(M[i][j]));
         }}

     LatLng[] Q= algoPrincipal();
     int i;
     for(  i=0; i< sol.length-1;i++) {
         getRouteTiMarker(Q[i],Q[i+1]);
     } getRouteTiMarker(Q[i],Q[0]);
     for( i=0; i< sol.length;i++) {
         Log.d("finnnn", String.valueOf(Q[i]));
     }
 }


        }
        private void getRouteTiMarker(LatLng ok,LatLng ol) {
            Routing routing = new Routing.Builder()
                    .key("AIzaSyBDwDlIFf_vWmwO9HPWFvdVsIsQ7Edzarg")
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(ok,ol)
                    .build();
            routing.execute();

        }
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
        map =googleMap;//nrmt boucle
        map.addMarker(new MarkerOptions().position(B).title("destination"));
        map.addMarker(new MarkerOptions().position(A).title("destination"));
        map.addMarker(new MarkerOptions().position(C).title("destination"));
        map.addMarker(new MarkerOptions().position(D).title("destination"));
        }
        @Override
        public void onRoutingCancelled() {}
        @Override
        public void onRoutingFailure(RouteException e) {
            if(e != null) {
                Toast.makeText(this, "Error Route : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Something went wrong in the routing, Try again", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onRoutingStart() {}

        public LatLng[] generationIndividu()
        {
            int k=0;

            ArrayList<LatLng> listeCopie = new ArrayList<LatLng>(Arrays.asList(sol.clone()));
                //enlever le premier et dernier element(hopital)
                listeCopie.remove(0);
                listeCopie.remove(listeCopie.size()-1);
                //je choisi deux position alleatoire et differente
                  int  j;
                int I =(int)(Math.random()*(listeCopie.size()-1));
                do
                {
                     j =(int)(Math.random()*(listeCopie.size()-1));
                }while(I==j);
                            //permuter les 2 element d indice i et j
                LatLng  t=listeCopie.get(I);
                listeCopie.set(I, listeCopie.get(j));
                listeCopie.set(j ,t);
                        // remettre le premie et le dernier
                listeCopie.add(0,sol[0]);
                listeCopie.add(sol[0]);
               // sol=(LatLng[])listeCopie.toArray();
                for(int p=0;p<listeCopie.size();p++){
                    sol[p]=listeCopie.get(p);
                    Log.d("oooo",String.valueOf(sol[p]));
                }
       //     }while(ExistInPopulation(ListePositions));
           return  sol ;
        }

        public Population creationPopulation(int nbClan,int nbPod,int nbIndiv,int fmin,int fmax,int L,int T)
        {
            Individu.A = ListePositions.length+1;
            int j=0;
            Pod P= new Pod(0);
            Clan C = new Clan(0) ;
            Population POP =new Population();

            int t=0;

            for(int i=1;i<=nbIndiv;i++)
            {
                Individu NewI =new Individu(generationIndividu(),i,fmin,fmax,L,T,M);
                Individu newi = new Individu(NewI.getSolution().clone(),i,fmin,fmax,L,T,M);
                P.addIndividu(newi);

                if(i%(nbIndiv/(nbPod*nbClan))==0)
                {
//                    for(int u=0;u<P.getListIndividus().size();u++){
//                        for (int l = 0; l < P.getListIndividus().get(0).getSolution().length; l++) {
//                               Log.d("Liste", String.valueOf(P.getListIndividus().get(u).getSolution()[l]));
//                         }
//                    }

                     C.addPod(P);
                     P= new Pod((i/(nbIndiv/(nbPod*nbClan))));
                }
                if(i%(nbIndiv/nbClan)==0 )
                {
                      POP.addClan(C);
                       C = new Clan(nbIndiv/nbClan);
                }
            }
            return POP;
        }

        public LatLng[] algoPrincipal()
        {
            int nbClan=5,nbPod=2,nbIndiv=60,fmin=0,fmax=1,L=100,IT=1000;//elle a mit nbPod =2 donc t3 chaque clan faut verifier avec la prof

            Long  T=System.currentTimeMillis();
           //2;
           pop = creationPopulation(nbClan,nbPod,nbIndiv,fmin,fmax,L,IT);
           Individu.A=pop.getListClans().get(0).getListPods().get(0).getListIndividus().get(0).getSolution().length;
           Log.d("lydia",String.valueOf(Individu.A));
           int t=0;

//            for (Clan d : pop.getListClans()) {
//                for (Pod o:d.getListPods()) {
//                    for(Individu i: o.getListIndividus()){
//                        for(int j=0;j<i.getSolution().length;j++) {
//                            Log.d("tage", String.valueOf(i.getSolution()[j]));
//                        }  Log.d("tage","hu");
//                    }
//                }
//
//            }
            while (t < 15 ){
                 for(Clan c : pop.getListClans())
                 {
                        for(Pod p : c.getListPods()){
                            //7.
                              p.TrieI(M);
                            //8.
                                 p.calculeTemp(T);
                               for(Individu I: p.getListIndividus()) {
                                   I.Intensification(pop, M);
                               }
                        }
                        //10.
                       c.triePod(M);
                       //11.

                       c.diversificationP(pop.randomPop(),c.randomP(),M);
                 }
                //12.
                 t++;

            }

           return pop.bestC(M).getSolution();
        }
}


