package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private final String TAG = "com.ndaktau.kila";
    private TextView signUp;
    private TextInputLayout email,password;
    private Button sign_in;
    protected String email_value,password_value;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db ;

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUp = view.findViewById(R.id.signUp);
        signUp.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.NavToSignUp));
        setDb();
        mAuth = FirebaseAuth.getInstance();
        NavToSignUp();
        init(view);
        loginCheck();
        return view;
    }
    protected void init(View view){
        email = view.findViewById(R.id.emailInputLayout);
        password = view.findViewById(R.id.passwordInputLayout);
        sign_in = view.findViewById(R.id.signIn);
    }

    private  void loginCheck(){
        sign_in.setOnClickListener(v -> {
            email_value = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            password_value = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
            if(email_value.isEmpty()){
                email.setError("Email tidak boleh kosong");
                email.setErrorEnabled(true);
            }
            else if(password_value.isEmpty()){
                password.setError("Password tidak boleh kosong");
                password.setErrorEnabled(true);
            }
            else{
                email.setErrorEnabled(false);
                password.setErrorEnabled(false);
                mAuth.signInWithEmailAndPassword(email_value, password_value)
                        .addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                User user1 = new User(user.getEmail(),password_value);
                                Toast.makeText(requireActivity(),"Login "+user.getEmail()
                                        ,Toast.LENGTH_LONG).show();
                                SessionManagement sessionManagement = new SessionManagement(requireActivity());
                                sessionManagement.saveSession(user1);
                                checkUser(mAuth.getCurrentUser());
//                                moveToDashboard();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(requireActivity(), "Email atau Password salah",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        });
            }
        });
    }

    private void NavToSignUp(){
        signUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.NavToSignUp));
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: cek session");
        checkSession();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(requireContext());
        int isUserLoggedIn = sessionManagement.getSession();
        if (isUserLoggedIn != -1){
            Log.i(TAG, "checkSession: user sudah login");
            checkUser(Objects.requireNonNull(mAuth.getCurrentUser()));
        }
        else{
            Log.i(TAG, "checkSession: user belum login");
        }
    }

    private void checkUser(FirebaseUser currentUser){
        DocumentReference docRef = getDb().collection("users")
                .document(Objects.requireNonNull(currentUser.getEmail()));
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    if(Objects.requireNonNull(document.get("Tipe Akun")).toString().equals("Administrator")){
                        moveToAdminDashboard();
                    }
                    else if(Objects.requireNonNull(document.get("Tipe Akun")).toString().equals("Pengguna")){
                        moveToDashboardUser();
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    private void moveToAdminDashboard(){
        Intent intent = new Intent(requireActivity(),DashboardAdmin.class);
        startActivity(intent);
        requireActivity().finish();
    }

    public void moveToDashboardUser(){
        Intent intent = new Intent(requireActivity(),DashboardUser.class);
        startActivity(intent);
        requireActivity().finish();
    }
}