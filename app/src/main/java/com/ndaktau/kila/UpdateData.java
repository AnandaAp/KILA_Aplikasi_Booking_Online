package com.ndaktau.kila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateData extends AppCompatActivity {
    private static final String TAG = "UpdateData";
    private FirebaseFirestore db ;
    private EditText nama,alamat,harga,kontak;
    private Button confirm;
    private ImageView cancel;
    String tempNama,tempAlamat;
    long tempHarga,tempKontak;
    String jenisUpdate = ListLapanganRecycleViewAdapter.getJenisSend();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        setDb();
        nama = findViewById(R.id.namaLapanganUpdate);
        alamat = findViewById(R.id.alamatUpdate);
        harga = findViewById(R.id.hargaUpdate);
        kontak = findViewById(R.id.nomorHp);
        nama.setHint(ListLapanganRecycleViewAdapter.getNamaSend());
        alamat.setHint(ListLapanganRecycleViewAdapter.getAlamatSend());
        kontak.setHint(ListLapanganRecycleViewAdapter.getKontakSend());
        harga.setHint(ListLapanganRecycleViewAdapter.getHargaSend());
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateData.this,ListLapangan.class);
                startActivity(intent);
            }
        });
        confirm.setOnClickListener(v -> {
                tempNama = nama.getText().toString().trim();
                tempAlamat = alamat.getText().toString().trim();
//                tempHarga = Long.parseLong(harga.getText().toString().trim());
//                tempKontak = Long.parseLong(kontak.getText().toString().trim());
                if(checkNama(tempNama)){
                    updateData(tempNama,tempAlamat,Long.parseLong(harga.getText().toString().trim()),Long.parseLong(kontak.getText().toString().trim()));
                }
        });
    }


    private void updateData(String namaUpdate,String alamatUpdate,long kontakUpdate,long hargaUpdate) {
        Log.i(TAG, "updateDataMahasiswa: masuk method");
        Log.i(TAG, "updateDataMahasiswa: jenis update :"+jenisUpdate);
        Log.i(TAG, "updateData: nama :"+namaUpdate);
        Log.i(TAG, "updateData: alamat : "+alamatUpdate);
        Log.i(TAG, "updateData: kontak :"+kontakUpdate);
        Log.i(TAG, "updateData: harga : "+hargaUpdate);
        getDb().collection("DaftarLapangan")
                .document(String.valueOf(ListLapanganRecycleViewAdapter.getIdSend()))
                .update("Nama lapangan",namaUpdate
                        ,"Alamat lapangan",alamatUpdate
                        ,"Kontak lapangan",kontakUpdate
                        ,"Harga lapangan(perjam)",hargaUpdate)
                .addOnSuccessListener(aVoid -> {
                    try {
                        Log.i(TAG, "updateData: sukses");
                        Toast.makeText(UpdateData.this, "Data berhasil Diupdate",
                                Toast.LENGTH_SHORT).show();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(UpdateData.this, "Error deleting document: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show());
    }
    private boolean checkNama(String checkNama){
        if(checkNama.isEmpty()){
            tempNama = ListLapanganRecycleViewAdapter.getNamaSend();
            return false;
        }
        else{
            return true;
        }
    }
//    private boolean checkAlamat(String checkAlamat){
//        if(checkAlamat.isEmpty()){
//            tempAlamat = ListLapanganRecycleViewAdapter.getAlamatSend();
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//    private boolean checkHarga(String checkHarga){
//        if(checkHarga.isEmpty()){
//            tempHarga = Long.parseLong(ListLapanganRecycleViewAdapter.getHargaSend());
//            return false;
//        }
//        else {
//            tempHarga = Long.parseLong(harga.getText().toString().trim());
//            return true;
//        }
//    }
//    private boolean checkKontak(String checkKontak){
//        if(checkKontak.equals("")){
//            tempKontak = Long.parseLong(ListLapanganRecycleViewAdapter.getKontakSend());
//            return false;
//        }
//        else {
//            tempKontak = Long.parseLong(kontak.getText().toString().trim());
//            return true;
//        }
//    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb() {
        this.db = FirebaseFirestore.getInstance();
    }
}