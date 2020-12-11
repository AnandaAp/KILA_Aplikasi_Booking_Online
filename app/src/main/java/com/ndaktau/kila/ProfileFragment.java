package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {

    public TextView getBtnLogOut() {
        return btnLogOut;
    }

    public void setBtnLogOut(View view) {
        this.btnLogOut = view.findViewById(R.id.btnLogOut);
    }

    /////////////////////////////////////////////////////
    private TextView btnLogOut,tvjdlnama,tvnama,tvemail;
    private String[] name,emai,password;
    private FirebaseFirestore db ;
    private ArrayList<User> user = new ArrayList<User>();
    private FirebaseAuth mAuth;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setDb();
        setBtnLogOut(view);
        getBtnLogOut().setOnClickListener(v -> logout());

        ///////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        tvjdlnama =view.findViewById(R.id.tvjdlnama);
        tvnama =view.findViewById(R.id.tvnama);
        tvemail=view.findViewById(R.id.tvemail);
        getAllDocument();


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
        requireActivity().finish();
        startActivity(intent);
    }

    private void getAllDocument() {
        Log.i(TAG, "getAllDocument: masuk logi");
        String string= mAuth.getCurrentUser().getEmail().toString();
        db.collection("users")
                .document(string)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tvjdlnama.setText(documentSnapshot.get("Name").toString());
                        tvemail.setText(documentSnapshot.get("Email").toString());
                        tvnama.setText(documentSnapshot.get("Name").toString());
                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            int posi = 0;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                tvjdlnama.setText(document.get("name").toString());
//                                tvnama.setText(document.get("name").toString());
//                                tvemail.setText(document.get("email").toString());
//                                User us= new User(document.get("Name").toString()
//                                        , document.get("Email").toString()
//                                        , document.get("Password").toString());
//                                user.add(us);
//                                Log.i(TAG, "onComplete: " + user.get(posi).getName());
//                                posi++;
                            }
//                            Log.i(TAG, "getAllDocument: User = " + user.size());
//                            name= new String[user.size()];
//                            emai = new String[user.size()];
//                            password = new String[user.size()];
//                            int pos = 0;
//                            for (int i = user.size(); i > 0; i--) {
//                                name[pos] = user.get(pos).getName();
//                                emai[pos] = user.get(pos).getEmail();
//                                password[pos] = user.get(pos).getPassword();
//                                Log.i(TAG, "onCreateView: mahasiswa = " + name[pos] + "\n"
//                                        + "nim : " + emai[pos] + "\n" + "phone : " + password[pos]);
//                                pos++;

//                            }
//                            pos=0;
//                            for (int i = user.size(); i > 0; i--) {
//                                if (emai[pos].equals(mAuth.getCurrentUser().getEmail())){
//                                        tvemail.setText(emai[pos]);
//                                        tvjdlnama.setText(name[pos]);
//                                        tvnama.setText(name[pos]);
//                                }
//                            }

//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

    }

