package com.ndaktau.kila;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class ListLapanganAdmin extends RecyclerView.Adapter<ListLapanganAdmin.kategoriviewholder>{

    private String[] nama;
    private String[] harga;
    private String[] kontak;
    private String[] alamat;
    private Context context;

    public ListLapanganAdmin( String[] nama, String[] harga, String[] kontak,String[] alamat, Context context) {
        setHarga(harga);
        setKontak(kontak);
        setNama(nama);
        setContext(context);
        setAlamat(alamat);
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

    public static class kategoriviewholder extends RecyclerView.ViewHolder{
        TextView nama,alamat,harga,kontak;
        Button updatee, deletee;




        public kategoriviewholder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namalapangan);
            harga = itemView.findViewById(R.id.harga);
            alamat= itemView.findViewById(R.id.alamat);
            kontak = itemView.findViewById(R.id.kontak);
            updatee = itemView.findViewById(R.id.btnUpdate);
            deletee = itemView.findViewById(R.id.btnDelete);


        }
    }

}
