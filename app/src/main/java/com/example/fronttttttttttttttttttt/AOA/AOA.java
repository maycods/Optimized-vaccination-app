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
public class AOA {
    ArrayList<Individu> BestSols;
    Individu Bestsol;
    public Population p;
    Long start_time;
    int maxIter;
    double ExecTime;
    int size;
    ArrayList<ArrayList<Double>> matricepositions;

    public AOA(int nclan,int npod,int nindiv,double fmin, double fmax, double L, double T,int maxIter, int size, ArrayList<ArrayList<Double>> matricepositions) throws CloneNotSupportedException {
        BestSols=new ArrayList<>();
        this.maxIter = maxIter;
        this.size = size;
        this.matricepositions = matricepositions;
        int clanD=size,podD=2*size/3,IndivD=size/3;
        p=new Population(nclan,npod,nindiv,clanD,podD,IndivD,size,fmin,fmax,L,T,matricepositions);
        //System.out.println(p.toString());
        
        start_time=System.currentTimeMillis();
        searchProcess();
    }

    private void searchProcess() throws CloneNotSupportedException {

        int i=0;
        p.evaluate();    
        Individu ib=p.getBest().clone();
        BestSols.add(ib);
            
        while(i<maxIter){
            
            //System.out.println(p.toString());
            for(Clan c:p.getClans()){
                //for each pod of the clan c
                for(Pod pod:c.pods){
                    pod.intensificationSearch(start_time,p,c);
                }
                p.evaluate();
                c.sortClan();
                c.diversificationSearch(p);
            }
            i++;
            p.evaluate();
            ib=p.getBest().clone();
            BestSols.add(ib);
            
        }
        
        
    }
     public ArrayList<Individu> getBestSolutions() {
        Collections.sort(BestSols);
        Collections.reverse(BestSols);
        return BestSols;
    }
    public Individu getBestSolution() {
        //Collections.sort(BestSols);
        getBestSolutions();
        return BestSols.get(BestSols.size()-1);
    }
    
    
    
}
