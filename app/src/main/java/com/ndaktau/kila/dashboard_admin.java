package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;


public class dashboard_admin extends Fragment {
    private Button logOut;

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth mAuth;

    public Button getLogOut() {
        return logOut;
    }

    public void setLogOut(View view) {
        this.logOut = view.findViewById(R.id.logOut);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        setmAuth();
        SessionManagement sessionManagement = new SessionManagement(requireActivity());
        setLogOut(view);
        getLogOut().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    private void logout(){
        SessionManagement sessionManagement = new SessionManagement(requireContext());
        sessionManagement.removeSesion();
        moveToLogin();
    }
    private void moveToLogin(){
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
        requireActivity().finish();
    }
}