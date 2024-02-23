package com.devdroid.assignmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.validators.IntegrationValidator;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_launcher);
        binding =ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
                finish();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String email =binding.email.getText().toString();
                String password =binding.password.getText().toString();
                login(email,password);
            }
        });
    }
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
                        Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void onStart(){

        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
//        }
  Branch.enableLogging();
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
        Branch.sessionBuilder(this).withCallback(new Branch.BranchUniversalReferralInitListener() {
            @Override
            public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
                System.out.println("this is called");
                if ((branchUniversalObject.getContentMetadata().getCustomMetadata().containsKey("androidOnly"))) {
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                        System.out.println("successcall");
                        String deeplinkPath = branchUniversalObject.getContentMetadata().getCustomMetadata().get("androidOnly");
                        if (deeplinkPath.equals("cartActivity")) {
                            Intent intent = new Intent(MainActivity.this, CartActivity.class);
                            startActivity(intent);
                            System.out.println("navigatedTocart");
                            finish();
                        } else if (deeplinkPath.equals("profileActivity")) {
                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            System.out.println("navigatedToprofile");
                            finish();
                        }
        }
                    else {
                        Toast.makeText(MainActivity.this, "Please Login to Continue", Toast.LENGTH_SHORT).show();

                    }
                }
                if ((branchUniversalObject.getContentMetadata().getCustomMetadata().containsKey("productModel"))){
                    System.out.println("productModelClicked");
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    System.out.println("navigatedToproduct");
                    finish();
                }

                if (error != null) {
                    Log.e("BranchSDK_Tester", "branch init failed. Caused by -" + error.getMessage());
                } else {
                    Log.i("BranchSDK_Tester", "branch init complete!");
                    if (branchUniversalObject != null) {
                        Log.i("BranchSDK_Tester", "title " + branchUniversalObject.getTitle());
                        Log.i("BranchSDK_Tester", "CanonicalIdentifier " + branchUniversalObject.getCanonicalIdentifier());
                        Log.i("BranchSDK_Tester", "metadata " + branchUniversalObject.getContentMetadata().convertToJson());
                    }

                    if (linkProperties != null) {
                        Log.i("BranchSDK_Tester", "Channel " + linkProperties.getChannel());
                        Log.i("BranchSDK_Tester", "control params " + linkProperties.getControlParams());
                    }
                }

            }
        }).withData(this.getIntent().getData()).init();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
        }
}

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        System.out.println("secondIntentCalled");
        if (intent != null && intent.hasExtra("branch_force_new_session") && intent.getBooleanExtra("branch_force_new_session",false)) {
            Branch.sessionBuilder(this).withCallback(new Branch.BranchReferralInitListener() {
                @Override
                public void onInitFinished(JSONObject referringParams, BranchError error) {
                    if (error != null) {
                        Log.e("BranchSDK_Tester", error.getMessage());
                    } else if (referringParams != null) {
                        Log.i("BranchSDK_Tester", referringParams.toString());
                    }
                }
            }).reInit();
        }
    }
}