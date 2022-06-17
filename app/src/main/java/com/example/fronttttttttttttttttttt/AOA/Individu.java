/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.fronttttttttttttttttttt.AOA;

/**
 *
 * @author PICOS
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Individu implements Comparable<Individu>,Cloneable{
    public int id;
    public int A;
    public ArrayList<Double> solution;//[A]
    public ArrayList<Integer> RealSolution;
    public int size;//Size of the solution
    public ArrayList<Double> velocity;
    public double Evaluation;
    public  double  fmin;
    public  double fmax;
    public  double L;
    public  double T;
    public static int S=1500;
    public ArrayList<ArrayList<Double>> matricepositions; //Liste des distances entre les points
    public Individu(int size, double fmin, double fmax, double L, double T,ArrayList<ArrayList<Double>> matricepositions) {
        this.size = size;
        this.fmin = fmin;
        this.fmax = fmax;
        this.L = L;
        this.T = T;
        velocity=new ArrayList<>();
        solution=new ArrayList<>();
        RealSolution=new ArrayList<>();
        for(int i=0;i<size;i++){
            velocity.add(1.0);
            
        }
        solution.add((size+10.0));//plus grande priorité pour la n°1 qui représente l'hopital
        
        for(int i=1;i<size;i++){
            solution.add(Math.random()*size);    
        }
        this.matricepositions=matricepositions;
        Evaluate();
    }
/*
    public Individu(ArrayList<Double> solution, int size, double fmin, double fmax, double L, double T,ArrayList<ArrayList<Double>> matricepositions) {
        this.solution = solution;
        this.size = size;
        this.fmin = fmin;
        this.fmax = fmax;
        this.L = L;
        this.T = T;
        velocity=new ArrayList<>();
        RealSolution=new ArrayList<>();
        for(int i=0;i<size;i++){
            velocity.add(0.0);
        }
        this.matricepositions=matricepositions;
        Evaluate();
    }*/

    public void Evaluate() {
        RealSolution=translateSolution();
        double tmpEval=0.0;
        //System.out.println("Real size "+RealSolution.size());
//        for(int i=0;i<RealSolution.size()-1;i++)
//        {
//            System.out.println("sol "+i+"=" +solution.get(i));
//        }
        for(int i=0;i<RealSolution.size()-1;i++)
        {
//           System.out.println("i = "+RealSolution.get(i));
           tmpEval+=matricepositions.get(RealSolution.get(i)).get(RealSolution.get(i+1));
        }
//        System.out.println("Fin");
         Evaluation=tmpEval;
    }

    public ArrayList<Integer> translateSolution() {
    ArrayList<Integer> ordre=new ArrayList<>();
    double maxpriority=solution.get(0);
    int j=0;
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
    @Override
    public String toString() {
        return "Evaluation : "+Evaluation;
        //+"\nSolutions{" + solution + "\nRealSolution "+ RealSolution+"}\n";
    }
public ArrayList<Integer> translateSolution1() {
        ArrayList<Integer> ordre=new ArrayList<>();
        double maxpriority=solution.get(0);
        int j=0;
        for(int i=0;i<size;i++)
        {
            if(maxpriority<solution.get(i)){
                maxpriority=solution.get(i);
            }
        }
        while(j<size){
            for(int i=0;i<size;i++){
                if(maxpriority==solution.get(i)){
                     ordre.add(i);
                     j++;
                }
            }
            if(j==size) break;
            double oldmaxp=maxpriority;
            maxpriority=solution.get(0);
            int k=0;
            while(maxpriority>=oldmaxp && k<size)
            {
                maxpriority=solution.get(k);
                k++;
            }
            for(int i=0;i<solution.get(i);i++)
            {
                if(maxpriority<solution.get(i) && solution.get(i)<oldmaxp)
                {
                    maxpriority=solution.get(i);
                }
            }
            
        }
        ordre.add(ordre.get(0));
        return ordre;
    }

    public ArrayList<Double> cooperate(Long startTime)
    {
        
        Double freq=getfrequences();
       double L1=S/freq;
       Long Time = System.currentTimeMillis()-startTime;
        ArrayList<Double> t=CalculateTime(Time.doubleValue());// time to go to the new position
        ArrayList<Double> t1=Solutions.Sinus(Solutions.Mult(solution, (double)(2*Math.PI)/(double)L1));
        ArrayList<Double> t2=Solutions.Cosinus(Solutions.Mult(t, (double)(2*Math.PI)/(double)T));
        ArrayList<Double> Res= Solutions.Mult(Solutions.Multiplication(t1, t2),size);
        return Res;
   
    }
    
    
    public void intensification(ArrayList<Double> cooperativeSol,Individu bestpod,Individu bestclan,Individu bestpop) {
        double alpha=Math.random();
//        System.out.println("\n NEW \n");
        if(alpha>0.5){
            //Pods' Cooperation
 
            solution=Solutions.Difference(Solutions.Mult(cooperativeSol, Math.random()),bestpod.solution);
            
        }
        else{
            //Echolocation
            Double fPod=getfrequences();
            Double fClan=getfrequences();
            Double fPop=getfrequences();
    //        System.out.println("fpod "+fPod+"fClan "+fClan+" fpop "+fPop);
            ArrayList<Double> tmpPod=Solutions.Mult(Solutions.abs(Solutions.Difference(bestpod.solution, solution)), fPod);
      //      System.out.println("tmp pod "+tmpPod);
            ArrayList<Double> tmpClan=Solutions.Mult(Solutions.abs(Solutions.Difference(bestclan.solution, solution)), fClan);
        //    System.out.println("tmp clan "+tmpClan);
            ArrayList<Double> tmpPop=Solutions.Mult(Solutions.abs(Solutions.Difference(bestpop.solution, solution)), fPop);
          //  System.out.println("tmp pop "+tmpPop);
            
            velocity=Solutions.Plus(velocity,Solutions.Plus(tmpPod, Solutions.Plus(tmpClan,tmpPop )));
//            System.out.println("velocity "+velocity);
//            System.out.println("before  "+solution); 
//           System.out.println("before  "+RealSolution); 
//           System.out.println("before  "+Evaluation); 
           
            solution=Solutions.Plus(solution,velocity);/**/
//           System.out.println("After  "+solution);
Evaluate();
//           System.out.println("after  "+RealSolution); 
//           System.out.println("after  "+Evaluation); 

        }
//        for(int i=0;i<size;i++){
//            solution.set(i,Math.random()*size);    
//        }
          double max=Collections.max(solution);
          solution.set(0, max+10.0);
//    System.out.println("After After  "+solution);
Evaluate();
//           System.out.println("after  After "+RealSolution); 
//           System.out.println("after After  "+Evaluation); 
      
   
    }
 
    
    
    @Override
    public int compareTo(Individu o) {
        if(Math.abs(o.Evaluation)==Math.abs(Evaluation))
            return 0;
        if(Math.abs(o.Evaluation)<Math.abs(Evaluation))
            return 1;
        return -1;
    
    }

    public Double getfrequences() {
   return Math.random()*(fmax-fmin)+fmin;
    }

    public ArrayList<Double> CalculateTime(double time) {
          ArrayList<Double> un=new ArrayList<>();
          for(Double d:solution){
              un.add(time);
          }
          return  un;
    }
    @Override	
    public Individu clone() throws CloneNotSupportedException {   
        Individu copie = (Individu)super.clone();
        copie.size = new Integer(size);
        copie.solution=(ArrayList<Double>) solution.clone();
        copie.RealSolution=(ArrayList<Integer>) RealSolution.clone();
        copie.Evaluation=new Double(Evaluation);
        copie.matricepositions=(ArrayList<ArrayList<Double>>) matricepositions.clone();
        copie.A=A;
        copie.L=L;
        copie.T=T;
        copie.fmax=fmax;
        copie.fmin=fmin;
        copie.velocity=(ArrayList<Double>)velocity.clone();
        return copie;
    }
    
}
