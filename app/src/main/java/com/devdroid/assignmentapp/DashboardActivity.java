package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

import android.hardware.lights.LightsManager;
import android.os.Bundle;

import com.devdroid.assignmentapp.databinding.ActivityDashboardBinding;

import org.w3c.dom.Document;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        getProducts();
    }

    private void getProducts() {
        FirebaseFirestore.getInstance()
                .collection("producst")
                .whereEqualTo("show",true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList){
                            ProductModel productModel =ds.toObject(ProductModel.class);
                        }
                    }
                });
    }
}