package com.devdroid.assignmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.netcore.android.Smartech;
import java.lang.ref.WeakReference;

import org.json.JSONObject;


import java.util.HashMap;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.validators.IntegrationValidator;
import io.hansel.hanselsdk.Hansel;


public class MainActivity extends AppCompatActivity {
    private static final String WEBHOOK_URL = "https://webhook.site/b42e20fc-69b3-4c3d-b335-eea52ebafb23";
    ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    String[] foregroundPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


//    protected void onDestroy() {
//        super.onDestroy();
//
//
//
//            Log.d("Myapp","FLUSHING To webhook");
//            // 🚀 Flush all logs to webhook when app is closed
//            SdkLogCaptor.INSTANCE.flushToWebhook(WEBHOOK_URL);
//
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());   // <-- MUST BE FIRST before accessing views

        Hansel.pairTestDevice(getIntent().getDataString());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestForegroundPermission();

        // Now the view exists — SAFE
        binding.btnStartBG.setOnClickListener(v -> startBGService());

        String androidId = Settings.Secure.getString(
                getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        Log.d("Android ID", androidId);

        binding.goToSignup.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
            finish();
        });

        binding.login.setOnClickListener(view -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            login(email, password);
        });
    }



    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        //setContentView(R.layout.activity_launcher);
//        binding =ActivityMainBinding.inflate(getLayoutInflater());
//      //  Smartech.getInstance(new WeakReference<>(this)).initializeSdk((Application) getApplicationContext());
//        Hansel.pairTestDevice(getIntent().getDataString());
//
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        requestForegroundPermission();
//
//        findViewById(R.id.btnStartBG).setOnClickListener(v -> startBGService());
//
//
//        setContentView(binding.getRoot());
//        String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//       // Smartech.getInstance(new WeakReference<>(getApplicationContext())).setUserIdentity("TestAllen");
//        // Now you can use the androidId variable which holds the Android ID
//        Log.d("Android ID", androidId);
//
//        binding.goToSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,SignupActivity.class));
//                finish();
//            }
//        });
//        binding.login.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                String email =binding.email.getText().toString();
//                String password =binding.password.getText().toString();
//                login(email,password);
//            }
//        });
//    }
    private void login(String email, String password){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("in Progress");
        progressDialog.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email.trim(),password.trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.cancel();
                         }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler(Looper.getMainLooper());
                        Smartech.getInstance(new WeakReference<>(getApplicationContext())).login("9447563439");

                   //     Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackEvent("user_login");
//                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .login("918129445709");
//                        }, 0);
//
//                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .trackEvent("User Login");
//                        }, 1000);
//
//                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .trackEvent("Sign up");
//                        }, 2000);
//
//                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .trackEvent("Select seats");
//                        }, 3000);
//
//                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .trackEvent("User Logout");
//                        }, 3000);

                        handler.postDelayed(() -> {
//                            Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                                    .trackEvent("Language preference selected");
                        }, 3000);



                        HashMap<String, Object> payload = new HashMap<>();
//                        payload.put("FIRST NAME", "Allen");
//                        payload.put("LAST NAME", "Thomson");
                        payload.put("AGE", 5);

                   //    Hansel.getUser().setUserId(email);
                      //  Smartech.getInstance(new WeakReference<>(getApplicationContext())).login(email);
