package com.mmust.leta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityVendorDashboardBinding;

public class VendorDashboardActivity extends AppCompatActivity {
    
    private ActivityVendorDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupListeners();
    }
    
    private void setupListeners() {
        // Notifications Button
        binding.btnNotifications.setOnClickListener(v -> {
            // TODO: Show notifications
        });
        
        // Tab Layout
        binding.tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                // TODO: Switch between Active Orders, Menu, History
                switch (tab.getPosition()) {
                    case 0: // Active Orders
                        break;
                    case 1: // Menu
                        break;
                    case 2: // History
                        break;
                }
            }
            
            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
            
            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
        });
        
        // Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_dashboard) {
                // Already on dashboard
                return true;
            } else if (itemId == R.id.nav_menu) {
                // TODO: Navigate to menu manager
                return true;
            } else if (itemId == R.id.nav_orders) {
                // TODO: Navigate to orders
                return true;
            } else if (itemId == R.id.nav_profile) {
                // TODO: Navigate to profile
                return true;
            }
            
            return false;
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
