package com.devdroid.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devdroid.assignmentapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}