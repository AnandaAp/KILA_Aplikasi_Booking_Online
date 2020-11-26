package com.ndaktau.kila;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahLapangan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahLapangan extends Fragment implements AdapterView.OnItemSelectedListener {


    private EditText namalapanganbskt;
    private EditText alamatlapanganbskt;
    private EditText kontaklapanganbskt;
    private EditText hargalapanganbskt;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private Button buttontambahbasket;
    private Button buttoncancelbasket;
    private Button btnupfoto;
    private Spinner jenisLapangan;
    private String pilihanjenis;
    public Uri imguri;
    private static final int PICK_IMAGE = 100;
    private Map<String, Object> upfoto, dataadmin;

//    ImageView imageView;



    public Button getButtoncancelbasket() {
        return buttoncancelbasket;
    }

    public void setButtoncancelbasket(View view) {
        this.buttoncancelbasket = view.findViewById(R.id.btnCancelBasket);
    }

//    private EditText namalapanganfutsal;
//    private EditText alamatlapanganfutsal;
//    private EditText kontaklapanganfutsal;
//    private EditText hargalapanganfutsal;
//    private Button buttontambahfutsal;
//    private Button buttoncancelfutsal;
//
//
//    private EditText namalapanganbadminton;
//    private EditText alamatlapanganbadminton;
//    private EditText kontaklapanganbadminton;
//    private EditText hargalapanganbadminton;
//    private Button buttontambahbadminton;
//    private Button buttoncancelbadminton;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_lapangan, container, false);
        db = FirebaseFirestore.getInstance();
//        mStorageRef = FirebaseStorage.getInstance().getReference("");
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("foto_lapangan");
        buttontambahbasket = view.findViewById(R.id.btnTambahBasket);
        buttoncancelbasket = view.findViewById(R.id.btnCancelBasket);
        btnupfoto=view.findViewById(R.id.pilihfoto);
        init(view);

        btnupfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });


        buttontambahbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!namalapanganbskt.getText().toString().isEmpty() && !alamatlapanganbskt.getText().toString().isEmpty()
                        && !kontaklapanganbskt.getText().toString().isEmpty() && !hargalapanganbskt.getText().toString().isEmpty()
                        && !pilihanjenis.isEmpty()) {
                    uploadFile();
                } else {
                    //jika kosong
                    Log.i(TAG, "onClick: "+namalapanganbskt.getText().toString());
                    Log.i(TAG, "onClick: "+alamatlapanganbskt.getText().toString());
                    Log.i(TAG, "onClick: "+kontaklapanganbskt.getText().toString());
                    Log.i(TAG, "onClick: "+hargalapanganbskt.getText().toString());
                    Log.i(TAG, "onClick: "+pilihanjenis);
                    Toast.makeText(requireActivity(), "Field harus diisi semua!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        setButtoncancelbasket(view);
        getButtoncancelbasket().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        return view;
//        return inflater.inflate(R.layout.fragment_tambah_lapangan, container, false);
    }


    private  String getFileExtention(Uri uri){
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (imguri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtention(imguri));
            fileReference.putFile(imguri).addOnSuccessListener(taskSnapshot -> {
                Upload upload = new Upload(fileReference.getName());
                upfoto.put("Foto Lapangan", upload);
                tambahLapBasket();


            });


        }else {
            imguri = Uri.parse("android.resource://com.ndaktau.kila/drawable/loccity");
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + ".png" );
            fileReference.putFile(imguri).addOnSuccessListener(taskSnapshot -> {
                Upload upload = new Upload(fileReference.getName());
                upfoto.put("Foto Lapangan", upload);
                tambahLapBasket();


            });
        }
    }


    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
//            imageView.setImageURI(imguri);

        }
    }

    public void back(){
//        FragmentDashboardAdmin fragment = new FragmentDashboardAdmin();
//        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
        Intent intent = new Intent(requireActivity(), DashboardAdmin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
    }
    private void init(View view) {
        namalapanganbskt = view.findViewById(R.id.namaLapBasket);
        alamatlapanganbskt = view.findViewById(R.id.alamatLapBasket);
        kontaklapanganbskt = view.findViewById(R.id.kontakLapBasket);
        hargalapanganbskt = view.findViewById(R.id.hargaLapBasket);
        jenisLapangan = view.findViewById(R.id.buttonjenis);
        upfoto= new HashMap<>();
        dataadmin = new HashMap<>();
//        addPhoto = view.findViewById(R.id.addPhoto);
//        addPhoto.setOnClickListener(v -> openGallery());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext()
                , R.array.jenislapangan, android.R.layout.simple_list_item_1);
        jenisLapangan.setAdapter(adapter);
        jenisLapangan.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    private void tambahLapBasket () {
        dataadmin.put("Nama lapangan", Objects.requireNonNull(namalapanganbskt.getText().toString()));
        dataadmin.put("Alamat lapangan", Objects.requireNonNull(alamatlapanganbskt.getText().toString()));
        dataadmin.put("Harga lapangan(perjam)", Objects.requireNonNull(Integer.parseInt(hargalapanganbskt.getText().toString())));
        dataadmin.put("Kontak lapangan",Objects.requireNonNull(kontaklapanganbskt.getText().toString()));
        dataadmin.put("Foto Profil",upfoto);
        dataadmin.put("Jenis lapangan", pilihanjenis);

//        DataLapangan dataLapangan = new DataLapangan(namalapanganbskt.getText().toString(),
//                alamatlapanganbskt.getText().toString(),
//                kontaklapanganbskt.getText().toString(),
//                hargalapanganbskt.getText().toString(), pilihanjenis, upfoto);


        db.collection("DaftarLapangan").document(namalapanganbskt.getText().toString()).set(dataadmin)
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pilihanjenis = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}