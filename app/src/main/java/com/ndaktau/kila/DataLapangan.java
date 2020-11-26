package com.ndaktau.kila;

import java.util.HashMap;
import java.util.Map;

public class DataLapangan {

    private String namalap;
    private String alamatlap;
    private String kontaklap;
    private String hargalap;
    private String jenlapangan;
    private Map<String, Object> upfoto;

    public DataLapangan (String namalap, String alamatlap, String kontaklap, String hargalap, String jenlapangan,
                         Map fotoo){
        upfoto = new HashMap<>();
        this.namalap = namalap;
        this.alamatlap = alamatlap;
        this.kontaklap = kontaklap;
        this.hargalap = hargalap;
        this.jenlapangan = jenlapangan;
        this.upfoto=fotoo;


    }

    public DataLapangan(){

    }

    public String getNamalap() {
        return namalap;
    }

    public void setNamalap(String namalapbasket) {
        this.namalap = namalapbasket;
    }

    public String getAlamatlap() {
        return alamatlap;
    }

    public void setAlamatlap(String alamatlapbasket) {
        this.alamatlap = alamatlapbasket;
    }

    public String getKontaklap() {
        return kontaklap;
    }

    public void setKontaklap(String kontaklapbasket) {
        this.kontaklap = kontaklapbasket;
    }

    public String getHargalap() {
        return hargalap;
    }

    public void setHargalap(String hargalapbasket) {
        this.hargalap = hargalap;
    }

}

