package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {
//    public Button getBtnLogOut() {
//        return btnLogOut;
//    }
//
//    public void setBtnLogOut(View view) {
//        this.btnLogOut = view.findViewById(R.id.btnLogOut);
//    }
//
//    private Button btnLogOut;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        setBtnLogOut(view);
//        getBtnLogOut().setOnClickListener(v -> logout());
        return view;
    }

//    private void logout(){
//        SessionManagement sessionManagement = new SessionManagement(requireContext());
//        sessionManagement.removeSesion();
//        moveToLogin();
//    }
//    private void moveToLogin(){
//        Intent intent = new Intent(requireActivity(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Coba_intent_extra","percobaan");
//        requireActivity().finish();
//        startActivity(intent);
//    }
}