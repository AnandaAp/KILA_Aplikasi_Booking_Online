package com.ndaktau.kila;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private  final String SESSION_KEY = "USER_SESSION";
    private String Email;
    public SessionManagement(Context context){
        String SHARED_PREF_NAME = "SESSION";
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

//    public void saveSession(FirebaseUser user){
//        User pengguna = new User(user.getEmail(),user.getDisplayName());
//        int id = pengguna.getID();
//        editor.putInt(SESSION_KEY,id).commit();
//    }
    public void saveSession(User user){
        User pengguna = new User(user.getEmail(),user.getPassword());
        int id = pengguna.getID();
        editor.putInt(SESSION_KEY,id).commit();
    }
    public String getEmail(User user){
        return user.getEmail();
    }


    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public void removeSesion(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
