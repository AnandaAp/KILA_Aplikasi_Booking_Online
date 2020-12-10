package com.ndaktau.kila;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Pesan extends AppCompatActivity {
    private ImageView btnBack;
    Button Pesanwa;
    EditText Namapemesan,Namalapangan,Jammulai,Jamselesai,Lamapemesan,Tanggal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        btnBack = findViewById(R.id.btnBack);
        Namapemesan=findViewById(R.id.namapemesan);
        Namalapangan=findViewById(R.id.nameLap);
        Jammulai=findViewById(R.id.jammulai);
        Jamselesai=findViewById(R.id.jamselesai);
        Lamapemesan=findViewById(R.id.lamapemesan);
        Tanggal=findViewById(R.id.tanggal);

        Pesanwa=findViewById(R.id.pesanwa);
        Pesanwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pesan1= Namapemesan.getText().toString();
                String pesan2= Namalapangan.getText().toString();
                String pesan3= Jammulai.getText().toString();
                String pesan4= Jamselesai.getText().toString();
                String pesan5= Lamapemesan.getText().toString();
                String pesan6= Tanggal.getText().toString();

                String semuapesan="Nama Pemesan: "+pesan1+"\n"+"Nama Lapangan: "+pesan2+"\n"+"Jam Mulai: "+pesan3+"\n"+"Jam Selesai: "+pesan4+"\n"+"Lama Pesanan: "+pesan5+"\n"+"Tanggal: "+pesan6+"\n";
                Intent kirimWA=new Intent(Intent.ACTION_SEND);
                kirimWA.setType("text/plain");
                kirimWA.putExtra(Intent.EXTRA_TEXT,semuapesan);
                kirimWA.putExtra("jid","62816652494"+"@s.whatsapp.net");
                kirimWA.setPackage("com.whatsapp");

                startActivity(kirimWA);
            }
        });
        btnBack.setOnClickListener(v ->{
            Intent intent = new Intent(Pesan.this,DetailLapangan.class);
            startActivity(intent);
        });
    }
}