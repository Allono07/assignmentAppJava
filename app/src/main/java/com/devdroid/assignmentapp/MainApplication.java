package com.devdroid.assignmentapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

//import com.android.netcoresdkcapturer.SdkLogCaptor;


import com.android.smartechsdkvalidator.*;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.netcore.android.Smartech;

import com.netcore.android.smartechpush.SmartPush;
import com.netcore.android.smartechpush.notification.SMTNotificationOptions;
import com.netcore.android.smartechpush.notification.channel.SMTNotificationChannel;
import com.netcore.android.utility.config.SmartechConfig;
import com.webengage.personalization.WEPersonalization;
import com.webengage.sdk.android.WebEngage;
import com.webengage.sdk.android.actions.render.PushNotificationData;
import com.webengage.sdk.android.callbacks.PushNotificationCallbacks;
import android.content.Context;


import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.hansel.core.logger.HSLLogLevel;
import io.hansel.hanselsdk.Hansel;
import io.hansel.ujmtracker.HanselInternalEventsListener;
import io.hansel.ujmtracker.HanselTracker;

public class MainApplication extends Application implements PushNotificationCallbacks{

  //   private Context context;

  //   public MainApplication(Context context) {
      //   this.context = context;
     //  }
     public void onCreate() {
        super.onCreate();


////         SingleTonClass.INSTANCE.printName("as");
//        SingleTonClasss sb = new SingleTonClasss();
//        sb.printName();
//SingleTonClasss.printName1.INSTANCE.sayHello();
         SmartechValidator.getInstance(this).initialize(
                  "http://13.217.226.104:5001//api/logs/aj12",
                  1.0f,
                 true  // SDK will not capture or send logs
         );

         Smartech.getInstance(new WeakReference<>(getApplicationContext())).initializeSdk(this);
         Smartech.getInstance(new WeakReference<>(getApplicationContext())).setDebugLevel(9);
         HSLLogLevel.all.setEnabled(true);
         HSLLogLevel.mid.setEnabled(true);
         HSLLogLevel.debug.setEnabled(true);
         Hansel.enableDebugLogs();



         SMTNotificationChannel.Builder smtBuilder = new SMTNotificationChannel.Builder(
                 "45",
                 "test_channel", NotificationManager.IMPORTANCE_MAX
                 );

//To set the description to the channel add below method.


//To set sound to channel, add below method. (Note that sound name must be without extention.)
         smtBuilder.setNotificationSound(String.valueOf((R.raw.example)));

         SMTNotificationChannel smtNotificationChannel = smtBuilder.build();

         SmartPush.getInstance(new WeakReference<>(getApplicationContext())).createNotificationChannel(smtNotificationChannel);

//         SdkLogCaptor.getInstance(this).initialize(
//                 "https://icily-shoeless-deidra.ngrok-free.dev/api/logs/aj12",
//                 1.0f,
//                 payload -> {
//                     // payload is org.json.JSONObject
//                     Log.d("MyApp", "Captured payload: " + payload.toString());
//                 }
//         );
//         Smartech.getInstance(new WeakReference<>(getApplicationContext())).initializeSdk(this, "3e3e838020149ee53689841da279c341", "", "");
      //  Smartech.getInstance(new WeakReference<>(this)).initializeSdk(this);

         SmartechConfig config = new SmartechConfig();
//Set this flag to true if you want to ignore internet checks while making api calls and showing inapps
     //    config.disableInternetCheck(true);
//By default hansel sdk is enabled and initialized, to disable hansel you can set the flag to true
    //     config.disableHansel(false);
//Set the complete Smartech config before initializing the sdk
     //    Smartech.getInstance(new WeakReference<>(this)).setConfig(config);

//initialize the sdk
         //Smartech.getInstance(new WeakReference<>(this)).initializeSdk(this);

         // Initialize the new modularized SdkLogCaptor (auto-captures and uploads logs)

         // registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, new WebEngageConfig.Builder().setWebEngageKey("~1341056cd").setDebugMode(true).build()));
     //   WebEngage.registerPushNotificationCallback(new PushNotificationCallbacksImpl());

         Smartech smartech = Smartech.getInstance(new WeakReference<>(this.getApplicationContext()));
        // SmartechAppInbox smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(getApplicationContext()));
         HSLLogLevel.all.setEnabled(true);
         HSLLogLevel.mid.setEnabled(true);
         HSLLogLevel.debug.setEnabled(true);
         Hansel.enableDebugLogs();

        //Smartech.getInstance(new WeakReference<>(this.getApplicationContext())).login("8129445708");


         DeeplinkReceiver deeplinkReceiver = new DeeplinkReceiver();
         IntentFilter filter = new IntentFilter("com.smartech.EVENT_PN_INBOX_CLICK");
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
             getApplicationContext().registerReceiver(deeplinkReceiver, filter, Context.RECEIVER_EXPORTED);
         } else {
             ContextCompat.registerReceiver(getApplicationContext(), deeplinkReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
         }

//         SMTAppInboxRequestBuilder builder = new SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
//                 .setCallback(new SMTInboxCallback() {
//                     @Override
//                     public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
//
//                         assert list != null;
//                         Log.d("APPINNBOX API CALL-----",list.toString());
//                     }
//
//                     @Override
//                     public void onInboxProgress() {
//
//                     }
//
//
//                     @Override
//                     public void onInboxFail() {
//
//                     }
//                 })
//                 .setLimit(2).build();

//

         SMTNotificationOptions options = new SMTNotificationOptions(this);
         //options.setSmallIcon(String.valueOf(R.drawable.spicemoney));
   //    options.setBrandLogo(String.valueOf(R.drawable.spicemoney));//e.g.ic_action_play is sample name for icon
      // options.setSmallIconTransparent(String.valueOf(R.drawable.spicemoney));
    //  options.setLargeIcon(String.valueOf(R.drawable.spicemoney));
         options.setTransparentIconBgColor("#033049");
       options.setPlaceHolderIcon(String.valueOf(R.drawable.spicemoney));//e.g.ic_notification is sample name for placeholder icon
         SmartPush.getInstance(new WeakReference(getApplicationContext())).setNotificationOptions(options);



       //  smartechAppInbox.displayAppInbox(getApplicationContext());
         smartech.setDebugLevel(9);



         HanselInternalEventsListener hanselInternalEventsListener = (eventName, dataFromHansel) -> {

//             Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                     .trackEvent(eventName, (HashMap<String, Object>) dataFromHansel);

             if ("hansel_branch_tracker".equals(eventName) && dataFromHansel instanceof HashMap) {

                 // Make a copy so we don't mutate the original
                 HashMap<String, Object> eventData = new HashMap<>((HashMap<String, Object>) dataFromHansel);

                 // Convert hsl_counter (Integer) to String
                 Object counterValue = eventData.get("hsl_counter");
                 if (counterValue instanceof Integer) {
                     String counterAsString = String.valueOf(counterValue); // converts Integer to String
                     eventData.put("hsl_counter", counterAsString);

                     Log.d("DATA TYPE OF THE HSL_COUNTER",counterAsString.getClass().getName().toString());
                 }

                 // Send modified data to Smartech
//                 Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                         .trackEvent(eventName, eventData);
             }
             else{
//                 Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                         .trackEvent(eventName, (HashMap<String, Object>) dataFromHansel);
             }

//
//
//
//             if (dataFromHansel != null) {
//                 Log.d("HanselDebug", "dataFromHansel class: " + dataFromHansel.getClass().getName());
//                 HashMap<?, ?> map = (HashMap<?, ?>) dataFromHansel;
//                 for (Map.Entry<?, ?> entry : map.entrySet()) {
//                     Object key = entry.getKey();
//                     Object value = entry.getValue();
//
//
//                     String valueType = (value != null) ? value.getClass().getName() : "null";
//                     Log.d("HanselDebug", "Key: " + key + ", Value type: " + valueType);
//                 }
//             } else {
//                 Log.d("HanselDebug", "dataFromHansel is null");
//             }
//
//
//                 // Create a new map (or work on a copy to avoid mutating the original)
//                 HashMap<String, Object> eventData = new HashMap<>((HashMap<String, Object>) dataFromHansel);
//
//                 // Remove the unwanted key
//                 eventData.remove("hsl_counter");
//
//                 // Pass the modified map to trackEvent
//                 Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                         .trackEvent(eventName, eventData);





//             Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                     .trackEvent(eventName, (HashMap<String, Object>) dataFromHansel);
         };



//         HanselInternalEventsListener hanselInternalEventsListener = (eventName, dataFromHansel) -> {
//             HashMap<String, Object> stringifiedMap = new HashMap<>();
//
//             dataFromHansel.forEach((key, value) -> stringifiedMap.put((String) key,
//                     value != null ? value.toString() : null));
//
//             Smartech.getInstance(new WeakReference<>(getApplicationContext()))
//                     .trackEvent(eventName, stringifiedMap);
//         };

         HanselTracker.registerListener(hanselInternalEventsListener);








        WebEngage.registerPushNotificationCallback(this);
        WEPersonalization.Companion.get().init();
         Smartech.getInstance(new WeakReference<>(getApplicationContext())).trackAppInstallUpdateBySmartech();

         try {
             SmartPush smartPush = SmartPush.getInstance(new WeakReference<>(getApplicationContext()));
             smartPush.fetchAlreadyGeneratedTokenFromFCM();
         } catch (Exception e) {
             Log.e(TAG, "Fetching FCM token failed.");
         }
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            try {
                String token = task.getResult();
                WebEngage.get().setRegistrationID(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


//


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

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Myapp","FLUSHING To webhook");
        // 🚀 Flush all logs to webhook when app is closed
        // Note: onTerminate() is rarely called on real devices, but flush is handled automatically
        SmartechValidator.getInstance(this).flush();
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
