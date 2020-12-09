package com.ndaktau.kila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DetailLapangan extends AppCompatActivity {
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
        db.collection(HomeFragment.getKategori())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int posi = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DataLapangan dl = new DataLapangan(document.get("Nama lapangan").toString()
                                        , document.get("Alamat lapangan").toString()
                                        , document.get("Kontak lapangan").toString()
                                        , document.get("Harga lapangan(perjam)").toString()
                                        , document.get("Jenis lapangan").toString()
                                );
                                datalapangan.add(dl);
                                Log.i(TAG, "onComplete: " + datalapangan.get(posi).getNamalap());
                                posi++;
                            }
                            Log.i(TAG, "getAllDocument: mahasiswaSize = " + datalapangan.size());
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
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
