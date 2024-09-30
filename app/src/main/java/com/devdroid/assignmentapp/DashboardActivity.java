package com.devdroid.assignmentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.devdroid.assignmentapp.databinding.ActivityDashboardBinding;
import com.netcore.android.Smartech;
import com.netcore.android.smartechappinbox.SmartechAppInbox;
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback;
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxMessageType;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder;
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType;
import com.webengage.personalization.WEPersonalization;
import com.webengage.sdk.android.WebEngage;

import org.w3c.dom.Document;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.hansel.hanselsdk.Hansel;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        Hansel.pairTestDevice(getIntent().getDataString());
        HashMap<String,Object> payload = new HashMap<>();
        payload.put("card_name","test_Allen");
      //  findViewById(R.id.invisibleView).setTag(R.id.hansel_ignore_view,true);
      //  findViewById(R.id.viewToBeIgnoredExcludingChildren).setTag(R.id.hansel_ignore_view_excluding_children, true);//Supported from Nudge SDK v8.8.2

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("TestAppInbox");
       // SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
        SMTAppInboxRequestBuilder builder = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
                .setCallback(new SMTInboxCallback() {
                    @Override
                    public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {

                    }

                    @Override
                    public void onInboxProgress() {

                    }


                    @Override
                    public void onInboxFail() {

                    }
                })
                .setCategory(categoryList).setLimit(10).build();
        WebEngage.get().analytics().screenNavigated("dashboard_screen");
       // WEPersonalization.Companion.get().registerWEPlaceholderCallback("placeholder_identifier", this);
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
                try{
                   // eventAPI.makeApiCall();
                    SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
                    smartechAppInbox.displayAppInbox(getApplicationContext());

                 //   SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
                    ArrayList<SMTInboxMessageData> messages = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.INBOX_MESSAGE);
                    assert messages != null;
                    for (SMTInboxMessageData message : messages) {
                        System.out.println(message); // Assumes toString() is overridden in SMTInboxMessageData
                    }
                    smartechAppInbox.getAppInboxMessages(builder);
                    int count = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.INBOX_MESSAGE);
                    smartechAppInbox.displayAppInbox(getApplicationContext());
                    Log.d("smartTechAppInboxMessageCount", String.valueOf(count));
                    Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackEvent("card_click", payload);

                    //  startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                } catch(Exception e){
                    e.printStackTrace();
                }



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