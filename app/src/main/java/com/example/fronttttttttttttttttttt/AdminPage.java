package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.Menu.SCROLL_DELTA;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AdminPage extends Activity implements AdapterView.OnItemSelectedListener {
    private ImageButton L,r;
    private HorizontalScrollView scrollView;
    public static  int SCROLL;
    DisplayMetrics displayMetric = new DisplayMetrics();
    //TODO AFFICHER HOPITEAU INFO DE BDD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
       Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.IDamb, R.layout.spinn);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//TODO AFFECTATION
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
