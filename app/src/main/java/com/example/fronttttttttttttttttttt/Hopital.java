package com.example.fronttttttttttttttttttt;

public class Hopital {
    private String NomH;
private int DoseSpootnik,DoseAstra,DoseJohnson,DoseSinovac;


    public Hopital(String NomH,int dsp,int da,int dj,int dsi) {
        this.NomH = NomH;
        DoseSinovac= dsi;
        this.DoseAstra= da;
        this.DoseJohnson= dj;
         this.DoseSpootnik=dsp;
    }
    public Hopital() {

    }

    public String getNomH() {
        return NomH;
    }

    public int getDosSp() {
        return DoseSpootnik;
    }

    public int getDosA() {
        return DoseAstra;
    }

    public int getDosJ() {
        return DoseJohnson;
    }

    public int getDosSi() {
        return DoseSinovac;
    }
}
