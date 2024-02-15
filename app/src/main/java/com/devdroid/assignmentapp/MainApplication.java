package com.devdroid.assignmentapp;

import android.app.Application;

import io.branch.referral.Branch;

public class MainApplication extends Application {

    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}
