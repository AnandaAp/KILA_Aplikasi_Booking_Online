package com.ndaktau.kila;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class SignUp extends Fragment implements AdapterView.OnItemSelectedListener {
    private final String TAG = "com.ndaktau.kila.SignUp";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^.{6,}$");
    private static final int PICK_IMAGE = 100;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private Button btnSignUp;
    private ImageView back,addPhoto;
    private TextInputLayout name,email,pass;
    private String tipeAkunYangDipilih;
    private Uri addImage;
    private Map<String, Object> user;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        back = view.findViewById(R.id.backButton);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("foto_profil");
        mAuth = FirebaseAuth.getInstance();
        init(view);
        signUpProcess();
        return view;
    }

    private void init(View view){
        name =  view.findViewById(R.id.nameLayout);
        email = view.findViewById(R.id.emSignUpLayout);
        pass =  view.findViewById(R.id.passSignUpLayout);
        progressBar = view.findViewById(R.id.progress_bar);
        Spinner tipeAkun = view.findViewById(R.id.tipeAkun);
        addPhoto = view.findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(v -> openGallery());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext()
                ,R.array.tipe_akun, android.R.layout.simple_list_item_1);
        tipeAkun.setAdapter(adapter);
        tipeAkun.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //mengecek apakah sistem memakai dark mode atau tidak
        int currentNightMode = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                TextView passKriteria = view.findViewById(R.id.keteranganPassword);
                passKriteria.setTextColor(ResourcesCompat.getColor(getResources()
                        , R.color.pasificBlue, null));

                break;
            case Configuration.UI_MODE_NIGHT_YES:
                passKriteria = view.findViewById(R.id.keteranganPassword);
                passKriteria.setTextColor(ResourcesCompat.getColor(getResources()
                        , R.color.diamond, null));
                break;
        }
        back.setOnClickListener(this::navToSignIn);
        checkInput();
    }
    private void checkInput(){
        Objects.requireNonNull(name.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 ) {
                    name.setError("Nama Belum Terisi");
                    name.setErrorEnabled(true);
                }
                else {
                    name.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Objects.requireNonNull(email.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 ) {
                    email.setError("Email Belum Terisi");
                    email.setErrorEnabled(true);
                }

                else {
                    email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Objects.requireNonNull(pass.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 ) {
                    pass.setError("password Belum Terisi");
                    pass.setErrorEnabled(true);
                }
                else {
                    pass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void navToSignIn(View v){
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
    }
    private void signUpProcess(){
        btnSignUp.setOnClickListener(this::addToDB);
    }
    private void addToDB(View view){
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            Log.i(TAG, "addToDB: input user belum sesuai validasi");
        }
        else {
            mAuth.fetchSignInMethodsForEmail(Objects.requireNonNull(email.getEditText()).getText().toString()).addOnCompleteListener(t -> {
                if(Objects.requireNonNull(Objects.requireNonNull(t.getResult()).getSignInMethods()).size() == 0){
                    //create user account
                    mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString()
                            , Objects.requireNonNull(pass.getEditText()).getText().toString())
                            .addOnCompleteListener(requireActivity(), task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
//                                    Toast.makeText(requireContext(),"Akun berhasil dibuat",Toast.LENGTH_LONG).show();
                                    uploadFile();
//                                .add(user)
//                                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
//                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
//                                    sendPicture();
                                    try {
                                        Thread.sleep(4000);
                                        navToSignIn(view);
                                        requireActivity().getFragmentManager().popBackStack();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    /*updateUI(user);*/
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(requireContext(), "Akun Gagal Dibuat.",
//                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                }
                            });

                }
                else{
                    email.setError("Email sudah terdaftar");
                    email.setErrorEnabled(true);
                }
            }).addOnFailureListener(Throwable::printStackTrace);

        }
    }
    private boolean validateEmail() {
        String emailInput = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Email tidak boleh kosong");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Tolong masukan alamat email yang valid");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String usernameInput = Objects.requireNonNull(name.getEditText()).getText().toString().trim();
        if (usernameInput.isEmpty()) {
            name.setError("Nama tidak boleh kosong");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = Objects.requireNonNull(pass.getEditText()).getText().toString().trim();
        if (passwordInput.isEmpty()) {
            pass.setError("Password tidak boleh kosong");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password harus mnimal 6 karakter");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tipeAkunYangDipilih = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            assert data != null;
            addImage = data.getData();
//            addPhoto.setImageURI(addImage);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), addImage);
                addPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private  String getFileExtention(Uri uri){
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile(){
//        final ProgressDialog progressDialog = new ProgressDialog(requireContext());
//        progressDialog.setTitle("Uploading");
        if(addImage != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtention(addImage));
            fileReference.putFile(addImage).addOnSuccessListener(taskSnapshot -> {
                name.setErrorEnabled(false);
                email.setErrorEnabled(false);
                pass.setErrorEnabled(false);
                // Create a new user with a first and last name
                Handler handler = new Handler();
                handler.postDelayed(() -> progressBar.setProgress(0),5000);
//                Toast.makeText(requireContext(),"Upload Foto Sukses",Toast.LENGTH_LONG).show();
                Upload upload = new Upload(fileReference.getName());
//                String uploadID = db.collection("users")
//                        .document(name.getEditText().getText().toString()).getId();
                user = new HashMap<>();
                user.put("Name", Objects.requireNonNull(name.getEditText()).getText().toString());
                user.put("Email", Objects.requireNonNull(email.getEditText()).getText().toString());
                user.put("Password", Objects.requireNonNull(pass.getEditText()).getText().toString());
                user.put("Tipe Akun",tipeAkunYangDipilih);
                user.put("Foto Profil",upload);
                db.collection("users")
                        .document(email.getEditText().getText().toString())
                        .set(user)
                        .addOnSuccessListener(e ->
                                Toast.makeText(requireContext(),"Akun berhasil dibuat",Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e ->
                                Log.w(TAG, "onFailure: Error adding document", e));
                        }).addOnFailureListener(e -> /*Toast.makeText(requireContext()
                                ,"Gagal mengupload: "+e.getMessage(),Toast.LENGTH_LONG).show()*/
                                Toast.makeText(requireContext(), "Akun Gagal Dibuat.",
                                        Toast.LENGTH_SHORT).show())
                        .addOnProgressListener(snapshot -> {
                                double progres = (100.0 * snapshot.getBytesTransferred())
                                        / snapshot.getTotalByteCount();
                                progressBar.setProgress((int)progres);
            });
        }
        else {
            addImage = Uri.parse("android.resource://com.ndaktau.kila/drawable/user");
            //                InputStream stream = requireActivity().getContentResolver().openInputStream(addImage);
//                Bitmap bitmap,bit;
            //                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), addImage);
//                bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
//                bit = BitmapFactory.decodeStream(stream);
//                addPhoto.setImageBitmap(bit);
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    +".png");
            fileReference.putFile(addImage).addOnSuccessListener(taskSnapshot -> {
                name.setErrorEnabled(false);
                email.setErrorEnabled(false);
                pass.setErrorEnabled(false);
                // Create a new user with a first and last name
                Handler handler = new Handler();
                handler.postDelayed(() -> progressBar.setProgress(0),5000);
                Upload upload = new Upload(fileReference.getName());
//                String uploadID = db.collection("users")
//                        .document(name.getEditText().getText().toString()).getId();
                user = new HashMap<>();
                user.put("Name", Objects.requireNonNull(name.getEditText()).getText().toString());
                user.put("Email", Objects.requireNonNull(email.getEditText()).getText().toString());
                user.put("Password", Objects.requireNonNull(pass.getEditText()).getText().toString());
                user.put("Tipe Akun",tipeAkunYangDipilih);
                user.put("Foto Profil",upload);
                db.collection("users")
                        .document(email.getEditText().getText().toString())
                        .set(user)
                        .addOnSuccessListener(e ->
                                Toast.makeText(requireContext(),"Akun berhasil dibuat",Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e ->
                                Log.w(TAG, "onFailure: Error adding document", e));
                         }).addOnFailureListener(e -> /*Toast.makeText(requireContext()
                                ,"Gagal mengupload: "+e.getMessage(),Toast.LENGTH_LONG).show()*/
                        Toast.makeText(requireContext(), "Akun Gagal Dibuat.",
                                Toast.LENGTH_SHORT).show())
                        .addOnProgressListener(snapshot -> {
                            double progres = (100.0 * snapshot.getBytesTransferred())
                                    / snapshot.getTotalByteCount();
                            progressBar.setProgress((int)progres);
                });
        }
//        else {
//            Toast.makeText(requireContext(),"Gambar belum di pilih",Toast.LENGTH_LONG).show();
//        }
    }

}