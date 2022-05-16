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
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class InfoPersoAvantModif extends Activity {
private Button modifier,generer;
private ImageButton retourpp;
private TextView nom,mail,tel,dose,type,rdv,motdp,age;
Bitmap bmp ,bmpQR,USTHB ;
TextView Nom_prenom;

QRGEncoder qrgEncoder;
int pageWidth =1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(InfoPersoAvantModif.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_perso_avant_modif);

        Intent i =new Intent(InfoPersoAvantModif.this,Info_Perso.class);

        DocumentReference reference;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String currentId;
        nom=findViewById(R.id.nom);
        tel=findViewById(R.id.tel);
        mail=findViewById(R.id.mail);
        dose=findViewById(R.id.dos);
        type=findViewById(R.id.typ);
        rdv=findViewById(R.id.date);
        age=findViewById(R.id.age);
        USTHB=BitmapFactory.decodeResource(getResources(),R.drawable.usthblogo);
        currentId=user.getUid();

        reference=db.collection("user").document(currentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            nom.setText(task.getResult().getString("Nom et Prenom"));
                            tel.setText(task.getResult().getString("Numero de Telephone"));
                            mail.setText(task.getResult().getString("Email"));
                            dose.setText(task.getResult().get("Nombre de doses",Integer.class).toString());
                            type.setText(task.getResult().getString("Type de vaccin"));
                            age.setText(task.getResult().getString("Age"));


                            i.putExtra("nomprenom" ,task.getResult().getString("Nom et Prenom"));
                            i.putExtra("email" ,task.getResult().getString("Email"));
                            i.putExtra("tele" ,task.getResult().getString("Numero de Telephone"));
                            i.putExtra("Type" ,task.getResult().getString("Age"));


                        }else{
                            Toast.makeText(getApplicationContext(),"jcpo",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    modifier =(Button) findViewById(R.id.modifier);
     generer=(Button) findViewById(R.id.generer);
    retourpp=(ImageButton)findViewById(R.id.retourP);
    Nom_prenom=(TextView) findViewById(R.id.nom);

    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.pass);
    //bmpQR = BitmapFactory.decodeResource(getResources(),R.drawable.qr);

        retourpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPersoAvantModif.this,Menu.class));
            }
        });
        generer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int dimen = width < height ? width : height;
                String info =Nom_prenom.getText().toString() +" \n"+ type.getText().toString()+" \n"+ dose.getText().toString();
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


                startActivity(i);

            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void createpdf(){

        String doseS =dose.getText().toString().trim();

        if((Integer.parseInt(doseS)>=2 & !type.equals("Jonson&Jonson"))|| (Integer.parseInt(doseS)>=1 & type.equals("Jonson&Jonson"))){
            PdfDocument passSanitaire = new  PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page maPage =passSanitaire.startPage(pageInfo);
            Canvas canva =maPage.getCanvas();

            Bitmap bmpUSTHB = Bitmap.createScaledBitmap(USTHB,300,300,true);

            canva.drawBitmap(bmpUSTHB,460,50,myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canva.drawText(" Université des Sciences et de la Technologie Houari Boumediene",pageWidth/2,450,titlePaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canva.drawText(" Votre passe sanitaire : ",pageWidth/2,630,titlePaint);

            titlePaint.setTextAlign(Paint.Align.LEFT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canva.drawText(" Nom et Prenom : "+Nom_prenom.getText().toString().trim(),10,720,titlePaint);

            titlePaint.setTextAlign(Paint.Align.LEFT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canva.drawText(" Age : "+age.getText().toString().trim(),10,790,titlePaint);

            titlePaint.setTextAlign(Paint.Align.LEFT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canva.drawText(" Type de vaccin : "+type.getText().toString().trim(),10,870,titlePaint);

            titlePaint.setTextAlign(Paint.Align.LEFT);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(30);
            canva.drawText(" Nombre de dose administre  : "+doseS,10,950,titlePaint);

            Bitmap bmp2 = Bitmap.createScaledBitmap(bmpQR,400,400,true);

            canva.drawBitmap(bmp2,430,1050,myPaint);

            passSanitaire.finishPage(maPage);
  String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/passeSanitaire.pdf";
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
