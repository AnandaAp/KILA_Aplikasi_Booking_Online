package com.ndaktau.kila;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends Fragment implements AdapterView.OnItemSelectedListener {
    private final String TAG = "com.ndaktau.kila.SignUp";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^.{6,}$");
    private TextView passKriteria;
    private FirebaseAuth mAuth;
    private Button btnSignUp;
    private ImageView back;
    private TextInputLayout name,email,pass;
    private Spinner tipeAkun;
    private String tipeAkunYangDipilih;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        init(view);
        signUpProcess(db);
        return view;
    }

    private void init(View view){
        name =  view.findViewById(R.id.nameLayout);
        email = view.findViewById(R.id.emSignUpLayout);
        pass =  view.findViewById(R.id.passSignUpLayout);
        tipeAkun = view.findViewById(R.id.tipeAkun);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext()
                ,R.array.tipe_akun, android.R.layout.simple_list_item_1);
        tipeAkun.setAdapter(adapter);
        tipeAkun.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mengecek apakah sistem memakai dark mode atau tidak
        int currentNightMode = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                passKriteria = view.findViewById(R.id.keteranganPassword);
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
        Navigation.findNavController(v).navigate(R.id.NavToSignIn);
    }
    private void signUpProcess(FirebaseFirestore db){
        btnSignUp.setOnClickListener(v -> addToDB(db,v));
    }
    private boolean addToDB(FirebaseFirestore db,View view){
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return false;
        }
        else {
            mAuth.fetchSignInMethodsForEmail(email.getEditText().getText().toString()).addOnCompleteListener(t -> {
                if(t.getResult().getSignInMethods().size() == 0){
                    //create user account
                    mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString()
                            , pass.getEditText().getText().toString())
                            .addOnCompleteListener(requireActivity(), task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(requireContext(),"Akun berhasil dibuat",Toast.LENGTH_LONG).show();
                                    navToSignIn(view);
                                    /*updateUI(user);*/
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                }
                            });

                    name.setErrorEnabled(false);
                    email.setErrorEnabled(false);
                    pass.setErrorEnabled(false);
                    // Create a new user with a first and last name
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", name.getEditText().getText().toString());
                    user.put("Email", email.getEditText().getText().toString());
                    user.put("Password", pass.getEditText().getText().toString());
                    user.put("Tipe Akun",tipeAkunYangDipilih);

                    // Add a new document with a generated ID
                    db.collection("users")
                            .document(name.getEditText().getText().toString())
                            .set(user)
                            .addOnSuccessListener(e -> Log.d(TAG, "onComplete: DocumentSnapshot added with ID: "+name.getEditText().getText().toString()))
                            .addOnFailureListener(e -> Log.w(TAG, "onFailure: Error adding document", e));
//                                .add(user)
//                                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
//                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                }
                else{
                    email.setError("Email sudah terdaftar");
                    email.setErrorEnabled(true);
                    return;
                }
            }).addOnFailureListener(Throwable::printStackTrace);

        }
        return true;
    }
    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();
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
        String usernameInput = name.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            name.setError("Nama tidak boleh kosong");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = pass.getEditText().getText().toString().trim();
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
}