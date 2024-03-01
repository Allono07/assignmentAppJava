package com.devdroid.assignmentapp;

import android.app.Application;

import com.webengage.sdk.android.WebEngageActivityLifeCycleCallbacks;
import com.webengage.sdk.android.WebEngageConfig;

import io.branch.referral.Branch;

public class MainApplication extends Application {

    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, new WebEngageConfig.Builder().setWebEngageKey("~1341056cd").setDebugMode(true).build()));

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}
