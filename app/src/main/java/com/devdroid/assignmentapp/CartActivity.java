package com.devdroid.assignmentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.netcore.android.Smartech;
import com.netcore.android.smartechappinbox.SmartechAppInbox;
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback;
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData;
import com.netcore.android.smartechappinbox.network.model.SMTPayload;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxMessageType;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder;
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType;
import com.netcore.android.smartechpush.SmartPush;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
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
    protected void onResume() {
        super.onResume();

        HashMap<String,Object> payload = new HashMap<>();
        payload.put("AGE",5);

     //    Smartech.getInstance(new WeakReference<>(getApplicationContext())).updateUserProfile(payload);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BranchUniversalObject buo = new BranchUniversalObject();

        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cartAdapter = new CartAdapter(this);
        binding.cartRecycler.setAdapter((cartAdapter));
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));

        List<SMTInboxMessageData> inboxList = new ArrayList<>();

        SmartPush.getInstance(new WeakReference<>(this)).initiateNotificationDoubleOptIn();

        getCartItem();

        testRaceConditionInboxAPI();



        SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
        ArrayList<SMTInboxMessageData> messages = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.UNREAD_MESSAGE);

        for(SMTInboxMessageData message : messages){
            Log.d("MESSAGE TRID",message.getSmtPayload().getTrid());
        }




//      Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackEvent("profile_screen");
//        SMTAppInboxRequestBuilder builder = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
//                .setCallback(new SMTInboxCallback() {
//                    @Override
//                    public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
//
//
//                        assert list != null;
//
//                        Log.d("APPINNBOX API CALL SERVER-----", "Total items: " + list.size());
//                        Log.d("APPINNBOX API CALL SERVER -----",list.toString());
//                        inboxList.addAll(list);
//                        Log.d("APPINNBOX API CALL customListSize-----", String.valueOf(inboxList.size()));
//                    }
//
//                    @Override
//                    public void onInboxProgress() {
//                        Log.d("APPINNBOX API CALL IN PROGRESS customListSize-----", String.valueOf(inboxList.size()));
//                    }
//
//
//                    @Override
//                    public void onInboxFail() {
//
//                    }
//                })
//                .setLimit(10).build();
//
//        SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
//        smartechAppInbox.getAppInboxMessages(builder);




//        SmartechAppInbox smartechAppInbox1 = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
//        ArrayList<SMTInboxMessageData> messages1 = smartechAppInbox1.getAppInboxMessages(SMTAppInboxMessageType.READ_MESSAGE);
//
//        assert messages1 != null;
//        Log.d("APPINNBOX API CALL LOCAL READ MESSAGE", messages1.toString());
//        ArrayList<SMTInboxMessageData> messages2 = smartechAppInbox1.getAppInboxMessages(SMTAppInboxMessageType.INBOX_MESSAGE);
//        assert messages2 != null;
//        Log.d("APPINNBOX API CALL LOCAL ALL MESSAGE", messages2.toString());

//        List<SMTInboxMessageData> newInbox = new ArrayList<>();
//
//        for(SMTInboxMessageData message:messages2){
//            newInbox.add(message);
//            Log.d("APPINNNBOX-----",message.getSmtPayload().getAppInboxCategory());
//        }
//       for(SMTInboxMessageData m: messages2){
//
//           smartechAppInbox1.markMessageAsClicked(m.getSmtPayload().getDeeplink(),m);
//       }
//
//
//        Log.d("APPINNBOX API CALL LOCAL READ1 MESSAGE", messages1.toString());

//try {
//    System.out.println("Login Event captured inside the try block - before");
//
//    Smartech.getInstance(new WeakReference<>(getApplicationContext())).login("identity here");
//    System.out.println("Login Event captured inside the try block - after");
//
//}
//catch (Exception e){
//    System.out.println("Login Event captured inside the catch block - before");
//    e.getMessage();
//    Smartech.getInstance(new WeakReference<>(getApplicationContext())).login("identity here");
//    System.out.println("Login Event captured inside the catch block - after");
//
//}




//    SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
//        smartechAppInbox.getAppInboxMessages(builder);
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


    private void testRaceConditionInboxAPI() {

        // Builder 1
        SMTAppInboxRequestBuilder builder1 = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
                .setCallback(new SMTInboxCallback() {
                    @Override
                    public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
                        Log.d("CALL_1_THREAD", "SUCCESS size=" + (list != null ? list.size() : 0));
                        if (list != null) {
                            for (SMTInboxMessageData msg : list) {
                                Log.d("INSERT_ATTEMPT_1", "Trying to insert tr_id=" );
                                // your insert code
                            }
                        }
                    }
                    @Override public void onInboxProgress() {}
                    @Override public void onInboxFail() {}
                })
                .setLimit(10)
                .build();


        // Builder 2
        SMTAppInboxRequestBuilder builder2 = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
                .setCallback(new SMTInboxCallback() {
                    @Override
                    public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
                        Log.d("CALL_2_THREAD", "SUCCESS size=" + (list != null ? list.size() : 0));
                        if (list != null) {
                            for (SMTInboxMessageData msg : list) {
                                Log.d("INSERT_ATTEMPT_2", "Trying to insert tr_id=" );

                            }
                        }
                    }
                    @Override public void onInboxProgress() {}
                    @Override public void onInboxFail() {}
                })
                .setLimit(10)
                .build();


        SmartechAppInbox inbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));


        new Thread(() -> inbox.getAppInboxMessages(builder1)).start();
        new Thread(() -> inbox.getAppInboxMessages(builder2)).start();

        Log.d("RACE_TEST", "Both API calls triggered in parallel.");
    }



}