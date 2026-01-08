package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmust.leta.databinding.ActivityRiderRecruitmentBinding;

import java.util.HashMap;
import java.util.Map;

public class RiderRecruitmentActivity extends AppCompatActivity {
    
    private ActivityRiderRecruitmentBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiderRecruitmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        
        setupListeners();
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnApply.setOnClickListener(v -> applyAsRider());
    }
    
    private void applyAsRider() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please sign in first", Toast.LENGTH_SHORT).show();
            return;
        }
        
        binding.btnApply.setEnabled(false);
        
        String uid = mAuth.getCurrentUser().getUid();
        
        // Update user role to rider
        Map<String, Object> updates = new HashMap<>();
        updates.put("role", "rider");
        updates.put("riderStatus", "pending_approval");
        updates.put("appliedAt", System.currentTimeMillis());
        
        db.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RiderRecruitmentActivity.this, 
                            "Application submitted! We'll review it soon.", 
                            Toast.LENGTH_LONG).show();
                    
                    // Navigate to rider home
                    Intent intent = new Intent(RiderRecruitmentActivity.this, RiderHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    binding.btnApply.setEnabled(true);
                    Toast.makeText(RiderRecruitmentActivity.this, 
                            "Application failed: " + e.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
