package com.ndaktau.kila;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahLapangan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahLapangan extends Fragment {


    private EditText namalapanganbskt;
    private EditText alamatlapanganbskt;
    private EditText kontaklapanganbskt;
    private EditText hargalapanganbskt;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private Button buttontambahbasket;
    private Button buttoncancelbasket;
    private String jenisLapangan;

    private EditText namalapanganfutsal;
    private EditText alamatlapanganfutsal;
    private EditText kontaklapanganfutsal;
    private EditText hargalapanganfutsal;
    private Button buttontambahfutsal;
    private Button buttoncancelfutsal;


    private EditText namalapanganbadminton;
    private EditText alamatlapanganbadminton;
    private EditText kontaklapanganbadminton;
    private EditText hargalapanganbadminton;
    private Button buttontambahbadminton;
    private Button buttoncancelbadminton;

    private FirebaseFirestore firebaseFirestoreDb;
//    private Button fotoLap;
//    private Button jenisLap;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TambahLapangan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TambahLapangan.
     */
    // TODO: Rename and change types and number of parameters
    public static TambahLapangan newInstance(String param1, String param2) {
        TambahLapangan fragment = new TambahLapangan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_lapangan, container, false);
        db = FirebaseFirestore.getInstance();
//        mStorageRef = FirebaseStorage.getInstance().getReference("");
        mAuth = FirebaseAuth.getInstance();

        buttontambahbasket = view.findViewById(R.id.btnTambahBasket);
        buttoncancelbasket = view.findViewById(R.id.btnCancelBasket);
        init(view);


        buttontambahbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!namalapanganbskt.getText().toString().isEmpty() && !alamatlapanganbskt.getText().toString().isEmpty()
                        && !kontaklapanganbskt.getText().toString().isEmpty() && hargalapanganbskt.getText().toString().isEmpty()) {
                    tambahLapBasket();
                } else {
                    //jika kosong
                    Toast.makeText(requireActivity(), "Field harus diisi semua!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttoncancelbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
    private void init(View view) {
        namalapanganbskt = view.findViewById(R.id.namaLapBasket);
        alamatlapanganbskt = view.findViewById(R.id.alamatLapBasket);
        kontaklapanganbskt = view.findViewById(R.id.kontakLapBasket);
        hargalapanganbskt = view.findViewById(R.id.hargaLapBasket);
        Spinner jenisLapangan = view.findViewById(R.id.buttonjenis);
//        addPhoto = view.findViewById(R.id.addPhoto);
//        addPhoto.setOnClickListener(v -> openGallery());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext()
                , R.array.jenislapangan, android.R.layout.simple_list_item_1);
        jenisLapangan.setAdapter(adapter);
//        jenisLapangan.setOnItemSelectedListener();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void getDataLapanganBasket () {
        DocumentReference docRef = firebaseFirestoreDb.collection("DaftarLapangan").document("LapBasket");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DataLapangan dataLapangan = document.toObject(DataLapangan.class);
                        namalapanganbskt.setText(dataLapangan.getNamalapbasket());
                        alamatlapanganbskt.setText(dataLapangan.getAlamatlapbasket());
                        kontaklapanganbskt.setText(dataLapangan.getKontaklapbasket());
                        hargalapanganbskt.setText(dataLapangan.getHargalapbasket());
                    } else {
                        //
                    }
                }
            }
        });
    }

    private void tambahLapBasket () {

        DataLapangan dataLapangan = new DataLapangan(namalapanganbskt.getText().toString(),
                alamatlapanganbskt.getText().toString(),
                kontaklapanganbskt.getText().toString(),
                hargalapanganbskt.getText().toString());

        firebaseFirestoreDb.collection("DaftarMhs").document(namalapanganbskt.getText().toString()).set(dataLapangan)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Data telah diinputkan ",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }

}