//                        Smartech.getInstance(new WeakReference<>(getApplicationContext())).updateUserProfile(payload);
                        startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                        binding.getRoot().post(() -> finish());

                    }
                });
    }



    private void requestForegroundPermission() {
        ActivityCompat.requestPermissions(
                this,
                foregroundPermissions,
                100
        );
    }

    private void requestBackgroundPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                101
        );
    }

    // ---- GET CURRENT LOCATION -----
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        Log.d("LOC", "LAT: " + location.getLatitude() +
                                " LNG: " + location.getLongitude());
                    }
                });

        // Real-time updates
        LocationRequest request = new LocationRequest.Builder(5000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build();

        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult result) {
            Location location = result.getLastLocation();
            if (location != null) {
                Log.d("LOC", "Realtime: " + location.getLatitude() + ", " + location.getLongitude());
            }
        }
    };

    // START BACKGROUND SERVICE
    private void startBGService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestBackgroundPermission();
        }

        Intent intent = new Intent(this, LocationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }


    protected void onStart(){

        super.onStart();



     //   Smartech.getInstance(new WeakReference<>(this)).login(em);

//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
//        }
//  Branch.enableLogging();
//        IntegrationValidator.validate(MainActivity.this);
//        BranchUniversalObject buo = new BranchUniversalObject()
//                .setCanonicalIdentifier("content/12345")
//                .setTitle("My Content Title")
//                .setContentMetadata(new ContentMetadata().addCustomMetadata("key1", "value1"));
//        LinkProperties lp = new LinkProperties()
//                .setChannel("facebook")
//                .setFeature("sharing")
//                .setCampaign("content 123 launch")
//                .setStage("new user")
//                .addControlParameter("$desktop_url", "https://example.com/home")
//                .addControlParameter("custom", "data");

// Generate a short URL for the Branch Universal Object
       // String url = buo.getShortUrl(this.getApplicationContext, lp);
//        try{
//            Branch.sessionBuilder(this).withCallback(new Branch.BranchUniversalReferralInitListener() {
//                @Override
//
//                public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
//                    System.out.println("this is called");
//                    if ((branchUniversalObject.getContentMetadata().getCustomMetadata().containsKey("androidOnly"))) {
//                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//                            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
//                            binding.getRoot().post(() -> finish());
//                            System.out.println("successcall");
//                            String deeplinkPath = branchUniversalObject.getContentMetadata().getCustomMetadata().get("androidOnly");
//                            if (deeplinkPath.equals("cartActivity")) {
//                                Intent intent = new Intent(MainActivity.this, CartActivity.class);
//                                startActivity(intent);
//                                System.out.println("navigatedTocart");
//                                finish();
//                            } else if (deeplinkPath.equals("profileActivity")) {
//                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                                startActivity(intent);
//                                System.out.println("navigatedToprofile");
//                                finish();
//                            }
//                        }
//                        else {
//                            Toast.makeText(MainActivity.this, "Please Login to Continue", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                    if ((branchUniversalObject.getContentMetadata().getCustomMetadata().containsKey("productModel"))){
//                        System.out.println("productModelClicked");
//                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                        startActivity(intent);
//                        System.out.println("navigatedToproduct");
//                        finish();
//                    }
//
//                    if (error != null) {
//                        Log.e("BranchSDK_Tester", "branch init failed. Caused by -" + error.getMessage());
//                    } else {
//                        Log.i("BranchSDK_Tester", "branch init complete!");
//                        if (branchUniversalObject != null) {
//                            Log.i("BranchSDK_Tester", "title " + branchUniversalObject.getTitle());
//                            Log.i("BranchSDK_Tester", "CanonicalIdentifier " + branchUniversalObject.getCanonicalIdentifier());
//                            Log.i("BranchSDK_Tester", "metadata " + branchUniversalObject.getContentMetadata().convertToJson());
//                        }
//
//                        if (linkProperties != null) {
//                            Log.i("BranchSDK_Tester", "Channel " + linkProperties.getChannel());
//                            Log.i("BranchSDK_Tester", "control params " + linkProperties.getControlParams());
//                        }
//                    }
//
//                }
//            }).withData(this.getIntent().getData()).init();
//        } catch (Exception e){
//            System.out.println("just ignore");
//        }


        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            binding.getRoot().post(() -> finish());
        }
}

//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        this.setIntent(intent);
//        System.out.println("secondIntentCalled");
//        if (intent != null && intent.hasExtra("branch_force_new_session") && intent.getBooleanExtra("branch_force_new_session",false)) {
//            Branch.sessionBuilder(this).withCallback(new Branch.BranchReferralInitListener() {
//                @Override
//                public void onInitFinished(JSONObject referringParams, BranchError error) {
//                    if (error != null) {
//                        Log.e("BranchSDK_Tester", error.getMessage());
//                    } else if (referringParams != null) {
//                        Log.i("BranchSDK_Tester", referringParams.toString());
//                    }
//                }
//            }).reInit();
//        }
//    }
}