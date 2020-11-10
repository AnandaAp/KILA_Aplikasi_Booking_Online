package com.ndaktau.kila;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_fragment extends Fragment {
    private final String TAG = "com.ndaktau.kila";
    private TextView signUp;
    private TextInputLayout email,password;
    private Button sign_in;
    protected String email_value,password_value;
    private FirebaseAuth mAuth;

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
            email_value = email.getEditText().getText().toString().trim();
            password_value = password.getEditText().getText().toString().trim();
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
                                Toast.makeText(requireActivity(),"Login "+user.getEmail()
                                        ,Toast.LENGTH_LONG).show();
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
}