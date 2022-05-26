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
    RecyclerView recyclerView;
    AdapterR myAdapter;
    FirebaseFirestore db;
    GeoPoint geo;
    ArrayList<RDVV> RDV;
    ArrayList<Hopital> listH;
    private Spinner V;
    String ambulance=null,Ambulance;
    DisplayMetrics displayMetric = new DisplayMetrics();
    private ArrayList<String> listV=new ArrayList<String>();
    RelativeLayout dv;


    //TODO AFFICHER HOPITEAU INFO DE BDD

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        cfrm=findViewById(R.id.inscritt);
        dv =(RelativeLayout)findViewById(R.id.divmenu) ;
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);
        top =(LinearLayout) findViewById(R.id.hopinf);


        recyclerView=findViewById(R.id.recy0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        db = FirebaseFirestore.getInstance();
        RDV = new ArrayList<RDVV>();
        myAdapter=new AdapterR(AdminPage.this ,RDV);
        recyclerView.setAdapter(myAdapter);
        db.collection("Hopital").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    //L1
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    LinearLayout lt = new LinearLayout(getApplicationContext());
                    lt.setPadding(38,60,0,0);
                    if (documentChange.getDocument().get("NomH").toString().equals("Kouba")){
                        lt.setPadding(0,60,140,0);
                    }
                    lt.setLayoutParams(params);
                    lt.setOrientation(LinearLayout.VERTICAL);
                    //NomH
                    TextView T = new TextView(getApplicationContext());
                    T.setText(documentChange.getDocument().get("NomH").toString());
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params1.setMargins(0,0,25,0);
                    T.setLayoutParams(params1);
                    T.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                    T.setTextColor(Color.rgb(255,255,255));
                    T.setTextSize(40);
                    lt.addView(T);
                    //L2
                    LinearLayout lt2 = new LinearLayout(getApplicationContext());
                    params.setMargins(5,0,10,0);
                    lt2.setLayoutParams(params);
                    lt2.setOrientation(LinearLayout.VERTICAL);

                    TextView NBA = new TextView(getApplicationContext());
                    NBA.setText("Ambulanceier : "+documentChange.getDocument().get("nbA"));
                    NBA.setLayoutParams(params1);
                    NBA.setPadding(0,10,0,15);
                    NBA.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                    NBA.setTextColor(Color.rgb(255,255,255));
                    NBA.setTextSize(17);
                    lt2.addView(NBA);

                    TextView Sp = new TextView(getApplicationContext());
                    Sp.setText("sputnik : "+documentChange.getDocument().get("DoseSpootnik").toString());
                    Sp.setLayoutParams(params1);
                    Sp.setPadding(0,0,0,5);
                    Sp.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                    Sp.setTextColor(Color.rgb(255,255,255));
                    Sp.setTextSize(15);
                    lt2.addView(Sp);

                    TextView AS = new TextView(getApplicationContext());
                    AS.setText("AstraZeneka : "+documentChange.getDocument().get("DoseAstra").toString());
                    AS.setLayoutParams(params1);
                    AS.setPadding(0,0,0,5);
                    AS.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                    AS.setTextColor(Color.rgb(255,255,255));
                    AS.setTextSize(15);
                    lt2.addView(AS);

                    TextView JS = new TextView(getApplicationContext());
                    JS.setText("Johnson & Johnson : "+documentChange.getDocument().get("DoseAstra").toString());
                    JS.setLayoutParams(params1);
                    JS.setPadding(0,0,0,5);
                    JS.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                    JS.setTextColor(Color.rgb(255,255,255));
                    JS.setTextSize(15);
                    lt2.addView(JS);

                    TextView SV = new TextView(getApplicationContext());
                    SV.setText("Astrazenica : "+documentChange.getDocument().get("DoseAstra").toString());
                    SV.setLayoutParams(params1);
                    SV.setPadding(0,0,0,5);
                    SV.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                    SV.setTextColor(Color.rgb(255,255,255));
                    SV.setTextSize(15);
                    lt2.addView(SV);


                    lt.addView(lt2);
                    top.addView(lt);
                }
            }
        });



        EventChangeListener();

        cfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Ambulance=ambulance;
            Toast.makeText(getApplicationContext(),String.valueOf(Ambulance),Toast.LENGTH_LONG).show();
            }
        });
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = scrollView.getScrollX();
                if(x - SCROLL_DELTA >= 0) {
                    scrollView.scrollTo(x-SCROLL, scrollView.getScrollY());
                }return;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxAmount = 4000;

                int x =scrollView.getScrollX();
                if(x + SCROLL_DELTA <= maxAmount) {
                    scrollView.scrollTo(x+SCROLL, scrollView.getScrollY());
                }
                return;
            }
        });



    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {

//TODO AFFECTATION

        Spinner t2 =(Spinner) adapterView;
        ambulance = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    private void EventChangeListener(){


        LocalDate aujourhui = LocalDate.now().plusDays(1);
        String date1 = aujourhui +" "+"00:00:00";
        Timestamp timestamp = Timestamp.valueOf(date1);
        Log.d("loccccc",String.valueOf(timestamp));
            db.collection("Rendez-vous").whereEqualTo("dateR",timestamp).whereEqualTo("confR",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    String address="";
                     HashMap<String, Object> RDVT = new HashMap<String, Object>();

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
                    RDVV A = new RDVV(IDR,typeV,address);
                    RDV.add(A);
                    myAdapter.notifyDataSetChanged();


                }
            }
        });
        db.collection("Ambulancier").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    String NOM = documentChange.getDocument().getData().get("Nom et Prenom").toString();
                    listV.add(NOM);
                    RDVV A = new RDVV();
                    A.setListV(listV);

                }
            }
        });

    }

}
