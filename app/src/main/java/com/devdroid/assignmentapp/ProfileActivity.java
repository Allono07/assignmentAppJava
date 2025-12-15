package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.devdroid.assignmentapp.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.netcore.android.Smartech;
import com.netcore.android.smartechpush.SmartPush;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
ActivityProfileBinding binding;

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String,Object> payload = new HashMap<>();
        payload.put("AGE",1);

        Smartech.getInstance(new WeakReference<>(getApplicationContext())).updateUserProfile(payload);
    }

    private OrdersAdapter ordersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
     //   binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

   //     SmartPush.getInstance(new WeakReference(getApplicationContext())).showInstantNotificationDoubleOptIn();


        ordersAdapter=new OrdersAdapter(this);
        binding.ordersRecycler.setAdapter(ordersAdapter);
//        HashMap<String,Object> payload = new HashMap<>();
//        payload.put("AGE",1);
//
//        Smartech.getInstance(new WeakReference<>(getApplicationContext())).updateUserProfile(payload);
        binding.ordersRecycler.setLayoutManager(new LinearLayoutManager(this));
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    protected void onStart(){
        super.onStart();
      //  getOrders();
    }

//    private void getOrders(){
//        FirebaseFirestore.getInstance().collection("orders")
//                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid()).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot ds:dsList){
//                            OrderModel orderModel=ds.toObject(OrderModel.class);
//
//                            ordersAdapter.addProduct(orderModel);
//                        }
//                    }
//                });
//    }
}