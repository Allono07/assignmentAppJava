package com.devdroid.assignmentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.hansel.actions.configs.HanselConfigs;
import io.hansel.hanselsdk.Hansel;
import io.hansel.ujmtracker.HanselTracker;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onResume() {
        super.onResume();


        JSONObject jsonObj = HanselConfigs.getJSONObject("jsonObj", null);

        if (jsonObj != null) {
            Iterator<String> keys = jsonObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = null;
                try {
                    value = jsonObj.get(key);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(key + " jsonObjValueOfHansel: " + value);
            }
        } else {
            System.out.println("JSONObject is null or config not found.");
        }
        //Use the deep config you created on the dashboard
        JSONArray array = HanselConfigs.getJSONArray("jsonObj", null);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    System.out.println(" jsonObjValueOfHansel ARRAY "+array.get(i));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("JSONArray is null or config not found.");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
//        handleDeepLinks(getIntent());

        Hansel.pairTestDevice(getIntent().getDataString());
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();

        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData!=null){
            String data = appLinkData.toString();
            Log.d("APPLINK DATA",appLinkData.toString());

        }
       // Smartech.getInstance(new WeakReference<>(this)).trackEvent("dashboard_screen");


        boolean isSmartechHandledDeeplink = Smartech.getInstance(new WeakReference<>(this)).isDeepLinkFromSmartech(getIntent());
        if (!isSmartechHandledDeeplink) {
            //Handle deeplink on app side
        }

        HashMap<String,Object>payload_1 = new HashMap<>();


        payload_1.put("user_name","allen");
        payload_1.put("age","12");

        HashMap<String,Object> payload = new HashMap<>();
        payload.put("card_name",2);
        payload.put("card_type","alle");
        payload.put("payment_type","alle");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("prname", "Apples");
        item1.put("price", 10.1);
        item1.put("age", 10);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("prname", "Bananas");
        item2.put("price", 5);

        // Add them to the list
        items.add(item1);
        items.add(item2);
        payload.put("items",items);
      //  findViewById(R.id.invisibleView).setTag(R.id.hansel_ignore_view,true);
      //  findViewById(R.id.viewToBeIgnoredExcludingChildren).setTag(R.id.hansel_ignore_view_excluding_children, true);//Supported from Nudge SDK v8.8.2


//
//        JSONObject jsonObj = HanselConfigs.getJSONObject("jsonObj", null);
//
//        if (jsonObj != null) {
//            Iterator<String> keys = jsonObj.keys();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                Object value = null;
//                try {
//                    value = jsonObj.get(key);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(key + " jsonObjValueOfHansel: " + value);
//            }
//        } else {
//            System.out.println("JSONObject is null or config not found.");
//        }
//        //Use the deep config you created on the dashboard
//        JSONArray array = HanselConfigs.getJSONArray("jsonObj", null);
//        if (array != null) {
//            for (int i = 0; i < array.length(); i++) {
//                try {
//                    System.out.println(" jsonObjValueOfHansel ARRAY "+array.get(i));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } else {
//            System.out.println("JSONArray is null or config not found.");
//        }


//        ArrayList<String> categoryList = new ArrayList<>();
        List<SMTInboxMessageData> inboxList = new ArrayList<>();
//        categoryList.add("TestAppInbox");
       // SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
//        SMTAppInboxRequestBuilder builder = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
//                .setCallback(new SMTInboxCallback() {
//                    @Override
//                    public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
//
//                        assert list != null;
//
//                        Log.d("APPINNBOX API CALL-----", "Total items: " + list.size());
//                        Log.d("APPINNBOX API CALL-----",list.toString());
//
//                        inboxList.addAll(list);
//                        Log.d("APPINNBOX API CALL customListSize-----", String.valueOf(inboxList.size()));
//                    }
//
//                    @Override
//                    public void onInboxProgress() {
//
//                    }
//
//
//                    @Override
//                    public void onInboxFail() {
//
//                    }
//                })
//                .setLimit(10).build();




       /// SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
//        smartechAppInbox.getAppInboxMessages(builder);
//
        SmartechAppInbox smartechAppInbox1 = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
        ArrayList<SMTInboxMessageData> messages1 = smartechAppInbox1.getAppInboxMessages(SMTAppInboxMessageType.READ_MESSAGE);

        assert messages1 != null;
        Log.d("APPINNBOX API CALL LOCAL READ MESSAGE", messages1.toString());
        ArrayList<SMTInboxMessageData> messages2 = smartechAppInbox1.getAppInboxMessages(SMTAppInboxMessageType.INBOX_MESSAGE);
        assert messages2 != null;
        Log.d("APPINNBOX API CALL LOCAL ALL MESSAGE", messages2.toString());


//        int countAll = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.INBOX_MESSAGE);
//        int countUnread = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.UNREAD_MESSAGE);


