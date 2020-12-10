package com.ndaktau.kila;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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

public class ListLapangan extends AppCompatActivity {

    String[] isii ={"Badminton","Futsal","Basket","Sepakbola"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    FirebaseFirestore db3 = FirebaseFirestore.getInstance();
    FirebaseFirestore db4 = FirebaseFirestore.getInstance();
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private final ArrayList<DataLapangan> datalapangan = new ArrayList<>();
    String[] id,namalapangan, alamatlapangan, kontaklapangan, jenislapangan, hargalapangan;
    private ListLapanganRecycleViewAdapter listLapanganAdmin;
    private DataLapangan dl,d2,d3,d4;
    int prev = LoginActivity.getAddToDB();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_lapangan);
        mFirestoreList = findViewById(R.id.recyclerlistlapangan);
        firebaseFirestore = firebaseFirestore.getInstance();
//        if(prev >= LoginActivity.getAddToDB()){
//            getAllDocument();
//        }
//        else if(prev < LoginActivity.getAddToDB()){
//            getAllDocument();
//        }
        getAllDocument();

//        final Runnable r = new Runnable() {
//            public void run() {
//                if(prev >= LoginActivity.getAddToDB()){
//                    getAllDocument();
//                    thr
//                }
//                else if(prev < LoginActivity.getAddToDB()){
//                    getAllDocument();
//                }
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//        handler.postDelayed(r, 1000);

    }
