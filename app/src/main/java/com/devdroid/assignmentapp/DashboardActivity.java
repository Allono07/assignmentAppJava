package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.view.View;

import com.devdroid.assignmentapp.databinding.ActivityDashboardBinding;

import org.w3c.dom.Document;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        EventAPIProfileActivity eventAPI = new EventAPIProfileActivity();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        productsAdapter = new ProductsAdapter(this);
        binding.productRecyler.setAdapter(productsAdapter);
        binding.productRecyler.setLayoutManager(new LinearLayoutManager(this));


        getProducts();
    binding.cart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(DashboardActivity.this, CartActivity.class));

        }
    });

        binding.person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventAPI.makeApiCall();
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));

            }
        });

    }

    private void getProducts() {
        FirebaseFirestore.getInstance()
                .collection("products")
                .whereEqualTo("show",true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList){
                            ProductModel productModel =ds.toObject(ProductModel.class);
                            productsAdapter.addProduct(productModel);
                        }
                    }
                });
    }
}