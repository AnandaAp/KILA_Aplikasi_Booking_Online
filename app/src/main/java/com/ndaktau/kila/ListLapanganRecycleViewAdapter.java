package com.ndaktau.kila;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class ListLapanganRecycleViewAdapter extends RecyclerView.Adapter<ListLapanganRecycleViewAdapter.kategoriviewholder> {

    private String[] nama;
    private int pos;
    private String[] harga;
    private String[] kontak;
    private String[] alamat;
    private String[] jenis;
    private String[] id;
    private Context context;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String namaSend,alamatSend,kontakSend,hargaSend,jenisSend,idSend;
    public ListLapanganRecycleViewAdapter(String[] nama, String[] harga, String[] kontak, String[] alamat, String[] jenis, Context context) {
        setHarga(harga);
        setKontak(kontak);
        setNama(nama);
        setContext(context);
        setAlamat(alamat);
        setJenis(jenis);

    }
    public ListLapanganRecycleViewAdapter(String[] id,String[] nama, String[] harga, String[] kontak, String[] alamat, String[] jenis, Context context) {
        setHarga(harga);
        setKontak(kontak);
        setNama(nama);
        setContext(context);
        setAlamat(alamat);
        setJenis(jenis);
        setId(id);
    }
    public String[] getAlamat() {
        return alamat;
    }

    public void setAlamat(String[] alamat) {
        this.alamat = alamat;
    }


    public String[] getNama() {
        return nama;
    }

    public void setNama(String[] nama) {
        this.nama = nama;
    }

    public String[] getHarga() {
        return harga;
    }

    public void setHarga(String[] harga) {
        this.harga = harga;
    }

    public String[] getKontak() {
        return kontak;
    }

    public void setKontak(String[] kontak) {
        this.kontak = kontak;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static String getJenisSend() {
        return jenisSend;
    }

    public static void setJenisSend(String jenisSend) {
        ListLapanganRecycleViewAdapter.jenisSend = jenisSend;
    }

    public String[] getJenis() {
        return jenis;
    }

    public void setJenis(String[] jenis) {
        this.jenis = jenis;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    @NonNull
    @Override
    public kategoriviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_row_design_admin,parent,false);
        return new kategoriviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull kategoriviewholder holder, int position) {

        holder.nama.setText(getNama()[position]);
        holder.harga.setText(getHarga()[position]);
        holder.alamat.setText(getAlamat()[position]);
        holder.kontak.setText(getKontak()[position]);
        pos = position;
        holder.deletee.setOnClickListener(v->{
            deleteData(position);
            LoginActivity.setAddToDB(1);
            LoginFragment.addID(-1);
        });
        holder.updatee.setOnClickListener(v -> {
            setNamaSend(getNama()[position]);
            setAlamatSend(getAlamat()[position]);
            setHargaSend(getHarga()[position]);
            setKontakSend(getKontak()[position]);
            setJenisSend(getJenis()[position]);
            setIdSend(getId()[position]);
            pindahKeUpdate();
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.nama.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.harga.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.alamat.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.kontak.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        }

    }

    @Override
    public int getItemCount() {
        return nama.length;
    }

    public static String getNamaSend() {
        return namaSend;
    }

    public static void setNamaSend(String namaSend) {
        ListLapanganRecycleViewAdapter.namaSend = namaSend;
    }

    public static String getAlamatSend() {
        return alamatSend;
    }

    public static void setAlamatSend(String alamatSend) {
        ListLapanganRecycleViewAdapter.alamatSend = alamatSend;
    }

    public static String getKontakSend() {
        return kontakSend;
    }

    public static void setKontakSend(String kontakSend) {
        ListLapanganRecycleViewAdapter.kontakSend = kontakSend;
    }

    public static String getHargaSend() {
        return hargaSend;
    }

    public static void setHargaSend(String hargaSend) {
        ListLapanganRecycleViewAdapter.hargaSend = hargaSend;
    }

    private void pindahKeUpdate(){
        Intent intent = new Intent(context,UpdateData.class);
        context.startActivity(intent);
    }

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }

    public static String getIdSend() {
        return idSend;
    }

    public static void setIdSend(String idSend) {
        ListLapanganRecycleViewAdapter.idSend = idSend;
    }

    private void deleteData(int pos) {
        Log.i(TAG, "deleteData: masuk method");
        getDb().collection("DaftarLapangan").document(getId()[pos])
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: sukses menghapus");
                        Toast.makeText(context, "Data berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static class kategoriviewholder extends RecyclerView.ViewHolder{
        TextView nama,alamat,harga,kontak;
        Button updatee, deletee;
        View view;



        public kategoriviewholder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nameLap);
            harga = itemView.findViewById(R.id.harga);
            alamat= itemView.findViewById(R.id.alamat);
            kontak = itemView.findViewById(R.id.kontak);
            updatee = itemView.findViewById(R.id.btnUpdate);
            deletee = itemView.findViewById(R.id.btnDelete);
            view = itemView;

        }
    }

}
