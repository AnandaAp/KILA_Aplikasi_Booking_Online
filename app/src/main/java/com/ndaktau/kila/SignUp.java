package com.ndaktau.kila;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUp extends Fragment {
    private Button btnSignUp;
    private ImageView back;
    private TextInputLayout name,email,pass,birthday;

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
        init(view);
        NavToSignIn();
        return view;
    }

    private void init(View view){
        name =  view.findViewById(R.id.nameLayout);
        email = view.findViewById(R.id.emSignUpLayout);
        pass =  view.findViewById(R.id.passSignUpLayout);
        birthday =  view.findViewById(R.id.birthdayLayout);
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
                    email.setError("Nama Belum Terisi");
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
                    pass.setError("Nama Belum Terisi");
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
        Objects.requireNonNull(birthday.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 ) {
                    birthday.setError("Tanggal lahir Belum Terisi");
                    birthday.setErrorEnabled(true);
                }
                else {
                    birthday.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void NavToSignIn(){
//        int currentNightMode = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        switch (currentNightMode) {
//            case Configuration.UI_MODE_NIGHT_NO:
//                back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.NavToSignIn));
//                break;
//            case Configuration.UI_MODE_NIGHT_YES:
//                back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.NavToSignInNight));
//                break;
//        }
        back.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.NavToSignIn));
    }
}