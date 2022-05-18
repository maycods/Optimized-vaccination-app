package com.example.fronttttttttttttttttttt;

import static com.example.fronttttttttttttttttttt.Menu.SCROLL_DELTA;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Field;

public class AdminPage2 extends Activity  implements NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {
    private ImageButton L,r;
    private HorizontalScrollView scrollView;
    public static  int SCROLL;
    private NumberPicker npick;
    private Button cfrm;
    DisplayMetrics displayMetric = new DisplayMetrics();
    //TODO AFFICHER HOPITEAU INFO DE BDD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin2);
        npick=findViewById(R.id.hourpicker);
        setNumberPickerTextColor(npick,R.color.black);
        init();
        cfrm=findViewById(R.id.inscrittt);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrl);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        SCROLL = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayMetric.widthPixels/2-50 , getResources().getDisplayMetrics());
        r = (ImageButton) findViewById(R.id.droite);
        L = (ImageButton) findViewById(R.id.gauche);
        cfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a;
                if(npick.getValue() == 0){
                  a=999;
                }else{
                a=npick.getValue()-1;}
                Toast.makeText (getApplicationContext(), String.valueOf(a), Toast.LENGTH_LONG).show();
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


    private void init() {
        npick.setOnValueChangedListener(this);
     npick.setOnScrollListener(this);
        npick.setMaxValue(999);
        npick.setMinValue(0);
        npick.setValue(0);
    }
        public static void setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {

        try{
            @SuppressLint("SoonBlockedPrivateApi") Field selectorWheelPaintField = numberPicker.getClass()
                    .getDeclaredField("mSelectorWheelPaint");
            selectorWheelPaintField.setAccessible(true);
            ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
        }
        catch(NoSuchFieldException e){
            Log.w("set", e);
        }
        catch(IllegalAccessException e){
            Log.w("set", e);
        }
        catch(IllegalArgumentException e){
            Log.w("set", e);
        }

        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText)
                ((EditText)child).setTextColor(color);
        }
        numberPicker.invalidate();
    }

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {
      /*  switch (i) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:

                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                Toast.makeText (this, "no sliding", Toast.LENGTH_LONG).show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

                break;
        }*/
    }
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}
