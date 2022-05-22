package com.example.fronttttttttttttttttttt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class Item extends Activity implements NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {
    private NumberPicker npick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        npick=findViewById(R.id.hourpicker);
        setNumberPickerTextColor(npick,R.color.black);
        npick.setOnValueChangedListener(this);
        npick.setOnScrollListener(this);
        npick.setMaxValue(999);
        npick.setMinValue(0);


    }

    public static void setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        Log.d("lolo",numberPicker.toString());
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
    public void onScrollStateChange(NumberPicker numberPicker, int i) {}

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {}

}
