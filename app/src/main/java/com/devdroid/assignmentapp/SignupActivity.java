package com.devdroid.assignmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.devdroid.assignmentapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                finish();
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name =binding.name.getText().toString();
                String email =binding.email.getText().toString();
                String password =binding.password.getText().toString();
                createAccount(name,email,password);
            }
        });
    }
    private void createAccount(String name, String email, String password){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        binding.progressBar.setVisibility(binding.getRoot().setTitle());
//        binding.progressBar.setVisibility(binding.getRoot().VISIBLE);

        fAuth.createUserWithEmailAndPassword(email.trim(),password.trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>(){
            public void onSuccess(AuthResult authResult){
                UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileChangeRequest);
                Toast.makeText(SignupActivity.this,"Account Created", Toast.LENGTH_SHORT).show();
                binding.name.setText("");
                binding.email.setText("");
                binding.password.setText("");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}