/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.fronttttttttttttttttttt.AOA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author PICOS
 */
public class Pod implements Comparable<Pod>,Cloneable{
    public int nIndividus;
    //individualDistance represents  the mesure that will create the differents values of each individu
    public int individualDistance;
    
    //the List of our Individuals in this pod 
    public LinkedList<Individu> solutions; 

    public double meanEval;
    public ArrayList<Double> cooperativeSolution;
    
    public Pod(Individu solution,int nIndividus,int individualDistance) {
        this.solutions = new LinkedList<Individu>();
        this.solutions.add(solution);
        this.nIndividus=nIndividus;
        this.individualDistance=individualDistance;
        generateSolutions();
    }
    
    public void generateSolutions() {
        Individu I=solutions.get(0);
        for(int i=1;i<nIndividus;i++){
            Individu s=new Individu(I.size, I.fmin, I.fmax, I.L, I.T, I.matricepositions);
            solutions.add(s);
       }
    }

    public int getnIndividus() {
        return nIndividus;
    }

    public int getIndividualDistance() {
        return individualDistance;
    }
    
   
    public Individu getFirst(){
        return solutions.getFirst();
    }

    public LinkedList<Individu> getSolutions() {
        return solutions;
    }
    
    @Override
    public String toString() {
        String S="";
        return "Pod{" + "\n\t\t" + solutions + "\n}\n";
    }

    Individu getBest() {
    
        Collections.sort(solutions);
        return solutions.getFirst();
    }

    void intensificationSearch(long startTime,Population p,Clan c) throws CloneNotSupportedException {
        //get best individual of the current pod
        Individu bestpod=getBest().clone();
        //get best individual of the clan to which the current pod belongs
        Individu bestClan=c.getBest().clone();
        //get best individual of the population
        Individu bestPopulation=p.getBest().clone();
        
        /********/
        //calculate the cooperative behaviour of the current pod 
        ArrayList<Double> cooperativeSol=new ArrayList<>();
        
        for(Double d:solutions.getFirst().solution)
            cooperativeSol.add(0.0);
        
        for (Individu s:this.solutions){
            cooperativeSol=Solutions.Plus(cooperativeSol, s.cooperate(startTime));
        }
        cooperativeSol=Solutions.divide(cooperativeSol, solutions.size());
        /*******/
        //lunch the intensification search for each individual of the pod
        for(Individu s:this.solutions){
           
            s.intensification(cooperativeSol,bestpod,bestClan,bestPopulation);
          
        }
        
    }
public double meanCost() {
        double sum=0.0;
        for(Individu i:solutions){
            sum+=i.Evaluation;
        }
        return (sum/(double)solutions.size());
    }
    @Override
    public int compareTo(Pod o) {
        if(this.meanCost()==o.meanCost())
            return 0;
        if(Math.abs(this.meanCost())<Math.abs(this.meanCost()))
            return -1;
        return 1;
    }

    void sortPod() {
        Collections.sort(solutions);
    }

    ArrayList<Double> diversificationPhase(Clan c,Population p) throws CloneNotSupportedException {
   
       
        LinkedList<Individu> c1=c.getSolution(),p1=p.getSolution();
        int rand2=(int) (Math.random()*(c1.size())),rand1=(int) (Math.random()*(p1.size()));
        Individu clanIndiv=c1.get(rand2);
        Individu PopIndiv=p1.get(rand1);
       
        double gamma=Math.random(),omega=Math.random();
         ArrayList<Double> multrandClan =Solutions.Mult(clanIndiv.solution, omega);
         ArrayList<Double> multrandPop =Solutions.Mult(PopIndiv.solution, gamma);
         ArrayList<Double> plusNew =Solutions.Plus(multrandClan, multrandPop);
         ArrayList<Double> div=Solutions.divide(plusNew, 2.0);
         
        return div;
        
        
        
        
        
    }
    void diversification(Clan c, Population pop) throws CloneNotSupportedException {
        for(Individu I:solutions){
            ArrayList<Double> newSol=diversificationPhase(c, pop);
            double max=Collections.max(newSol);
            newSol.set(0, max+10.0);
            if(Solutions.evaluate(newSol, I.matricepositions)<I.Evaluation){//if and only if the new solution is better then the previous one
                I.solution=newSol;            
            }
        }
    }

   void update() {
        Collections.sort(solutions);
    }

}
