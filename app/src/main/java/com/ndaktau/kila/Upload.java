package com.ndaktau.kila;

public class Upload {
    private String title;
    public Upload(){}

    public  Upload(String name){
        if(name.trim().equals("")){
            name = "No name";
        }
        setnName(name);
    }
    public String getnName(){
        return this.title;
    }
    private void setnName(String name){
        title = name;
    }
}

