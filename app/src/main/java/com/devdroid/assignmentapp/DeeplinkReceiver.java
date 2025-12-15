package com.devdroid.assignmentapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.netcore.android.SMTBundleKeys;

public class DeeplinkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("The deeplink Source Class",intent.toString());
        try {
            Bundle bundleExtra = intent.getExtras();
            if (bundleExtra != null) {
                String deepLinkSource = bundleExtra.getString(SMTBundleKeys.SMT_KEY_DEEPLINK_SOURCE);
                String deepLink = bundleExtra.getString(SMTBundleKeys.SMT_KEY_DEEPLINK);
                String customPayload = bundleExtra.getString(SMTBundleKeys.SMT_KEY_CUSTOM_PAYLOAD);
                assert deepLink != null;
                assert customPayload!=null;
                Log.d("Deeplink Receiver","Deeplink Value "+deepLink + " Deeplink Payload "+customPayload);


                assert deepLinkSource != null;
                Log.d("The deeplink Source", deepLinkSource);
                if (deepLink != null && !deepLink.isEmpty()) {

                      if(deepLink.startsWith("cart")){
                          Intent intent1 = new Intent(context, CartActivity.class);
                          intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          context.startActivity(intent1);
                      }
                      // handle deepLink for redirection. Here you can use deepLinkSource for redirection if required

                }
                if (customPayload != null && !customPayload.isEmpty()) {
                    // handle your custom payload based on deeplink source like below if required
                }
            }
        } catch (Throwable t) {
            Log.e("DeeplinkReceiver", "Error occurred in deeplink:" + t.getLocalizedMessage());
        }
    }
}


