package com.example.fronttttttttttttttttttt;
import static android.service.controls.ControlsProviderService.TAG;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Rendez_vous extends Activity implements AdapterView.OnItemSelectedListener {
    private ImageButton retour;
    private EditText mSearchText;
    private Button comfirmer;
    private  TextView gps;
    private String date1 = "";
    private String choixV=null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng Position;
    private String typeV;
    private ArrayList<String> listV=new ArrayList<String>();
    private ArrayAdapter<CharSequence> adapter;
    private Spinner V;
    private HashMap<String, Object> RDV = new HashMap<String, Object>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv);
        CalenderEvent calenderEvent = findViewById(R.id.calender_event);

        mSearchText = (EditText)findViewById(R.id.search_des) ;
        gps = (TextView) findViewById(R.id.mapos) ;
        retour=(ImageButton)findViewById(R.id.retourR);
        comfirmer=(Button) findViewById(R.id.comfirmerr);
        V = findViewById(R.id.spinner1);


//idpatient
        DocumentReference reference;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();


//vaccin
//        db.collection("Vaccin").document("IDV").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful() && task.getResult().exists() && task.getResult() != null)
//                {
//                    listV.addAll ((ArrayList<String>) task.getResult().get("TypeVaccin"));
//
//                }
//            }
//        });
        adapter = ArrayAdapter.createFromResource(this,R.array.vaccines, R.layout.spinn);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        String[] l = getResources().getStringArray(R.array.vaccines);
        Collections.addAll(listV,l);
        Log.d("vaccccccccc",String.valueOf(listV));
        reference=db.collection("user").document(currentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            typeV = (String) task.getResult().get("Type de vaccin");
                            Log.d("wow", String.valueOf(typeV));
                            V.setAdapter(adapter);
                            if (typeV==null){
                                V.setEnabled(true);
                                V.setClickable(true);
                                V.setOnItemSelectedListener(Rendez_vous.this);
                            }
                            else {
                                V.setSelection(listV.indexOf(typeV));
                                V.setEnabled(false);
                                choixV=typeV;
                                //RDV.put("Type de vaccin", typeV);
                            }
                        }}});
//Date
        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {

                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                String date= dateFormat.format(new Date());
                String jourj;
                LocalDate  aujourhui = LocalDate.parse(date);



                    if(dayContainerModel.getMonthNumber() + 1 != 10 && dayContainerModel.getMonthNumber() + 1 != 11 && dayContainerModel.getMonthNumber() + 1 != 12) {
                        jourj = dayContainerModel.getYear() + "-0" + String.valueOf(dayContainerModel.getMonthNumber()+1) ;
                    }else{
                        jourj = dayContainerModel.getYear() + "-" + String.valueOf(dayContainerModel.getMonthNumber()+1);
                    }
                if(dayContainerModel.getDay()  < 10 ) {
                    jourj = jourj+ "-0" + dayContainerModel.getDay();
                }else{
                    jourj = jourj+ "-" + dayContainerModel.getDay();
                }
                LocalDate  j = LocalDate.parse(jourj);


                if(aujourhui.isAfter(j) || aujourhui.isEqual(j) || aujourhui.plusDays(1).isEqual(j)){
                    Toast.makeText(getApplicationContext(), "Date de rendez-vous indisponible", Toast.LENGTH_LONG).show();

                }else{
                    date1 = jourj +" "+"00:00:00";
                    Timestamp timestamp = Timestamp.valueOf(date1);
                    RDV.put("dateR",timestamp);

                }


            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Rendez_vous.this, Menu.class));
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askGalleryPermissionLocation();
            }
        });
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocate();
                String search = mSearchText.getText().toString().trim();
                if(search.isEmpty()){
                    mSearchText.setError("Champ obligatoire");
                    mSearchText.requestFocus();
                    return;
                }
                if(Position ==null){
                    mSearchText.setError("adresse invalide");
                    mSearchText.requestFocus();
                    return;
                }
                if(date1.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Selectionnez une date", Toast.LENGTH_LONG).show();
                    return;
                }
                db.collection("user").document(currentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if(  task.isSuccessful()) {
                          Long a =(Long) task.getResult().get("Nombre de chance");
    if(a>0){
         db.collection("user").document(currentId).update("Type de vaccin", choixV);
         db.collection("Rendez-vous").whereEqualTo("IDP", currentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                if (task.getResult().getDocuments().isEmpty()) {
                    RDV.put("IDP", currentId);
                    GeoPoint geo = new GeoPoint(Position.latitude, Position.longitude);
                    RDV.put("Localisation", geo);
                    RDV.put("confR", false);
                    RDV.put("confV", false);
                    RDV.put("comfJJ", true);
                    RDV.put("AMB", "");
                    RDV.put("Type de vaccin", choixV);

                    db.collection("Rendez-vous").document()
                            .set(RDV)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Rendez-vous pris avec succes",
                                            Toast.LENGTH_SHORT).show();
                                    mSearchText.setFocusable(false);
                                    V.setEnabled(false);
                                    calenderEvent.requestFocus();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Fail", "Error", e);

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Rendez-vous deja pris",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Rendez_vous.this, Rendez_vous.class));
                }
            }
        }
    });
}else{
    Toast.makeText(getApplicationContext(),"Vous ne pouvez plus prendre de rendez-vous car vous avez consommé toutes vos tentatives de vaccination de par vos annulations",Toast.LENGTH_LONG).show();
}
                      }
                    }
                });


            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choixV = (String) adapterView.getItemAtPosition(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    private void geoLocate(){
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Rendez_vous.this);
        List<Address> list = new ArrayList<>();

        try{
            list = geocoder.getFromLocationName(searchString, 1);

        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }
        if(list.size() > 0){
            Address address = list.get(0);
            Position =new LatLng( address.getLatitude(),address.getLongitude());

        }

        else{
            Toast.makeText(getApplicationContext(),"adresse invalide",Toast.LENGTH_LONG).show();
        }
    }
    private void askGalleryPermissionLocation() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Fournissez la permission de localisation à Geovax afin d'acceder à votre position";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Permission requise")
                .setSettingsDialogTitle("Attention");

        Permissions.check(this/*context*/, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {

                Toast.makeText(getApplicationContext(), "Permission accordée", Toast.LENGTH_LONG).show();
                getCurrentLocationn();
            }
        });


    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocationn(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Rendez_vous.this);
            try {
                Task location0 = fusedLocationProviderClient.getLastLocation();

                location0.addOnCompleteListener(this,
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful()) {
                                    Location location = task.getResult();
                                    if (location != null) {
                                        LatLng P =new LatLng( location.getLatitude(), location.getLongitude());
                                        Geocoder geocoder = new Geocoder(Rendez_vous.this);
                                        List<Address> list1 = new ArrayList<>();
                                        try {
                                            list1 = geocoder.getFromLocation(P.latitude,P.longitude,1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(list1.size() > 0){
                                            Address address = list1.get(0);

                                            Log.d("GPS",String.valueOf(address.getAddressLine(0)));
                                            mSearchText.setText(address.getAddressLine(0));
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Aucune Adresse", Toast.LENGTH_LONG).show();
                                    }
                                }else{ Toast.makeText(getApplicationContext(), "Une erreur a eu lieu", Toast.LENGTH_LONG).show();}
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Une erreur a eu lieu", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Une erreur a eu lieu", Toast.LENGTH_LONG).show();
        }
    }

}
