/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.fronttttttttttttttttttt.AOA;

import java.util.ArrayList;

/**
 *
 * @author PICOS
 */
class Solutions {
    public static ArrayList<Double> divide(ArrayList<Double> ind, double d) {
          ArrayList<Double> result=new ArrayList<>();
        int j;
        for(j=0;j<ind.size();j++){
              //result.add(ind.get(j)/d);
        
              result.add(Math.random()*ind.size());
        }
        
        return result;
    }
    public static ArrayList<Double> Cosinus(ArrayList<Double> ind){
        ArrayList<Double> d=new ArrayList<>();
            
        for(int i=0;i<ind.size();i++){
            d.add(Math.cos(ind.get(i)));
        }
        return d;
    }
    public static ArrayList<Double> Sinus(ArrayList<Double> ind){
        ArrayList<Double> d=new ArrayList<>();
            
        for(int i=0;i<ind.size();i++){
            d.add(Math.sin(ind.get(i)));
        }
        return d;
    }
     public static ArrayList<Double> Mult(ArrayList<Double> r, double d) {
        ArrayList<Double> result=new ArrayList<>();
        for(Double r1:r){
            result.add(d*r1);
        }
        return result;
        
    }
    public static ArrayList<Double> Multiplication(ArrayList<Double> Intermediaire,ArrayList<Double> r) {
        
        ArrayList<Double> result=new ArrayList<>();
        int j=0;
        for(j=0;j<Intermediaire.size();j++){
            result.add(Intermediaire.get(j)*r.get(j));
            
        }
        
        return result;
    }
public static ArrayList<Double> Difference( ArrayList<Double> first,  ArrayList<Double> individual) {
        ArrayList<Double> d=new ArrayList<>();
            
        for(int i=0;i<first.size();i++){
            d.add((individual.get(i))-first.get(i));
        }
            
        return d;
    }
   public static ArrayList<Double> abs(ArrayList<Double> Difference) {
        ArrayList<Double> result=new ArrayList<>();
        for(Double e:Difference){
            result.add(Math.abs(e));
        }
        return result;
    }
public static  ArrayList<Double> Plus( ArrayList<Double> Solutionsal, ArrayList<Double> Valeurs) {
     
        ArrayList<Double> result=new ArrayList<>();
        for(int i=0;i<Solutionsal.size();i++){
            result.add(Solutionsal.get(i)+Valeurs.get(i));
        }
        
        return result;
    }

public static double DistanceHamming(ArrayList<Integer> a1,ArrayList<Integer> a2,int size){
    int nb=0;
    for(int i=0;i<size;i++){
        if(a1.get(i)!=a2.get(i))
            nb++;
    }
    return nb;
}
public static double distancearray(ArrayList<Double> a1,ArrayList<Double> a2,int size)
{
    double nb=0.0;
    for(int i=0;i<size;i++){
        
        double v=a1.get(i)-a2.get(i);
        if(v<0)
            nb+=(-1*v);
        else
            nb+=v;
    }
    return nb;
}
public static ArrayList<Double> generatefirstrandomSolution(int size)
{
    ArrayList<Double> tmpsol=new ArrayList<Double>();
    for(int i=0;i<size;i++)
    {
        tmpsol.add(Math.random()*size);
    }
    return tmpsol;
}

    static double evaluate(ArrayList<Double> newSol, ArrayList<ArrayList<Double>> matricepositions) {
        ArrayList<Integer> RealSolution=translateSolution(newSol);
        double tmpEval=0.0;
        //System.out.println("Realsolution size "+RealSolution.size());
        for(int i=0;i<newSol.size();i++)
        {
            tmpEval+=matricepositions.get(RealSolution.get(i)).get(RealSolution.get(i+1));
        }
        return tmpEval;
    }
    
    public static  ArrayList<Integer> translateSolution(ArrayList<Double> solution) {
            ArrayList<Integer> ordre=new ArrayList<>();
    double maxpriority=solution.get(0);
    int j=0;
    int size=solution.size();
    for(int i=0;i<size;i++){
        if(maxpriority<solution.get(i)){
            maxpriority=solution.get(i);
        }
    }
    while(j<size){
        for(int i=0;i<size;i++){
            if(maxpriority==solution.get(i)){
                ordre.add(i);
                //cout<<"j"<<j<<" max priority"<< maxpriority<<endl;
                j++;
            }
        }
        if(j==size) break;
        double oldMaxPriority=maxpriority;
        maxpriority=solution.get(0);
        int k=0;
        while(maxpriority>=oldMaxPriority && k<size){
            maxpriority=solution.get(k);
            k++;
        }
        for(int i=0;i<size;i++){
            if(maxpriority<solution.get(i) && solution.get(i)<oldMaxPriority ){
                maxpriority=solution.get(i);
            }
        }
    }
    ordre.add(ordre.get(0));
    return ordre;

    }

}
