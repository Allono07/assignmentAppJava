package com.devdroid.assignmentapp;

import static com.devdroid.assignmentapp.CartActivity.cartsItemList;

import androidx.appcompat.app.AppCompatActivity;
/// Each Products page

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devdroid.assignmentapp.databinding.ActivityDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import com.devdroid.assignmentapp.CartActivity.*;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.*;
import io.branch.referral.util.ShareSheetStyle;
import io.branch.referral.SharingHelper;
import io.branch.referral.BranchShareSheetBuilder;



public class DetailActivity extends AppCompatActivity {
ActivityDetailBinding binding;
private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BranchUniversalObject buo = new BranchUniversalObject();

        Intent intent =getIntent();

         productModel = (ProductModel)  intent.getSerializableExtra("model");

        binding.title.setText(productModel.getTitle());
        binding.description.setText(productModel.getDescription());
        binding.price.setText(productModel.getPrice());
        Glide.with(binding.getRoot())
                .load(productModel.getImage())
                .into(binding.image);
    binding.share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
//        private void  shareBranchLink(){
//            // Create a Branch Universal Object
//            BranchUniversalObject buo1 = new BranchUniversalObject()
//                    .setCanonicalIdentifier("content/12345");
//
//            // Create a Link Properties instance
//            LinkProperties lp = new LinkProperties()
//                    .setChannel("facebook")
//                    .setFeature("sharing")
//                    .setCampaign("content 123 launch")
//                    .setStage("new user")
//                    .addControlParameter("$desktop_url", "https://example.com/home")
//                    .addControlParameter("custom", "data")
//                    .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));
//
//            // Show Sharesheet
//            Branch.getInstance().share(MainActivity.this, buo1, lp, new Branch.BranchNativeLinkShareListener() {
//                        @Override
//                        public void onLinkShareResponse(String sharedLink, BranchError error) {}
//                        @Override
//                        public void onChannelSelected(String channelName) { }
//                    },
//                    "Sharing Branch Short URL",
//                    "Using Native Chooser Dialog");
//        }
    binding.addTocart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showBottomSheet(0);
       //     addToCart();
        }
    });
binding.buyNow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showBottomSheet(1);
        //CartModel cartModel = new CartModel(null,productModel.getTitle(),productModel.getImage(),productModel.getPrice(),productModel.getPrice(), pro)
    }
});

    }

    private void showBottomSheet(int i) { ///When Clicked Addto Cart, the bottom sheet will pop to enter the qunatity
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view= LayoutInflater.from(DetailActivity.this).inflate(R.layout.bottom_layout,(LinearLayout)findViewById(R.id.mainLayout),
               false)
;        bottomSheetDialog.setContentView(view);
        EditText qty=view.findViewById(R.id.qty);
        Button btn = view.findViewById(R.id.checkOut);
        bottomSheetDialog.show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = qty.getText().toString();

                if(i==0){
                    //Add to Cart
                    addToCart(quantity);

                    bottomSheetDialog.cancel();
                }

                else if(i==1){
                CartModel cartModel = new CartModel(null, productModel.getTitle(),productModel.getImage(),productModel.getPrice(),quantity,FirebaseAuth.getInstance().getUid(), null);
                     cartsItemList = new ArrayList<>();
                     cartsItemList.add(cartModel);
                     startActivity(new Intent(DetailActivity.this,OrderPlacingActivity.class));
                     bottomSheetDialog.cancel();

                }


            }
        });
    }

    private void addToCart(String qty) { //addTo Cart Event and also logging event to branch
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding");
        progressDialog.setMessage("Item in Cart");
        progressDialog.show();

        String id = UUID.randomUUID().toString();
        CartModel cartModel = new CartModel(id, productModel.getTitle(),productModel.getImage(),productModel.getPrice(),qty, FirebaseAuth.getInstance().getUid(),null);
        double doubleValue = Double.parseDouble(productModel.getPrice());

        FirebaseFirestore.getInstance()
                .collection("cart")
                .document(id)
                .set(cartModel)
                .addOnCompleteListener(task ->{
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        new BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
                                .setCurrency(CurrencyType.INR)
                                .setDescription(productModel.getDescription())
                                .setRevenue(doubleValue)
                                .logEvent(getApplicationContext());
                        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    }                    Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                });


    }
}