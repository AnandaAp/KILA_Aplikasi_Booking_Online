package com.ndaktau.kila;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {
    private static final String TAG = "Dashboard";
    private FirebaseAuth mAuth;

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_dashboard);
        checkUser(mAuth.getCurrentUser());
    }

    private void checkUser(FirebaseUser currentUser){
        DocumentReference docRef = db.collection("users").document(currentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        if(document.get("Tipe Akun").toString().equals("Administrator")){
                            moveToAdminDashboard();
                        }
                        else if(document.get("Tipe Akun").toString().equals("Pengguna")){
                            //moveToPenggunaDashboard();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void moveToAdminDashboard(){
//        Intent i = new Intent(Dashboard.this,dashboard_admin.class);
//        startActivity(i);
        dashboard_admin fragment = new dashboard_admin();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
//    private void moveToPenggunaDashboard(){
////        Intent i = new Intent(Dashboard.this,dashboard_admin.class);
////        startActivity(i);
//        fragmentuser fragment = new fragmentuser();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container,fragment);
//        transaction.commit();
//    }
}