//        Log.d("APPINNBOX MESSAGE COUNT ALL", String.valueOf(countAll));
//        Log.d("APPINNBOX MESSAGE COUNT UNREAD", String.valueOf(countUnread));


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
//            Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackEvent("profile_screen");
            startActivity(new Intent(DashboardActivity.this, CartActivity.class));

        }
    });

        binding.person.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try{
                   // eventAPI.makeApiCall();
                //    Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackEvent("cart_screen");

                    SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
                    smartechAppInbox.displayAppInbox(getApplicationContext());

                 //   SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
                    ArrayList<SMTInboxMessageData> messages = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.INBOX_MESSAGE);
                    assert messages != null;
                    for (SMTInboxMessageData message : messages) {
                        System.out.println(message); // Assumes toString() is overridden in SMTInboxMessageData
                    }
                   // smartechAppInbox.getAppInboxMessages(builder);
                    int count = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.INBOX_MESSAGE);
                    smartechAppInbox.displayAppInbox(getApplicationContext());

                    Log.d("smartTechAppInboxMessageCount", String.valueOf(count));
// 1️Step 1
Handler handler = new Handler(Looper.getMainLooper());
                    Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                            .trackEvent("logout_event",payload_1);
                    Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                            .trackEvent("new_event", payload);
                    HanselTracker.logEvent("fast_game", "ctp", payload);

// 2️ Step 2 after 2 seconds
                    new Thread(() -> sendTestEvents()).start();
                    handler.postDelayed(() -> {
//                        Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                .logoutAndClearUserIdentity(true);

                        // 3️⃣ Step 3 after another 2 seconds
                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .setUserIdentity("9447563434");

                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                                    .trackEvent("profile_clicked", payload);
HashMap<String,Object> map1 = new HashMap<>();
map1.put("user_type","1");
map1.put("event_date","2025-10-12 12:12:12+0530");
map1.put("event_date_1","2025-10-12 12:12:12+0530");
                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                                    .trackEvent("appointment_click", map1);
                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                                    .trackEvent("product_purchase", payload);
                        }, 2000);
                    }, 2000);


                    startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                } catch(Exception e){
                    e.printStackTrace();
                }



            }
        });




    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLinks(intent);
    }


    private void handleDeepLinks(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            String encodedURL = intent.getDataString();

            if (Intent.ACTION_VIEW.equals(action) && encodedURL != null) {
                Log.d("App Link", encodedURL);
                new Thread(() -> {
                    try {
                        URL originalURL = new URL(encodedURL);
                        HttpURLConnection urlConnection = (HttpURLConnection) originalURL.openConnection();
                        urlConnection.setInstanceFollowRedirects(false);
                        String location = urlConnection.getHeaderField("Location");
                        if (location != null) {
                            URL resolvedURL = new URL(location);
                            Log.d("App Link", resolvedURL.toString());
                        } else {
                            Log.d("App Link", "No redirect location found");
                        }
                    } catch (MalformedURLException ex) {
                        Log.e("App Link", Log.getStackTraceString(ex));
                    } catch (IOException ex) {
                        Log.e("App Link", Log.getStackTraceString(ex));
                    }
                }).start();
            } else {
                Log.d("App Link", "Intent action not VIEW or URL was null");
            }
        }
    }

//    private void handleDeepLinks(Intent intent) {
//        if (intent != null) {
//            String action = intent.getAction();
//            String encodedURL = intent.getDataString();
//            assert encodedURL != null;
//            Log.d("App Link", encodedURL);
//            if (Intent.ACTION_VIEW.equals(action)) {
//                Log.d("App Link", encodedURL);
//                new Thread(() -> {
//                    try {
//                        URL originalURL = new URL(encodedURL);
//                        HttpURLConnection urlConnection = (HttpURLConnection) originalURL.openConnection();
//                        urlConnection.setInstanceFollowRedirects(false);
//                        String location = urlConnection.getHeaderField("Location");
//                        if (location != null) {
//                            URL resolvedURL = new URL(location);
//                            Log.d("App Link", resolvedURL.toString());
//                        } else {
//                            Log.d("App Link", "No redirect location found");
//                        }
//                    } catch (MalformedURLException ex) {
//                        Log.e("App Link", Log.getStackTraceString(ex));
//                    } catch (IOException ex) {
//                        Log.e("App Link", Log.getStackTraceString(ex));
//                    }
//                }).start();
//            }
//        }
//    }

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



    private void sendTestEvents() {
        for (int i = 1; i <= 100; i++) {
            String eventName = "product_purchase_" + i;

            // Example payload (customize this as needed)
            HashMap<String, Object> payload = new HashMap<>();
            payload.put("eventIndex", i);
            payload.put("timestamp", System.currentTimeMillis());
            payload.put("value", (int) (Math.random() * 1000));

            // Track event
            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
                    .trackEvent(eventName, payload);

            System.out.println("→ Sent " + eventName);

            try {
                Thread.sleep(1000); // 1 second gap (optional)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}