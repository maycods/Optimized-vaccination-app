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
private TextView nom,mail,tel,dose,type,rdv,motdp;
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
        motdp=findViewById(R.id.mdpp);
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
                            motdp.setText(task.getResult().getString("Mot de passe"));

                            i.putExtra("nomprenom" ,task.getResult().getString("Nom et Prenom"));
                            i.putExtra("email" ,task.getResult().getString("Email"));
                            i.putExtra("tele" ,task.getResult().getString("Numero de Telephone"));
                            i.putExtra("mdp" ,task.getResult().getString("Mot de passe"));

                        }else{
                            Toast.makeText(getApplicationContext(),"jcpo",Toast.LENGTH_LONG).show();
                        }
                    }
                });


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


                startActivity(i);

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
