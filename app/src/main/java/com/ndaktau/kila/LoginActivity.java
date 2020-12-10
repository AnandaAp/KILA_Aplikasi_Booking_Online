package com.ndaktau.kila;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static int addToDB = 0;
    public static int getAddToDB() {
        return addToDB;
    }

    public static void setAddToDB(int addToDB) {
        LoginActivity.addToDB += addToDB;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


}