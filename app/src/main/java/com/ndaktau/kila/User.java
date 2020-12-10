package com.ndaktau.kila;

public class User {
    int ID;
    String email;
    String password;
    String name;
public User(String email,String password,String name){
    setEmail(email);
    setPassword(password);
    setName(name);
}
    public User(int ID,String Email,String Password){
        setEmail(Email);
        setID(ID);
        setPassword(Password);
    }
    public User(String Email,String password){
        setEmail(Email);
        setID(ID);
        setPassword(password);
    }

    private void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public void setEmail(String email){

        this.email = email;
    }
    public String getEmail(){

        return email;
    }
    public int getID(){

        return ID;
    }
    public void setID(int id){

        this.ID = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
