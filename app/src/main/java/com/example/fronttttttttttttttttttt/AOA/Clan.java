/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.fronttttttttttttttttttt.AOA;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author PICOS
 */
public class Clan implements Comparable<Clan>,Cloneable {
     //this class represent each clan of a population 
    //npod reprents the number of pod in each clan
    public int nPod;
    public int nIndividus;
    //podDistance represents  the mesure that will create the differents values of each individu
    public int podDistance;
    public int individualDistance;
    public Individu matriarche;
    //the List of the pods of each clan 
    public LinkedList<Pod> pods; 
   
    public Clan(Individu s,int nPod,int podDistance,int nIndividus,int individualDistance) {
        this.pods=new LinkedList<>();
        this.nPod=nPod;
        this.podDistance=podDistance;
        this.nIndividus=nIndividus;
        this.individualDistance=individualDistance;
        //Adding the first individus to the first pod of the clan
        pods.add(new Pod(s,this.nIndividus,this.individualDistance));
        generatePods();
    }

    public void generatePods() {
        Individu I=pods.getFirst().getFirst();
        for(int i=1;i<nPod;i++){
            Individu s=new Individu(I.size, I.fmin, I.fmax, I.L, I.T, I.matricepositions);
            pods.add(new Pod(s,nIndividus,individualDistance));
        }
    }
    public void sortClan(){
        for(Pod p:pods)
            p.sortPod();
        Collections.sort(pods);
        
    }
    
    @Override
    public String toString() {
        return "Clan{=\n\t" +  pods + "\n}\n";
    }

    void diversificationSearch(Population pop) throws CloneNotSupportedException {
        Pod worstPod=Collections.max(pods);//Determine the worst pod
        worstPod.diversification(this,pop);//replace the individuals of the worst pod
    }

    public LinkedList<Individu> getSolution() {
        LinkedList<Individu> allSolutions=new LinkedList<>();
        for(Pod p:pods){
            for(Individu s:p.getSolutions()){
                allSolutions.add(s);
            }
        }
        Collections.sort(allSolutions);
        return allSolutions;
    
    }
@Override	
    public Clan clone() throws CloneNotSupportedException {   
        Clan copie = (Clan)super.clone();
        copie.individualDistance= new Integer(individualDistance);
        copie.matriarche=matriarche.clone();
        copie.pods=(LinkedList<Pod>) pods.clone();
        copie.nIndividus=new Integer(nIndividus);
        copie.nPod=new Integer(nPod);
        copie.podDistance=new Integer(podDistance);
        return copie;
    }

    public Individu getBest() {
        LinkedList<Individu> allSolutions=new LinkedList<>();
        for(Pod p:pods){
            for(Individu s:p.getSolutions()){
                allSolutions.add(s);
            }
        }
        Collections.sort(allSolutions);
        return allSolutions.getFirst();
    }

    void update() {
        Collections.sort(pods);
    }
public double meanCost() {
        double sum=0.0;
        for(Pod p:pods){
            p.sortPod();
            sum+=p.meanCost();

        }

        return (sum/(double)pods.size());
    }
    @Override
    public int compareTo(Clan o) {
    if(this.meanCost()==o.meanCost())
            return 0;
        if(Math.abs(this.meanCost())<Math.abs(this.meanCost()))
            return -1;
        return 1;
    
    }

    
}
