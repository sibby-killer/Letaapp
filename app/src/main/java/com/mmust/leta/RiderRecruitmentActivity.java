package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityRiderRecruitmentBinding;
import com.mmust.leta.utils.SupabaseClient;

import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class RiderRecruitmentActivity extends AppCompatActivity {
    
    private ActivityRiderRecruitmentBinding binding;
    private SupabaseClient supabaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiderRecruitmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        supabaseClient = SupabaseClient.getInstance(this);
        
        setupListeners();
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnApply.setOnClickListener(v -> applyAsRider());
    }
    
    private void applyAsRider() {
        String userId = supabaseClient.getCurrentUserId();
        if (userId == null) {
            Toast.makeText(this, "Please sign in first", Toast.LENGTH_SHORT).show();
            return;
        }
        
        binding.btnApply.setEnabled(false);
        
        // Update user role to rider
        JSONObject updates = new JSONObject();
        try {
            updates.put("role", "rider");
            updates.put("rider_status", "pending_approval");
            updates.put("applied_at", System.currentTimeMillis());
            
            supabaseClient.updateUser(userId, updates).thenAccept(success -> {
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(RiderRecruitmentActivity.this, 
                                "Application submitted! We'll review it soon.", 
                                Toast.LENGTH_LONG).show();
                        
                        // Navigate to rider home
                        Intent intent = new Intent(RiderRecruitmentActivity.this, RiderHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        binding.btnApply.setEnabled(true);
                        Toast.makeText(RiderRecruitmentActivity.this, 
                                "Application failed. Please try again.", 
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }).exceptionally(e -> {
                runOnUiThread(() -> {
                    binding.btnApply.setEnabled(true);
                    Toast.makeText(RiderRecruitmentActivity.this, 
                            "Application failed: " + e.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                });
                return null;
            });
        } catch (Exception e) {
            binding.btnApply.setEnabled(true);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
