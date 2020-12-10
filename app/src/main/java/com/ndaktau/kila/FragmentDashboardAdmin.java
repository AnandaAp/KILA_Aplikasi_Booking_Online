package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


public class FragmentDashboardAdmin extends Fragment {
    private Button logOut;
    private Button tmbhLapangan;
    private Button listLapangan;






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
        setLogOut(view);
        setTmbhLapangan(view);
        setListLapangan(view);
        getLogOut().setOnClickListener(v -> logout());
        getTmbhLapangan().setOnClickListener(v -> moveToTambahLapangan(v));
        getListLapangan().setOnClickListener(v -> moveToListLap(v));
        return view;
    }

    private void moveToTambahLapangan(View view){
//        TambahLapangan tambahLapangan = new TambahLapangan();
//        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager()
//                                                    .beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container,tambahLapangan);
//        fragmentTransaction.commit();
        Navigation.findNavController(view).navigate(R.id.tambahLapangan);
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

    private void moveToListLap(View view){
//        Navigation.findNavController(view).navigate(R.id.listLapangan);
//        Intent intent = new Intent(getActivity(), ListLapangan.class);
//        startActivity(intent);
        Navigation.findNavController(view).navigate(R.id.listLapangan);
    }


    public Button getTmbhLapangan() {
        return tmbhLapangan;
    }

    public void setTmbhLapangan(View view) {
        this.tmbhLapangan = view.findViewById(R.id.btnTambahLap);
    }

    public Button getListLapangan() {
        return listLapangan;
    }

    public void setListLapangan(View view) {
        this.listLapangan = view.findViewById(R.id.list_lapangan);
    }

}