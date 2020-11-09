package com.ndaktau.kila;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "com.ndaktau.kila";
    protected TextInputLayout email;
    protected TextInputLayout password;
    private Button sign_in;
    private TextView sign_up;
    protected String email_value = "";
    protected String password_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        loginCheck();
    }

    protected void init(){
        email = (TextInputLayout) findViewById(R.id.emailInputLayout);
        password = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        sign_in = findViewById(R.id.signIn);
        sign_up = findViewById(R.id.signUp);
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
                email_value = email.getEditText().getText().toString();
            }
        });
        Objects.requireNonNull(password.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    password.setError("Password Belum diisi");
                    password.setErrorEnabled(true);
                }
                else{
                    password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                password_value = password.getEditText().getText().toString();
            }
        });
    }

    private  void loginCheck(){
        sign_in.setOnClickListener(v -> {
            if(email_value.equals("admin") && password_value.equals("admin")){
                Toast.makeText(getApplicationContext(),"Selamat anda sudah login",Toast.LENGTH_LONG).show();
                email.setErrorEnabled(false);
                password.setErrorEnabled(false);
                Log.i(TAG, "onClick: "+email_value);
                Log.i(TAG, "onClick: "+password_value);
                Log.i(TAG, "onClick: berhasil login");
            }
            else if(email_value.length() > 0 && password_value.length() == 0){
                password.setError("Harap mengisi password terlebih dahulu");
                password.setErrorEnabled(true);
            }
            else if(email_value.length() == 0 && password_value.length() > 0){
                email.setError("Harap mengisi email terlebih dahulu");
                email.setErrorEnabled(true);
            }
            else if(email_value.length() == 0 && password_value.length() == 0){
                Toast.makeText(getApplicationContext(),"Harap mengisi Input Terlebih Dahulu",
                        Toast.LENGTH_LONG).show();
                email.setErrorEnabled(true);
                password.setErrorEnabled(true);
            }
            else if(email_value.equals("admin") && !password_value.equals("admin")){
                Toast.makeText(getApplicationContext(),"Email atau Password yang anda " +
                        "salah",Toast.LENGTH_LONG).show();
            }
            else if(!email_value.equals("admin") && password_value.equals("admin")){
                Toast.makeText(getApplicationContext(),"Email atau Password yang anda " +
                        "salah",Toast.LENGTH_LONG).show();
            }
            else if(!email_value.equals("admin") && !password_value.equals("admin")){
                Toast.makeText(getApplicationContext(),"Email atau Password yang anda " +
                        "salah",Toast.LENGTH_LONG).show();
            }
        });
    }


}