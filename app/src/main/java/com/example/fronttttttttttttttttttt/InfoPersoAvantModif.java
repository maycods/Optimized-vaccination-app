package com.example.fronttttttttttttttttttt;

import static android.graphics.Color.rgb;
import static android.graphics.Typeface.DEFAULT;

import static java.util.jar.Pack200.Packer.ERROR;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class InfoPersoAvantModif extends Activity {
private Button modifier,generer;
private ImageButton retourpp;
Bitmap bmp ,bmpQR ;
TextView Nom_prenom;
TextView Dose;
TextView Vaccin;
QRGEncoder qrgEncoder;
int pageWidth =1200;

//TODO AFFICHER INFO  PERSO DE BDD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(InfoPersoAvantModif.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_perso_avant_modif);
    modifier =(Button) findViewById(R.id.modifier);
     generer=(Button) findViewById(R.id.generer);
    retourpp=(ImageButton)findViewById(R.id.retourP);
    Nom_prenom=(TextView) findViewById(R.id.Nom_prenom);
    Dose=(TextView) findViewById(R.id.nbrdoses);
    Vaccin=(TextView) findViewById(R.id.vaccin);
    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.pass);
    //bmpQR = BitmapFactory.decodeResource(getResources(),R.drawable.qr);

        retourpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Menu.class));
            }
        });
        generer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int dimen = width < height ? width : height;
                String info =Nom_prenom.getText().toString() +" \n"+ Vaccin.getText().toString()+" \n"+ Dose.getText().toString();
                Log.d("info",String.valueOf(info.trim()));
                dimen = dimen * 3 / 4;
                qrgEncoder = new QRGEncoder(info.trim(), null, QRGContents.Type.TEXT, dimen);
                try {

                    bmpQR = qrgEncoder.encodeAsBitmap();
                    createpdf();

                } catch (WriterException e) {

                    Log.e("Tag", e.toString());
                }
                Log.d("generer","generer");

            }
        });
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Info_Perso.class));

            }
        });

    }



    public  void createpdf(){
        Log.d("kkkk","pass sanitaire");
        String nbrD =Dose.getText().toString().trim();

        String V =Vaccin.getText().toString().trim();
        if((Integer.parseInt(nbrD)>=2 & !Vaccin.equals("Jonson&Jonson"))|| (Integer.parseInt(nbrD)>=1 & Vaccin.equals("Jonson&Jonson"))){
            PdfDocument passSanitaire = new  PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page maPage =passSanitaire.startPage(pageInfo);
            Canvas canva =maPage.getCanvas();
           Bitmap bmp1 = Bitmap.createScaledBitmap(bmp,1200,400,true);
            canva.drawBitmap(bmp1,0,0,myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canva.drawText(" Votre passe sanitaire : ",pageWidth/2,610,titlePaint);

            Bitmap bmp2 = Bitmap.createScaledBitmap(bmpQR,600,600,true);

            canva.drawBitmap(bmp2,300,700,myPaint);

            passSanitaire.finishPage(maPage);
  String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/passeSanitaire.pdf";
    Log.d("file path",String.valueOf(myFilePath));
     File myFile = new File(myFilePath);

            try{
                passSanitaire.writeTo(new FileOutputStream(myFile));
                Toast.makeText(this,"Votre passe sanitaire a bien ete generer", Toast.LENGTH_LONG).show();
            }catch (IOException e){
                e.printStackTrace();
            }
            passSanitaire.close();
        }
        else {
             Toast.makeText(this,"vous n'avez pas attient le nombre requie de dose administre pour avoir un passe sanitaire ", Toast.LENGTH_LONG).show();
        }


    }
}
