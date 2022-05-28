package com.example.fronttttttttttttttttttt;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.fronttttttttttttttttttt.Menu.SCROLL_DELTA;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

public class AdminPage extends Activity  implements AdapterView.OnItemSelectedListener {
    private ImageButton L,r;
    private LinearLayout top;
    private HorizontalScrollView scrollView;
    public static  int SCROLL;
    private Button cfrm;
    private Spinner A;
    int pos=0;
    RecyclerView recyclerView,recyf;
    AdapterR myAdapter;
    FirebaseFirestore db;
    GeoPoint geo;
    ArrayList<RDVV> RDV;
    ArrayList<Hopital> listH;
    AdapterH AdapterH;
    private Spinner V;
    String ambulance=null,Ambulance;
    DisplayMetrics displayMetric = new DisplayMetrics();
    private ArrayList<RDVV> listAR=new ArrayList<RDVV>();
    RelativeLayout dv;


    //TODO AFFICHER HOPITEAU INFO DE BDD

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        dv =(RelativeLayout)findViewById(R.id.divmenu) ;
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);
        //top =(LinearLayout) findViewById(R.id.hopinf);

        recyf=findViewById(R.id.recyT);
        recyf.setHasFixedSize(false);
        recyf.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerView=findViewById(R.id.recy0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        db = FirebaseFirestore.getInstance();
        RDV = new ArrayList<RDVV>();
        listH = new ArrayList<Hopital>();

        myAdapter=new AdapterR(AdminPage.this ,RDV,db);
        recyclerView.setAdapter(myAdapter);


        AdapterH =new AdapterH(AdminPage.this ,listH,db);
        recyf.setAdapter(AdapterH);
        EventChangeListener();

        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=listH.size()){
                    recyf.scrollToPosition(pos);
                    pos++;}
                return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=-1){
                    recyf.scrollToPosition(pos);
                    pos--;}
                return;
            }
        });



    }



    @RequiresApi(api = Build.VERSION_CODES.O)

    private void EventChangeListener(){
        LocalDate aujourhui = LocalDate.now().plusDays(1);
        String date1 = aujourhui +" "+"00:00:00";
        Timestamp timestamp = Timestamp.valueOf(date1);
        db.collection("Rendez-vous").whereEqualTo("dateR",timestamp).whereEqualTo("confR",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {

                    String address="";
                    geo= (GeoPoint) documentChange.getDocument().get("Localisation");
                    Geocoder geocoder = new Geocoder(AdminPage.this);
                    List<Address> list1 = new ArrayList<>();
                    try {
                        list1 = geocoder.getFromLocation(geo.getLatitude(),geo.getLongitude(),1);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if(list1.size() > 0){
                        address = list1.get(0).getAddressLine(0);

                    }
                    String IDP =  documentChange.getDocument().getData().get("IDP").toString();
                    String typeV =  documentChange.getDocument().getData().get("Type de vaccin").toString();
                    String IDR =  documentChange.getDocument().getId();
                    Log.d("adddddd",address);
                    RDVV A = new RDVV(IDR,typeV,address,IDP);
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {

                        RDV.add(A);
                        myAdapter.notifyDataSetChanged();


                    }

                }
            }
        });
        db.collection("Hopital").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    listH.add(documentChange.getDocument().toObject(Hopital.class));
                    AdapterH.notifyDataSetChanged();
//                    String Nomh,nbAH,doseSP,doseAS,doseJJ,doseSN;
//                    Nomh = documentChange.getDocument().getId();
//                    nbAH =  documentChange.getDocument().get("nbA").toString();
//                    doseAS= documentChange.getDocument().get("AstraZeneka").toString();
//                    doseSP= documentChange.getDocument().get("Sputnik V").toString();
//                    doseJJ= documentChange.getDocument().get("Johnson & Johnson").toString();
//                    doseSN= documentChange.getDocument().get("Sinovac-CoronaVac").toString();
//                    Hopital H = new Hopital(Nomh,doseSP,doseAS,doseJJ,doseSN,nbAH);
//
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
