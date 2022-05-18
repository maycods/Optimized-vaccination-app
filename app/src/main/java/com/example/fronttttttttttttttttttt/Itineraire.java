package com.example.fronttttttttttttttttttt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.List;

public class Itineraire extends FragmentActivity implements RoutingListener {//TODO AFFICHER NOMBRE DE DOSES
    private Button annuler,itineraire;
    GoogleMap map;
//    private List<Polyline> polylines;

    private FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //polylines = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        setContentView(R.layout.itineraire);
    annuler=(Button)findViewById(R.id.annuler);
    itineraire=(Button)findViewById(R.id.itin);
    annuler.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Itineraire.this,Ambulence.class));

        }
    });
        itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Itineraire.this, Map.class));
            }
        });
    }
    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
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
                // do your task.
                getCurrentLocationn();
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_LONG).show();

            }
        });


    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocationn(){

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Itineraire.this);

            try {

                Task location0 = fusedLocationProviderClient.getLastLocation();

                location0.addOnCompleteListener(this,
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful()) {
                                    Location location = task.getResult();
                                    if (location != null) {
                                        mapFragment.getMapAsync(new OnMapReadyCallback(){
                                            @Override
                                            public void onMapReady(@NonNull GoogleMap googleMap){
                                                map =googleMap;
//                                                MarkerOptions src ;
//                                                LatLng ok =new LatLng( location.getLatitude(), location.getLongitude());
//                                                map.moveCamera(CameraUpdateFactory.newLatLng(ok));
//                                                src =new MarkerOptions().position(ok).title("am here");
//                                                map.addMarker(src);

                                            }});

                                    } else {
                                        Toast.makeText(getApplicationContext(), "no location", Toast.LENGTH_LONG).show();
                                    }




//
                                }else{ Toast.makeText(getApplicationContext(), "no success", Toast.LENGTH_LONG).show();}


                            }
                        });

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRoutingCancelled() {

    }

}
