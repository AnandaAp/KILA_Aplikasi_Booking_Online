package com.ndaktau.kila;

import java.util.HashMap;
import java.util.Map;

public class DataLapangan {

    private String namalap;
    private String alamatlap;
    private String kontaklap;
    private String hargalap;
    private String id;

    public String getJenlapangan() {
        return jenlapangan;
    }

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

    public DataLapangan (String id,String namalap, String alamatlap, String kontaklap, String hargalap, String jenlapangan,
                         Map fotoo){
        upfoto = new HashMap<>();
        this.namalap = namalap;
        this.alamatlap = alamatlap;
        this.kontaklap = kontaklap;
        this.hargalap = hargalap;
        this.jenlapangan = jenlapangan;
        this.upfoto=fotoo;
        this.id=id;
    }

    public DataLapangan (String id,String namalap, String alamatlap, String kontaklap, String hargalap, String jenlapangan
                         ){
        upfoto = new HashMap<>();
        this.namalap = namalap;
        this.alamatlap = alamatlap;
        this.kontaklap = kontaklap;
        this.hargalap = hargalap;
        this.jenlapangan = jenlapangan;
        this.id = id;

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

    public String getId() {
        return id;
    }
}

