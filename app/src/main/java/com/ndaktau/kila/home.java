package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class home extends AppCompatActivity {

    ImageButton btfutsal,btbasket,btbadminton,btsepakbola;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btbadminton = findViewById(R.id.badminton);
        btbadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,badminton.class);
            }
        });


        btbasket= findViewById(R.id.basket);
        btbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,basket.class);
            }
        });

        btfutsal = findViewById(R.id.futsal);
        btfutsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,futsal.class);
            }
        });


        btsepakbola= findViewById(R.id.sepakbola);
        btsepakbola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,sepakbola.class);
            }
        });
    }
}
