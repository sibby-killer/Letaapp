package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmust.leta.databinding.ActivitySelectRoleBinding;

import java.util.HashMap;
import java.util.Map;

public class SelectRoleActivity extends AppCompatActivity {
    
    private ActivitySelectRoleBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String selectedRole = "student"; // Default to student

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectRoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        
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
        String uid = mAuth.getCurrentUser().getUid();
        
        binding.btnContinue.setEnabled(false);
        
        // Create user document in Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("role", selectedRole);
        user.put("email", mAuth.getCurrentUser().getEmail());
        user.put("createdAt", System.currentTimeMillis());
        
        db.collection("users").document(uid).set(user)
                .addOnSuccessListener(aVoid -> {
                    // Role saved successfully, navigate to appropriate dashboard
                    navigateToRoleDashboard(selectedRole);
                })
                .addOnFailureListener(e -> {
                    binding.btnContinue.setEnabled(true);
                    Toast.makeText(SelectRoleActivity.this, 
                            "Failed to save role: " + e.getMessage(), 
                            Toast.LENGTH_SHORT).show();
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
