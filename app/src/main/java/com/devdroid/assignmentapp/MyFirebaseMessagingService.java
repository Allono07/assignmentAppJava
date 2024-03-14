package com.devdroid.assignmentapp;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.webengage.sdk.android.WebEngage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        if (data != null) {
            if (data.containsKey("source") && "webengage".equals(data.get("source"))) {
                WebEngage.get().receive(data);
            }
//            if(data.containsKey("deeplink")){
//                String deeplinkVal= data.get("deeplink");
//                if(deeplinkVal.equals("profile")){
//                    Intent intent = new Intent(this, CartActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Required to start an activity from a non-activity context.
//                    startActivity(intent);
//                }
//            }
        }
    }
    public void onNewToken(String s) {
        super.onNewToken(s);
        WebEngage.get().setRegistrationID(s);
    }
}
