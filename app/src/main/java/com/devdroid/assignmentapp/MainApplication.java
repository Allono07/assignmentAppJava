package com.devdroid.assignmentapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.webengage.personalization.WEPersonalization;
import com.webengage.sdk.android.WebEngage;
import com.webengage.sdk.android.WebEngageActivityLifeCycleCallbacks;
import com.webengage.sdk.android.WebEngageConfig;
import com.webengage.sdk.android.actions.render.PushNotificationData;
import com.webengage.sdk.android.callbacks.PushNotificationCallbacks;
import android.content.Context;

import com.webengage.sdk.android.actions.render.PushNotificationData;



import io.branch.referral.Branch;

 public class MainApplication extends Application implements PushNotificationCallbacks{

    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, new WebEngageConfig.Builder().setWebEngageKey("~1341056cd").setDebugMode(true).build()));
     //   WebEngage.registerPushNotificationCallback(new PushNotificationCallbacksImpl());
        WebEngage.registerPushNotificationCallback(this);
        WEPersonalization.Companion.get().init();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            try {
                String token = task.getResult();
                WebEngage.get().setRegistrationID(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
     public PushNotificationData onPushNotificationReceived(Context context, PushNotificationData pushNotificationData) {

         if (pushNotificationData != null) {
             Log.d(Constants.TAG, "onPushNotificationReceived " + pushNotificationData.getExperimentId());
             // Modify the pushNotificationData here if necessary
             Log.d(TAG, "onPushNotificationReceived: "+pushNotificationData.getExperimentId() );
             Log.d(TAG, "onPushNotificationReceived12: "+pushNotificationData.getChannelId() );
             Log.d(TAG, "onPushNotificationReceived: "+pushNotificationData.getVariationId() );
             pushNotificationData.getVariationId();
         } else {
             // Handle the case where pushNotificationData is null if necessary
         }

         return pushNotificationData;
     }

     public void onPushNotificationShown(Context context, PushNotificationData pushNotificationData){
        Log.d(Constants.TAG, "onPushNotificationShown " + pushNotificationData.getExperimentId());
     }
     public boolean onPushNotificationClicked(Context context, PushNotificationData pushNotificationData){
         Log.d(Constants.TAG, "onPushNotificationclicker " + pushNotificationData.getExperimentId());
         if(pushNotificationData.getCustomData().containsKey("deeplink")){
             String deeplinkVal = pushNotificationData.getCustomData().getString("deeplink");
             if(deeplinkVal.equals("cartActivity")){
                 Intent intent = new Intent(this, CartActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }

            Log.d("mylogs","priting something here");
            //String deeplinkVal= pushNotificationData.getPushPayloadJSON().keys().equals("deeplink");
//             if(deeplinkVal.equals("profile")){
//                 Intent intent = new Intent(this, CartActivity.class);
//                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 startActivity(intent);
//
//             }

         }
         return  true;
     }

     @Override
     public void onPushNotificationDismissed(Context context, PushNotificationData pushNotificationData) {

     }

     @Override
     public boolean onPushNotificationActionClicked(Context context, PushNotificationData pushNotificationData, String s) {
         return false;
     }


 }
