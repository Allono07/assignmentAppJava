package com.devdroid.assignmentapp;

import static com.devdroid.assignmentapp.CartActivity.cartsItemList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivityOrderPlacingBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;

public class OrderPlacingActivity extends AppCompatActivity {
    ActivityOrderPlacingBinding binding;
    int mainTotal =0;
    private String name, number, address, cityName,postalCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderPlacingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.name.getText().toString();
                address=binding.address.getText().toString();
                cityName=binding.cityName.getText().toString();
                number=binding.number.getText().toString();
                placeOrder();
            }
        });

    }

    private void placeOrder() {
        //123456
        //100000 999999
        String orderNumber = String.valueOf(getRandomNumber(100000,999999));
        OrderModel orderModel = new OrderModel(orderNumber
        ,name,number,cityName, address, String.valueOf(mainTotal),"220",null,
                "TCS",String.valueOf(Calendar.getInstance().getTimeInMillis()),"Pending");
        FirebaseFirestore.getInstance().collection("orders")
                .document(orderNumber)
                .set(orderModel);
        for(int i=0;i<cartsItemList.size();i++){
            CartModel cartModel =cartsItemList.get(i);
            cartModel.setOrderNumber(orderNumber);
            String id = UUID.randomUUID().toString();
            cartModel.setCartId(id);

            FirebaseFirestore.getInstance()
                    .collection("orderProducts")
                    .document()
                    .set(cartModel);

        }
        BranchUniversalObject buo = new BranchUniversalObject()
                new ContentMetadata();
            //    .setAddress(address);
        new BranchEvent(BRANCH_STANDARD_EVENT.PURCHASE)
                .setCurrency(CurrencyType.INR)
                .setDescription("item purchased by the customer")
                .setRevenue(mainTotal)
                .logEvent(getApplicationContext());
        Toast.makeText(this, "Item Purchased", Toast.LENGTH_SHORT).show();

    }
    public static  int getRandomNumber(int min, int max){
        return (new Random()).nextInt((max-min)+1)+min;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i=0;i<cartsItemList.size();i++){
            CartModel cartModel = cartsItemList.get(i);
            int price= Integer.parseInt(cartModel.getProductPrice());
            int qty= Integer.parseInt(cartModel.getProductQty());
            int total=price*qty;
            mainTotal+=total;
        }
        binding.expense.setText(String.valueOf(mainTotal));
        binding.delivery.setText("220");
        binding.totalCod.setText(String.valueOf((mainTotal+220)));
    }
}