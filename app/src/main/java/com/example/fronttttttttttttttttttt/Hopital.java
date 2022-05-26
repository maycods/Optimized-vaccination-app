package com.example.fronttttttttttttttttttt;

public class Hopital {
    public String NomH;
public long DoseSpootnik,DoseAstra,DoseJohnson,DoseSinovac,nbA;


    public Hopital(String NomH,long dsp,long da,long dj,long dsi,long nbA) {
        this.NomH = NomH;
        DoseSinovac= dsi;
        this.DoseAstra= da;
        this.DoseJohnson= dj;
         this.DoseSpootnik=dsp;
         this.nbA=nbA;
    }
    public Hopital() {
    }


}