//    private void showDbBasket(){
////        db.collection("DaftarLapangan")
////                .get()
////                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                        if (task.isSuccessful()) {
////                            int posi = 0;
////                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                dl = new DataLapangan(Integer.parseInt(document.get("ID").toString())
////                                        ,document.get("Nama lapangan").toString()
////                                        , document.get("Alamat lapangan").toString()
////                                        , document.get("Kontak lapangan").toString()
////                                        , document.get("Harga lapangan(perjam)").toString()
////                                        , document.get("Jenis lapangan").toString()
////                                );
////                                Log.i(TAG, "test " + document.get("Nama lapangan").toString());
////                                datalapangan.add(dl);
////                                Log.i(TAG, "isi data nama lapangan " + datalapangan.get(posi).getNamalap());
////                                posi++;
////                            }
////
////                        } else {
////                            Log.d(TAG, "Error getting documents: ", task.getException());
////                        }
////                    }
////                });
//        db.collection("DaftarLapangan")
//                .whereEqualTo("Jenis lapangan", HomeFragment.getKategori())
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException e) {
//                        int posi = 0;
//                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }
//
//
//                        for (QueryDocumentSnapshot doc : value) {
//                            if (doc.get("Jenis lapangan") != null) {
//                                DataLapangan d4 = new DataLapangan(Integer.parseInt(doc.get("ID").toString())
//                                        ,doc.get("Nama lapangan").toString()
//                                        , doc.get("Alamat lapangan").toString()
//                                        , doc.get("Kontak lapangan").toString()
//                                        , doc.get("Harga lapangan(perjam)").toString()
//                                        , doc.get("Jenis lapangan").toString()
//                                );
//                                datalapangan.add(dl);
//                                posi++;
//                            }
//                        }
//                    }
//                });
//    }
//    private void showDbFutsal(){
//        db2.collection("Futsal")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            int posi = 0;
////                            Log.i(TAG, "isi data nama lapangan " + datalapangan.get(posi).getNamalap());
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                d2 = new DataLapangan(Integer.parseInt(document.get("ID").toString())
//                                        ,document.get("Nama lapangan").toString()
//                                        , document.get("Alamat lapangan").toString()
//                                        , document.get("Kontak lapangan").toString()
//                                        , document.get("Harga lapangan(perjam)").toString()
//                                        , document.get("Jenis lapangan").toString()
//                                );
//                                datalapangan.add(d2);
////                                Log.i(TAG, "onComplete: " + datalapangan.get(1).getNamalap());
//                                posi++;
//                            }
//                        }else{
//                            Log.d(TAG, "Error getting documents : ", task.getException());
//                        }
//                    }
//                });
//    }
//    private void showDbBatminton(){
//        db3.collection("Badminton")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            int posi = 0;
////                            Log.i(TAG, "isi data nama lapangan " + datalapangan.get(0).getNamalap());
//                            for (QueryDocumentSnapshot document : task.getResult()){
//                                d3 = new DataLapangan(Integer.parseInt(document.get("ID").toString())
//                                        ,document.get("Nama lapangan").toString()
//                                        , document.get("Alamat lapangan").toString()
//                                        , document.get("Kontak lapangan").toString()
//                                        , document.get("Harga lapangan(perjam)").toString()
//                                        , document.get("Jenis lapangan").toString()
//
//                                );
//                                Log.i(TAG, "db3 : "+d3.getNamalap());
//                                datalapangan.add(d3);
//                                Log.i(TAG, "onComplete: "+datalapangan.size());
//                                posi++;
//                            }
//                        }else{
//                            Log.d(TAG, "onComplete: Error getting documents : ", task.getException());
//                        }
//                    }
//                });
//    }
//    private void showDbSepakBola(){
//        db4.collection("Sepakbola")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            int posi = 0;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                d4 = new DataLapangan(Integer.parseInt(document.get("ID").toString())
//                                        ,document.get("Nama lapangan").toString()
//                                        , document.get("Alamat lapangan").toString()
//                                        , document.get("Kontak lapangan").toString()
//                                        , document.get("Harga lapangan(perjam)").toString()
//                                        , document.get("Jenis lapangan").toString()
//                                );
//                                datalapangan.add(d4);
//                                Log.i(TAG, "onComplete: " + datalapangan.size());
//                                posi++;
//
//                                Log.i(TAG, "getAllDocument: " + datalapangan.size());
//                                id = new String[datalapangan.size()];
//                                namalapangan = new String[datalapangan.size()];
//                                alamatlapangan = new String[datalapangan.size()];
//                                jenislapangan = new String[datalapangan.size()];
//                                kontaklapangan = new String[datalapangan.size()];
//                                hargalapangan = new String[datalapangan.size()];
////        Log.i(TAG, "getAllDocument: "+datalapangan.get(0).getNamalap());
//
//                                int pos = 0;
//                                for (int i = datalapangan.size(); i > 0; i--) {
//                                    id[pos] = String.valueOf(datalapangan.get(pos).getId());
//                                    namalapangan[pos] = datalapangan.get(pos).getNamalap();
//                                    hargalapangan[pos] = datalapangan.get(pos).getHargalap();
//                                    kontaklapangan[pos] = datalapangan.get(pos).getKontaklap();
//                                    alamatlapangan[pos] = datalapangan.get(pos).getAlamatlap();
//                                    jenislapangan[pos] = datalapangan.get(pos).getJenlapangan();
//
//                                    Log.i(TAG, "onCreateView: nama lapangan = " + namalapangan[pos] + "\n"
//                                            + "harga lapangan : " + hargalapangan[pos] + "\n" + "kontak  : " + kontaklapangan[pos]
//                                    );
//                                    pos++;
//                                }
//                                listLapanganAdmin= new ListLapanganRecycleViewAdapter(id,namalapangan, hargalapangan, kontaklapangan, alamatlapangan,jenislapangan, ListLapangan.this);
//                                listLapanganAdmin.notifyDataSetChanged();
//                                mFirestoreList.setAdapter(listLapanganAdmin);
//                                mFirestoreList.setLayoutManager(new LinearLayoutManager(ListLapangan.this));
//
//
//                            }
//                        }
//                        else{
//                            Log.d(TAG, "onComplete: Error getting documents : ",task.getException());
//                            namalapangan = new String[datalapangan.size()];
//                            alamatlapangan = new String[datalapangan.size()];
//                            jenislapangan = new String[datalapangan.size()];
//                            kontaklapangan = new String[datalapangan.size()];
//                            hargalapangan = new String[datalapangan.size()];
////        Log.i(TAG, "getAllDocument: "+datalapangan.get(0).getNamalap());
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
//                            listLapanganAdmin= new ListLapanganRecycleViewAdapter(namalapangan, hargalapangan, kontaklapangan, alamatlapangan, jenislapangan,ListLapangan.this);
//                            listLapanganAdmin.notifyDataSetChanged();
//                            mFirestoreList.setAdapter(listLapanganAdmin);
//                            mFirestoreList.setLayoutManager(new LinearLayoutManager(ListLapangan.this));
//                        }
//                    }
//                });
//    }
    private void getAllDocument() {
        Log.i(TAG, "getAllDocument: masuk logi");
        db.collection("DaftarLapangan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int posi = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dl = new DataLapangan(
                                        ""+document.get("ID")
                                        ,document.get("Nama lapangan").toString()
                                        , document.get("Alamat lapangan").toString()
                                        , document.get("Kontak lapangan").toString()
                                        , document.get("Harga lapangan(perjam)").toString()
                                        , document.get("Jenis lapangan").toString()
                                );
                                datalapangan.add(dl);
                                Log.i(TAG, "onComplete ID: " + document.get("ID"));
                                posi++;
                            }
                            Log.i(TAG, "getAllDocument: " + datalapangan.size());
                            id = new String[datalapangan.size()];
                            namalapangan = new String[datalapangan.size()];
                            alamatlapangan = new String[datalapangan.size()];
                            jenislapangan = new String[datalapangan.size()];
                            kontaklapangan = new String[datalapangan.size()];
                            hargalapangan = new String[datalapangan.size()];
//        Log.i(TAG, "getAllDocument: "+datalapangan.get(0).getNamalap());

                            int pos = 0;
                            for (int i = datalapangan.size(); i > 0; i--) {
                                id[pos] = ""+datalapangan.get(pos).getId();
                                Log.i(TAG, "onComplete: id "+i+" : "+datalapangan.get(pos).getId());
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
                            listLapanganAdmin= new ListLapanganRecycleViewAdapter(id,namalapangan, hargalapangan, kontaklapangan, alamatlapangan,jenislapangan, ListLapangan.this);
                            listLapanganAdmin.notifyDataSetChanged();
                            mFirestoreList.setAdapter(listLapanganAdmin);
                            mFirestoreList.setLayoutManager(new LinearLayoutManager(ListLapangan.this));
                        }
                        else{
                            Log.d(TAG, "onComplete: Error getting documents : ",task.getException());
                            namalapangan = new String[datalapangan.size()];
                            alamatlapangan = new String[datalapangan.size()];
                            jenislapangan = new String[datalapangan.size()];
                            kontaklapangan = new String[datalapangan.size()];
                            hargalapangan = new String[datalapangan.size()];
//        Log.i(TAG, "getAllDocument: "+datalapangan.get(0).getNamalap());

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
                            listLapanganAdmin= new ListLapanganRecycleViewAdapter(namalapangan, hargalapangan, kontaklapangan, alamatlapangan, jenislapangan,ListLapangan.this);
                            listLapanganAdmin.notifyDataSetChanged();
                            mFirestoreList.setAdapter(listLapanganAdmin);
                            mFirestoreList.setLayoutManager(new LinearLayoutManager(ListLapangan.this));
                        }
                    }
                });
    }




}