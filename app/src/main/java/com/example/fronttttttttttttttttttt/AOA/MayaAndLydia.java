/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.fronttttttttttttttttttt.AOA;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author PICOS
 */
public class MayaAndLydia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        // TODO code application logic here
/******************************************/
//TO DO MAYA AND LYDIA 

        ArrayList<LatLng> positions=new ArrayList<>();


        positions.add(new LatLng(1,2));//Hopital doit tjrs etre le premier de la liste
        positions.add(new LatLng(3,4));
        positions.add(new LatLng(4,6));
        positions.add(new LatLng(4,2));
        positions.add(new LatLng(3,6));
        positions.add(new LatLng(6,6));
        positions.add(new LatLng(1,1));
        positions.add(new LatLng(2,5));
        positions.add(new LatLng(5,6));
        positions.add(new LatLng(2,4));
        positions.add(new LatLng(1,4));

        //Construction de la matrice des distances
      /*  int size=11;
        ArrayList<ArrayList<Double>> matricepositions=new ArrayList<>();
        for( LatLng l:positions){
            ArrayList<Double> tmp=new ArrayList<>();
            for( LatLng m:positions){
                tmp.add(l.Distance(m));
            }
            matricepositions.add(tmp);
        }
        int nclan=2,npod=2,nidiv=5;
        double fmin=0,fmax=1,T=1000,L=100;
        int maxIter=20;
        AOA a=new AOA(nclan,npod,nidiv,fmin,fmax,L,T,maxIter,size,matricepositions);
        a.p.evaluate();
        System.out.println(a.p.getBest().RealSolution);*/
//        for(int i=0;i<a.getBestSolutions().size();i++)
//        {
//            System.out.println("i ="+i+" best "+a.BestSols.get(i).Evaluation);
//        }

//Tests();

    }/*
    public static void Tests() throws IOException, CloneNotSupportedException{
        ArrayList<LatLng> positions=new ArrayList<>();
         positions.add(new LatLng(1,2));//Hopital doit tjrs etre le premier de la liste
        positions.add(new LatLng(3,4));
        positions.add(new LatLng(4,6));
        positions.add(new LatLng(4,2));
        positions.add(new LatLng(3,6));
        positions.add(new LatLng(6,6));
        positions.add(new LatLng(1,1));
        positions.add(new LatLng(2,5));
        positions.add(new LatLng(5,6));
        positions.add(new LatLng(2,4));
        positions.add(new LatLng(1,4));

        //Construction de la matrice des distances
        int size=11;
        ArrayList<ArrayList<Double>> matricepositions=new ArrayList<>();
        for( LatLng l:positions){
            ArrayList<Double> tmp=new ArrayList<>();
            for( LatLng m:positions){
                tmp.add(l.Distance(m));
            }
            matricepositions.add(tmp);
        }
        
        
        
        ArrayList<Integer> Clan=new ArrayList<>(),Pod=new ArrayList<>(),Orca=new ArrayList<>();
        Clan.add(2);Clan.add(3);Clan.add(4);Clan.add(5);
        Pod.add(2);Pod.add(3);Pod.add(4);Pod.add(5);
        Orca.add(2);Orca.add(3);Orca.add(4);Orca.add(5);
        ArrayList<Double> Min=new ArrayList<>(),Max=new ArrayList<>(),T=new ArrayList<>(),L=new ArrayList<>();
        Min.add(0.0);Min.add(1.0);Min.add(2.0);Min.add(3.0);Min.add(10.0);Min.add(100.0);
        Max.add(1.0);Max.add(2.0);Max.add(3.0);Max.add(4.0);Max.add(100.0);Max.add(1000.0);
        T.add(0.0);T.add(10.0);T.add(100.0);T.add(1000.0);
        L.add(0.0);L.add(10.0);L.add(100.0);L.add(1000.0);
        ArrayList<Integer> MIter=new ArrayList<>();
        MIter.add(5);MIter.add(10);MIter.add(15);MIter.add(20);MIter.add(50);MIter.add(100);
        
        createFolder("AOA");
        File write=createFile("AOA/Resultats.csv");
        ecrireFichier("nb;Nb Clan;Nb Pod;Nb Orca; Fmin;Fmax;T;L;MaxIter;Moyenne;Best;Worst\n",
                        "AOA/Resultats.csv",write);
        
        File writeSols=createFile("AOA/Solution.csv");
        ecrireFichier("nb;Evaluation;solution;RealSoution\n",
                        "AOA/Solution.csv",writeSols);
        
        int lydia=1;
        for(int c :Clan){
            for(int p:Pod){
                for(int o:Orca){
                    for(double minF:Min){
                        for(double maxF:Max){
                            for(double t:T){
                                for(double l:L){
                                    for(int iter:MIter){
                                        //Run 10 execution for each param
                                        ArrayList<Double> Resultat=new ArrayList<>();
                                        for(int i=0;i<10;i++){
                                            AOA a=new AOA(c,p,o,minF,maxF,l,t,iter,size,matricepositions);
                                            a.p.evaluate();
                                            Individu Ind=a.getBestSolution();
                                            Resultat.add(Ind.Evaluation);
                                            ecrireFichier(lydia+";"+Ind.Evaluation+";"+Ind.solution+";"+Ind.RealSolution+"\n",
                                                "AOA/Solution.csv",writeSols);
                                        }
                                        
                                        
        ecrireFichier(lydia+";"+c+";"+p+";"+o+";"+minF+";"+maxF+";"+t+";"+l+";"+iter+";"+Average(Resultat)+";"+Collections.min(Resultat)+";"+Collections.max(Resultat)+"\n",
                        "AOA/Resultats.csv",write);
        lydia++;
        
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
       
         
           
        
        
    }
    static void createFolder(String name) throws IOException {
    	File newDirectory = new File(name);
    	boolean isCreated = newDirectory.mkdirs();
    	
    }
    
    static File createFile(String name) throws IOException {
    	 File scmp = new File(name); 
         boolean isCreated = scmp.createNewFile();
         

    	 return scmp;
    }
    public static void ecrireFichier(String texte,String fileName,File csvFile ) throws FileNotFoundException
        {
         if (!csvFile.exists()) 
             throw new FileNotFoundException("Le fichier n'existe pas"); 
         else{
             PrintStream l_out = new PrintStream(new FileOutputStream(fileName,true)); 
             l_out.print(texte); 
             //String newline = System.getProperty("line.separator");
             //l_out.println(newline);
             l_out.flush(); 
             l_out.close(); 
             l_out=null;
         }
        }
    private static double Average(ArrayList<Double> values1) {
        double sum=0;
        for(int i=0;i<values1.size();i++){
            sum+=values1.get(i);
        }
        return sum/(1.0*values1.size());
    }
*/
}
//        positions.add(new LatLng(36.7223074,3.0621774));//Hopital de kouba
//        positions.add(new LatLng(36.7125247,3.1798528));
//        positions.add(new LatLng(36.7114255,3.1989274));
//        positions.add(new LatLng(36.7433498,3.2754037));
//        positions.add(new LatLng(36.7361072,3.0914457));
//        positions.add(new LatLng(36.7240101,3.1955769));
//        positions.add(new LatLng(36.7417903,3.1242093));
//        positions.add(new LatLng(36.739297,3.1381675));
//        positions.add(new LatLng(36.7418833,3.3334813));
//        positions.add(new LatLng(36.7322455,3.1692202));
//        positions.add(new LatLng(36.7409731,3.1996747));
