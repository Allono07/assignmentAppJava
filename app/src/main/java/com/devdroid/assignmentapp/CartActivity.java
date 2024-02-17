package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devdroid.assignmentapp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCartItem();
    }

    private void getCartItem() {
        FirebaseFirestore.getInstance()
                .collection("cart")
               // .whereEqualTo("")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    }
                });
    }
}