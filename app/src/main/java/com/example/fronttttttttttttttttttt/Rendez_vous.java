package com.example.fronttttttttttttttttttt;
import static android.service.controls.ControlsProviderService.TAG;
import static java.lang.Integer.parseInt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.metrics.Event;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
import com.google.protobuf.Value;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

// TODO: 21/05/2022  si possible rendre calendrier fixe apres confirmation
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
        Spinner spinner = findViewById(R.id.spinner1);
        spinner.setEnabled(false);
        spinner.setClickable(false);
         V = findViewById(R.id.spinner1);


//idpatient
        DocumentReference reference;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();


//vaccin
        db.collection("Vaccin").document("IDV").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult().exists() && task.getResult() != null)
                {
                    listV.addAll ((ArrayList<String>) task.getResult().get("TypeVaccin"));

                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.spinn,
                        listV);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        reference=db.collection("user").document(currentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            typeV = (String) task.getResult().get("Type de vaccin");
                            Log.d("wow", String.valueOf(typeV));
                            if (typeV==""){
                                V.setEnabled(true);
                                spinner.setAdapter(adapter);
                                V.setBackgroundColor(0x00000000);
                                V.setBackgroundResource(R.drawable.bouton2);
                                V.setOnItemSelectedListener(Rendez_vous.this);

                        }
                            else {
                                V.setSelection(listV.indexOf(typeV));
                                V.setEnabled(false);
                                spinner.setAdapter(adapter);
                                V.setBackgroundColor(0x00000000);
                                V.setBackgroundResource(R.drawable.bouton2);

                                RDV.put("Type de vaccin", typeV);
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
                        jourj = dayContainerModel.getYear() + "-0" + String.valueOf(dayContainerModel.getMonthNumber()+1) + "-" + dayContainerModel.getDay();
                    }else{
                        jourj = dayContainerModel.getYear() + "-" + String.valueOf(dayContainerModel.getMonthNumber()+1)+ "-" + dayContainerModel.getDay();
                    }
                    LocalDate  j = LocalDate.parse(jourj);


                    if(aujourhui.isAfter(j) || aujourhui.isEqual(j)){
                        Toast.makeText(getApplicationContext(), "vous pouvez pas prendre un rendez-vous pour aujourd hui ou avant", Toast.LENGTH_LONG).show();

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
                  mSearchText.setError("ce champ est obligatoire");
                  mSearchText.requestFocus();
                  return;
              }
              if(date1.isEmpty()){
                  Toast.makeText(getApplicationContext(), "inserer une date correcte", Toast.LENGTH_LONG).show();
                  return;
              }
              db.collection("Rendez-vous").whereEqualTo("IDP",currentId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                  @Override
                  public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                      for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                      {
                          if(documentChange.getDocument().getId().isEmpty()){
                              RDV.put("IDP",currentId);
                              GeoPoint geo = new GeoPoint(Position.latitude,Position.longitude);
                              RDV.put("Localisation",geo);
                              RDV.put("confR",false);
                              RDV.put("confV",false);

                              db.collection("Rendez-vous").document()
                                      .set(RDV)
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              Toast.makeText(getApplicationContext(), "le rendez-vous a été prit ",
                                                      Toast.LENGTH_SHORT).show();
                                              mSearchText.setFocusable(false);
                                              V.setEnabled(false);
                                              spinner.setAdapter(adapter);
                                              V.setBackgroundColor(0x00000000);
                                              V.setBackgroundResource(R.drawable.bouton2);
                                              V.setOnItemSelectedListener(Rendez_vous.this);
                                              V.setSelection(listV.indexOf(choixV));
                                              calenderEvent.requestFocus();

                                          }
                                      })
                                      .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Log.w("Fail", "Error", e);

                                          }
                                      });
                          }
                          else {
                              Toast.makeText(getApplicationContext(), "le rendez-vous  deja a ete prit ",
                                      Toast.LENGTH_SHORT).show();
                          }
                      }}
              });

              //j jours
              //Position pos
              //
                                                   }
      });

   }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String currentId=user.getUid();        Spinner Spinner =(Spinner) adapterView;
        if(Spinner.getId() == R.id.spinner1) {
            choixV = (String) adapterView.getItemAtPosition(i);
            Log.d("je sui",String.valueOf(choixV));
            V.setSelection(listV.indexOf(choixV));
            RDV.put("Type de vaccin",choixV);
            db.collection("user").document(currentId).update("Type de vaccin", choixV);
        }
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
            Toast.makeText(getApplicationContext(),"invalid location",Toast.LENGTH_LONG).show();
        }
    }
    private void askGalleryPermissionLocation() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide location permission so that you can use the app or smthg";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Permission required")
                .setSettingsDialogTitle("big Warning");

        Permissions.check(this/*context*/, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                getCurrentLocationn();
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_LONG).show();

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
                                        Toast.makeText(getApplicationContext(), "no location", Toast.LENGTH_LONG).show();
                                    }
                                }else{ Toast.makeText(getApplicationContext(), "no success", Toast.LENGTH_LONG).show();}
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_LONG).show();
            }
        }else{
        }
    }

}
