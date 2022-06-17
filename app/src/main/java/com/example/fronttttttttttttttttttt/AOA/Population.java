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
public class Population implements Cloneable{
     //this class contains all our population
    //nclan represents the number of clan of our population
    //npod reprents the number of pod in each clan
    private int nClan;
    private int nPod;
    private int nIndividus;
    //clanDistance represents  the mesure that will create the differents values of each individu
    private int clanDistance;
    private int podDistance;
    private int individualDistance;
    
    //the List of our Clan 
    private LinkedList<Clan> clans; 
    public Population(int nClan, int nPod,int nIndividual,
            int clanDistance,int podDistance,int individualDistance, 
            int size, double fmin, double fmax, double L, double T,ArrayList<ArrayList<Double>> matricepositions) {
        this.nPod = nPod;
        this.nClan = nClan;
        this.nIndividus=nIndividual;
        //Distances
        this.clanDistance=clanDistance;
        this.podDistance=podDistance;
        this.individualDistance=individualDistance;
        
        //Init the List of the clan
        clans= new LinkedList<>();
        //generate a solution for each Clan, from this I will cal the Creation of Pods in each clan
        generateClansSolutions(size,  fmin, fmax, L, T, matricepositions);
        
    }

    private void generateClansSolutions(int size, double fmin, double fmax, double L, double T,ArrayList<ArrayList<Double>> matricepositions) {
        Individu I=new Individu(size,fmin,fmax,L,T,matricepositions);
        clans.add(new Clan(I,nPod,podDistance,nIndividus,individualDistance));
        
        for(int i=1;i<nClan;i++){
            Individu s=new Individu(I.size, I.fmin, I.fmax, I.L, I.T, I.matricepositions);
       
            clans.add(new Clan(s,
                                nPod,podDistance,nIndividus,individualDistance));
        }
       
    }

    public int getnClan() {
        return nClan;
    }

    public int getnPod() {
        return nPod;
    }

    public int getnIndividus() {
        return nIndividus;
    }

    public int getClanDistance() {
        return clanDistance;
    }

    public int getPodDistance() {
        return podDistance;
    }

    public int getIndividualDistance() {
        return individualDistance;
    }

    public LinkedList<Clan> getClans() {
        return clans;
    }

    public void setClans(LinkedList<Clan> clans) {
        this.clans = clans;
    }

   
    
    
    @Override
    public String toString() {
        return "Population{" + "clans=\n" + clans + "}\n";
    }

    public Individu getBest() {
        LinkedList<Individu> l=getSolution();
        Collections.sort(l);
       // System.out.println("l = "+l+" and first = \n"+l.getFirst());
        return l.getFirst();
    }

     public LinkedList<Individu> getSolution() {
        LinkedList<Individu> allSolutions=new LinkedList<>();
        for(Clan c:clans)
            for(Pod p:c.pods){
                for(Individu s:p.getSolutions()){
                    allSolutions.add(s);
                }
            }
       Collections.sort(allSolutions);
        return allSolutions;
    
    }
     
     @Override	
    public Population clone() throws CloneNotSupportedException {   
        Population copie = (Population)super.clone();
        copie.clanDistance= new Integer(clanDistance);
        copie.clans=(LinkedList<Clan>) clans.clone();
        copie.individualDistance=new Integer(individualDistance);
        copie.nClan=new Integer(nClan);
        copie.nIndividus=new Integer(nIndividus);
        copie.nPod=new Integer(nPod);
        copie.podDistance=new Integer(podDistance);
        return copie;
    }
    
    public void evaluate() {
        
        for(Clan c:clans){
            c.update();//sort the artificial orcas if their pods
        }
        
        
       
    
    
    }
}
