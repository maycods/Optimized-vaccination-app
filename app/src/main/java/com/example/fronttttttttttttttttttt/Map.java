package com.example.fronttttttttttttttttttt;

import android.app.Activity;
import android.graphics.Color;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
//TODO BOUTON JE SS ARRIV2 ET TT TESTER QD CARTE BANCAIRE PRETE
public class Map extends FragmentActivity implements OnMapReadyCallback, RoutingListener {
    GoogleMap map;
    int d=0;
    int r=0;
    private List<Polyline> polylines;
    private Button arriv,okk;
    private TextView leT,T;
    Boolean b = false;
    private static final int[] COLORS = new int[]{R.color.purple_200};
    Population pop = new Population();
    LatLng A ,B,C,D;
    LatLng ListePositions [] ;//={A=new LatLng(	35.55,  6.17 ),B =new LatLng( 35.6976541, -0.6337376),C =new LatLng(35.6976541,5.6337376),D=new LatLng(36.4798683,2.8005677)};
    Object  M[][] ;
   Vector<Integer> a ;
    int [] Vtest = new int[]{2,3,1,1,7,9,2,4,10,4,8,7};
    LatLng sol[] = new LatLng[]{A,B,C,D,A};
    ArrayList<LatLng> ListeP =new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        polylines = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            polylines.get(r).setColor(R.color.bleu2);
            r++;
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();
        LocalDate aujourhui = LocalDate.now();
        String date1 = aujourhui +" "+"00:00:00";
        Timestamp timestamp = Timestamp.valueOf(date1);

        db.collection("Ambulancier").document(currentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
           String AMB =task.getResult().get("id").toString();
        db.collection("Hopital").document(task.getResult().get("Hopital").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                GeoPoint geoPoint = (GeoPoint)task.getResult().get("Localisation");
                double lat = geoPoint.getLatitude();
                double lng = geoPoint.getLongitude();
                LatLng latLng = new LatLng(lat, lng);
                ListeP.add( 0,latLng);

                db.collection("Rendez-vous").whereEqualTo("AMB", AMB).whereEqualTo("dateR",timestamp).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        int i=1;
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {
                            GeoPoint geoPoint = (GeoPoint) documentChange.getDocument().get("Localisation");
                            double lat = geoPoint.getLatitude();
                            double lng = geoPoint.getLongitude();
                            LatLng latLng = new LatLng(lat, lng);
                            ListeP.add(i, latLng);
                            i++;
                        }
                        ListeP.add(ListeP.get(0));
                        Object  M1[][] = new Object[ListeP.size()+1][ListeP.size()+1];
                        Log.d("7123888", String.valueOf(ListeP.size()));
                        LatLng ListePP [] =new LatLng[ListeP.size()];
                        ListePP= ListeP.toArray(ListePP);
                        remplirM(M1,ListePP);

                    }
                });
            }}
        });
                       }
            }
        });

        }
        public void remplirM(Object M1[][],LatLng ListePP[]){
            ListePositions = new LatLng[ListePP.length];
        for(int i=0;i<ListePP.length;i++){
            Log.d("psssst",String.valueOf(ListePP[i]));
            ListePositions[i]=ListePP[i];
        }
        a = new Vector<Integer>(ListePositions.length*ListePositions.length-ListePositions.length);
        M = new Object[ListePP.length+1][ListePP.length+1];
            int j,i,l=0;
            for( j=1; j<=ListePP.length;j++) { M1[0][j]=ListePP[j-1]; M[0][j]=ListePP[j-1];}
            for( i=1; i<=ListePP.length;i++){ M1[i][0]=ListePP[i-1]; M[i][0]=ListePP[i-1];}

            for( j=1; j<=ListePP.length;j++) {
                for( i=1; i<=ListePP.length;i++){
                    if(i!=j) {
                        getRouteTiMarker((LatLng) M1[0][j],(LatLng) M1[i][0]);
                        M1[i][j]=Vtest[l];
                        M[i][j]=Vtest[l];
                        l++;
                    }else{
                        M1[i][j]=null;
                        M[i][j]=null;
                    }
                }
            }
        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
            Log.d("pppp",String.valueOf(M));

            polylines = new ArrayList<Polyline>();
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(R.color.black));
            polyOptions.addAll(route.get(0).getPoints());

            Log.d("jjjjjjjj",String.valueOf(M));

            if(b==true){
                polylines.add(map.addPolyline(polyOptions));
            }
             a.add(route.get(0).getDurationValue());   d++;
     //       Toast.makeText(getApplicationContext(), "Route " + (1) + ": distance  " + route.get(0).getDistanceText() + ": duration  " + route.get(0).getDurationText(), Toast.LENGTH_LONG).show();

//           if(polylines.size()>0) {
//                for (Polyline poly : polylines) {
//                    poly.remove();
//                }
//            }
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
                }
//                LatLng[] Q= algoPrincipal();
//                int i;
//                b=true;
//               for(  i=0; i< sol.length-1;i++) {
//                 getRouteTiMarker(Q[i],Q[i+1]);
//               } getRouteTiMarker(Q[i],Q[0]);
//
//             for( i=0; i< sol.length;i++) {
//                 Log.d("finnnn", String.valueOf(Q[i]));
//             }





 }

        }
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
        map =googleMap;
        int i=0;
             //map.moveCamera(CameraUpdateFactory.newLatLng(sol[0]));

        while(i < sol.length-1){
           // map.addMarker(new MarkerOptions().position(sol[i])).setTitle("Arret numero: "+(i+1));
           i++;
        }

        }

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
            int nbClan=5,nbPod=2,nbIndiv=60,fmin=0,fmax=1,L=100,IT=1000;

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
    private void getRouteTiMarker(LatLng ok,LatLng ol) {
       Log.d("MMMMMM",String.valueOf(M[1][2]));
        Routing routing = new Routing.Builder()
                .key("AIzaSyBGPlS5sQHDfAB3pEVyqXvU8hcuVUdG_gA")
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(ok,ol)
                .build();
        routing.execute();

    }
}


