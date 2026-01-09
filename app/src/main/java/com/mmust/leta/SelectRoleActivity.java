package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivitySelectRoleBinding;
import com.mmust.leta.utils.SupabaseClient;

public class SelectRoleActivity extends AppCompatActivity {
    
    private ActivitySelectRoleBinding binding;
    private SupabaseClient supabase;
    private String selectedRole = "student"; // Default to student

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectRoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize
        supabase = SupabaseClient.getInstance(this);
        
        setupListeners();
    }
    
    private void setupListeners() {
        // Default selection
        binding.rbStudent.setChecked(true);
        
        // Student Card Click
        binding.cardStudent.setOnClickListener(v -> {
            selectedRole = "student";
            binding.rbStudent.setChecked(true);
            binding.rbVendor.setChecked(false);
        });
        
        // Vendor Card Click
        binding.cardVendor.setOnClickListener(v -> {
            selectedRole = "vendor";
            binding.rbVendor.setChecked(true);
            binding.rbStudent.setChecked(false);
        });
        
        // Radio Button Click
        binding.rbStudent.setOnClickListener(v -> {
            selectedRole = "student";
            binding.rbVendor.setChecked(false);
        });
        
        binding.rbVendor.setOnClickListener(v -> {
            selectedRole = "vendor";
            binding.rbStudent.setChecked(false);
        });
        
        // Continue Button
        binding.btnContinue.setOnClickListener(v -> saveRoleAndContinue());
        
        // Back Button
        binding.btnBack.setOnClickListener(v -> finish());
    }
    
    private void saveRoleAndContinue() {
        String userId = supabase.getCurrentUserId();
        String accessToken = supabase.getAccessToken();
        
        if (userId == null || accessToken == null) {
            Toast.makeText(this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }
        
        binding.btnContinue.setEnabled(false);
        binding.btnContinue.setText("Saving...");
        
        // Get email from intent or use empty string
        String email = getIntent().getStringExtra("email");
        if (email == null) {
            email = "";
        }
        
        // Create user profile in Supabase
        supabase.createUserProfile(userId, email, selectedRole, accessToken, 
            new SupabaseClient.DataCallback() {
                @Override
                public void onSuccess(String data) {
                    runOnUiThread(() -> {
                        Toast.makeText(SelectRoleActivity.this, 
                                "Profile created!", Toast.LENGTH_SHORT).show();
                        
                        // Navigate to appropriate dashboard
                        navigateToRoleDashboard(selectedRole);
                    });
                }
                
                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        binding.btnContinue.setEnabled(true);
                        binding.btnContinue.setText(R.string.continue_btn);
                        Toast.makeText(SelectRoleActivity.this, 
                                "Failed to save role: " + error, 
                                Toast.LENGTH_SHORT).show();
                    });
                }
            });
    }
    
    private void navigateToRoleDashboard(String role) {
        Intent intent;
        
        switch (role) {
            case "student":
                intent = new Intent(SelectRoleActivity.this, StudentHomeActivity.class);
                break;
            case "vendor":
                intent = new Intent(SelectRoleActivity.this, VendorDashboardActivity.class);
                break;
            case "rider":
                intent = new Intent(SelectRoleActivity.this, RiderHomeActivity.class);
                break;
            default:
                intent = new Intent(SelectRoleActivity.this, StudentHomeActivity.class);
        }
        
        startActivity(intent);
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
