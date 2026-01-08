package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityVendorProfileBinding;

public class VendorProfileActivity extends AppCompatActivity {
    
    private ActivityVendorProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupUI();
        setupListeners();
    }
    
    private void setupUI() {
        binding.tvVendorName.setText("Kwa Mathe");
        binding.tvRating.setText("4.7");
        binding.tvVendorDescription.setText("Authentic campus street food. Famous for smokie pasua and fresh juices.");
        binding.tvTotalOrders.setText("1,234");
        binding.tvRevenue.setText("KES 45K");
        binding.tvMenuItems.setText("24");
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnEdit.setOnClickListener(v -> {
            // TODO: Navigate to edit profile
        });
        
        binding.btnManageMenu.setOnClickListener(v -> {
            Intent intent = new Intent(VendorProfileActivity.this, MenuManagerActivity.class);
            startActivity(intent);
        });
        
        binding.btnViewOrders.setOnClickListener(v -> {
            // TODO: Navigate to orders list
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
