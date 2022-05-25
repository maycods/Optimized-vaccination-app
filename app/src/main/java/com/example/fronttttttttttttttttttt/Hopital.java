package com.example.fronttttttttttttttttttt;

public class Hopital {
    public String NomH;
public long DoseSpootnik,DoseAstra,DoseJohnson,DoseSinovac;


    public Hopital(String NomH,long dsp,long da,long dj,long dsi) {
        this.NomH = NomH;
        DoseSinovac= dsi;
        this.DoseAstra= da;
        this.DoseJohnson= dj;
         this.DoseSpootnik=dsp;
    }
    public Hopital() {
    }


}
