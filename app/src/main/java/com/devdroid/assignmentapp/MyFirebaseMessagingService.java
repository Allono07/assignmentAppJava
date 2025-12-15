package com.devdroid.assignmentapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netcore.android.smartechpush.SmartPush;
import com.webengage.sdk.android.WebEngage;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        boolean isPnHandledBySmartech = true;

        Log.d("Push Notification Payload",remoteMessage.toString());
        Log.d("Push Notification Payload Data",remoteMessage.getData().toString());
//        Log.d("Push Notification Payload Raw Data", remoteMessage.getRawData().toString());
        Map<String, String> data = remoteMessage.getData();
        Log.d("payload",data.toString());
        if (data.containsKey("source") && "webengage".equals(data.get("source"))) {
            WebEngage.get().receive(data);
        } else if (data.containsKey("smtSrc")) {
            SmartPush.getInstance(new WeakReference<Context>(getApplicationContext())).handleRemotePushNotification(remoteMessage);
        } else {
            getFirebaseMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
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
    private  void getFirebaseMessage(String title, String body){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"push")
                .setSmallIcon(R.drawable.person_img)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(102,builder.build());
    }
    public void onNewToken(String s) {
        super.onNewToken(s);
        SmartPush.getInstance(new WeakReference<Context>(this)).setDevicePushToken(s);
        WebEngage.get().setRegistrationID(s);
    }
}
