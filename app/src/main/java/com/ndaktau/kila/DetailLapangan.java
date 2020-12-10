package com.ndaktau.kila;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DetailLapangan extends AppCompatActivity {

    Button Pesan;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private final ArrayList<DataLapangan> datalapangan = new ArrayList<>();
    String[] namalapangan, alamatlapangan, kontaklapangan, jenislapangan, hargalapangan;
    private LapanganAdapter lapanganAdapter;


    public DetailLapangan() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lapangan);
        mFirestoreList = findViewById(R.id.recyclerlapangan);
        firebaseFirestore = firebaseFirestore.getInstance();
        getAllDocument();

    }

    private void getAllDocument() {
        Log.i(TAG, "getAllDocument: masuk logi");
//        db.collection("DaftarLapangan")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            int posi = 0;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                DataLapangan dl = new DataLapangan(Integer.parseInt(document.get("ID").toString())
//                                        ,document.get("Nama lapangan").toString()
//                                        , document.get("Alamat lapangan").toString()
//                                        , document.get("Kontak lapangan").toString()
//                                        , document.get("Harga lapangan(perjam)").toString()
//                                        , document.get("Jenis lapangan").toString()
//                                );
//                                datalapangan.add(dl);
////                                Log.i(TAG, "onComplete: " + datalapangan.get(posi).getNamalap());
//                                posi++;
//                            }
//                            Log.i(TAG, "getAllDocument: lapangan = " + datalapangan.size());
//                            namalapangan = new String[datalapangan.size()];
//                            alamatlapangan = new String[datalapangan.size()];
//                            jenislapangan = new String[datalapangan.size()];
//                            kontaklapangan = new String[datalapangan.size()];
//                            hargalapangan = new String[datalapangan.size()];
//
//
//                            int pos = 0;
//                            for (int i = datalapangan.size(); i > 0; i--) {
//                                namalapangan[pos] = datalapangan.get(pos).getNamalap();
//                                hargalapangan[pos] = datalapangan.get(pos).getHargalap();
//                                kontaklapangan[pos] = datalapangan.get(pos).getKontaklap();
//                                alamatlapangan[pos] = datalapangan.get(pos).getAlamatlap();
//                                jenislapangan[pos] = datalapangan.get(pos).getJenlapangan();
//
//                                Log.i(TAG, "onCreateView: nama lapangan = " + namalapangan[pos] + "\n"
//                                        + "harga lapangan : " + hargalapangan[pos] + "\n" + "kontak  : " + kontaklapangan[pos]
//                                );
//                                pos++;
//                            }
//                            lapanganAdapter = new LapanganAdapter(namalapangan, hargalapangan, kontaklapangan, alamatlapangan, DetailLapangan.this);
//                            lapanganAdapter.notifyDataSetChanged();
//                            mFirestoreList.setAdapter(lapanganAdapter);
//                            mFirestoreList.setLayoutManager(new LinearLayoutManager(DetailLapangan.this));
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        db.collection("DaftarLapangan")
                .whereEqualTo("Jenis lapangan", HomeFragment.getKategori())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        int posi = 0;
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("Jenis lapangan") != null) {
                                DataLapangan dl = new DataLapangan(doc.get("ID").toString()
                                        ,doc.get("Nama lapangan").toString()
                                        , doc.get("Alamat lapangan").toString()
                                        , doc.get("Kontak lapangan").toString()
                                        , doc.get("Harga lapangan(perjam)").toString()
                                        , doc.get("Jenis lapangan").toString()
                                );
                                datalapangan.add(dl);
//                                Log.i(TAG, "onComplete: " + datalapangan.get(posi).getNamalap());
                                posi++;
                            }
                        }
                        namalapangan = new String[datalapangan.size()];
                        alamatlapangan = new String[datalapangan.size()];
                        jenislapangan = new String[datalapangan.size()];
                        kontaklapangan = new String[datalapangan.size()];
                        hargalapangan = new String[datalapangan.size()];
                        int pos = 0;
                        for (int i = datalapangan.size(); i > 0; i--) {
                            namalapangan[pos] = datalapangan.get(pos).getNamalap();
                            hargalapangan[pos] = datalapangan.get(pos).getHargalap();
                            kontaklapangan[pos] = datalapangan.get(pos).getKontaklap();
                            alamatlapangan[pos] = datalapangan.get(pos).getAlamatlap();
                            jenislapangan[pos] = datalapangan.get(pos).getJenlapangan();
                            Log.i(TAG, "onCreateView: nama lapangan = " + namalapangan[pos] + "\n"
                                        + "harga lapangan : " + hargalapangan[pos] + "\n" + "kontak  : " + kontaklapangan[pos]
                            );
                            pos++;
                        }
                        lapanganAdapter = new LapanganAdapter(namalapangan, hargalapangan, kontaklapangan, alamatlapangan, DetailLapangan.this);
                        lapanganAdapter.notifyDataSetChanged();
                        mFirestoreList.setAdapter(lapanganAdapter);
                        mFirestoreList.setLayoutManager(new LinearLayoutManager(DetailLapangan.this));
                    }
                });
    }
}
