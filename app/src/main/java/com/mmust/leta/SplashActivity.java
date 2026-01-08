package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mmust.leta.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    
    private ActivitySplashBinding binding;
    private FirebaseAuth mAuth;
    private static final int SPLASH_DELAY = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        
        // Animate progress bar
        animateProgressBar();
        
        // Check authentication after delay
        new Handler(Looper.getMainLooper()).postDelayed(this::checkAuthenticationStatus, SPLASH_DELAY);
    }
    
    private void animateProgressBar() {
        // Simulate loading progress
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                if (progress <= 100) {
                    binding.progressBar.setProgress(progress);
                    progress += 5;
                    new Handler(Looper.getMainLooper()).postDelayed(this, 50);
                }
            }
        });
    }
    
    private void checkAuthenticationStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        
        if (currentUser != null) {
            // User is signed in, route to appropriate dashboard
            UserRouter.routeUser(this, currentUser.getUid());
        } else {
            // No user signed in, go to auth screen
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
