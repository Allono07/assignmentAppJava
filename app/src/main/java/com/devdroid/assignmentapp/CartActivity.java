package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.CurrencyType;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    public static List<CartModel> cartsItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BranchUniversalObject buo = new BranchUniversalObject();

        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cartAdapter = new CartAdapter(this);
        binding.cartRecycler.setAdapter((cartAdapter));
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));

        getCartItem();

        binding.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            cartsItemList=cartAdapter.getSelectedItems();
                //Toast.makeText(CartActivity.this, ""+cartList.size(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this, OrderPlacingActivity.class));
            }
        });
        // ATTENTION: This was auto-generated to handle app links.
//        Intent appLinkIntent = getIntent();
//        String appLinkAction = appLinkIntent.getAction();
//        Uri appLinkData = appLinkIntent.getData();
    }

    private void getCartItem() {
        FirebaseFirestore.getInstance()
                .collection("cart")
               .whereEqualTo("sellerUid", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot ds:dsList){
                    CartModel cartModel =ds.toObject(CartModel.class);
                    cartAdapter.addProduct(cartModel);
                }
                        new BranchEvent(BRANCH_STANDARD_EVENT.VIEW_CART)
                                .logEvent(getApplicationContext());
                    }
                });
    }
}