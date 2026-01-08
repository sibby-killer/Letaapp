package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mmust.leta.databinding.ActivityCartBinding;
import com.mmust.leta.utils.ConfigManager;

public class CartActivity extends AppCompatActivity {
    
    private ActivityCartBinding binding;
    private ConfigManager config;
    
    // Sample cart totals
    private double itemTotal = 550.0;
    private double deliveryFee = 50.0;
    private double discount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        config = ConfigManager.getInstance(this);
        
        setupRecyclerView();
        updateTotals();
        setupListeners();
    }
    
    private void setupRecyclerView() {
        binding.rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set adapter with cart items
    }
    
    private void updateTotals() {
        binding.tvItemTotal.setText(String.format("KES %.2f", itemTotal));
        binding.tvDeliveryFee.setText(String.format("KES %.2f", deliveryFee));
        binding.tvDiscount.setText(String.format("- KES %.2f", discount));
        
        double total = itemTotal + deliveryFee - discount;
        binding.tvTotal.setText(String.format("KES %.2f", total));
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnApplyReferral.setOnClickListener(v -> {
            String code = binding.etReferralCode.getText().toString().trim();
            if (!code.isEmpty()) {
                applyReferralCode(code);
            } else {
                Toast.makeText(this, "Please enter a referral code", Toast.LENGTH_SHORT).show();
            }
        });
        
        binding.btnProceedToCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("total", itemTotal + deliveryFee - discount);
            startActivity(intent);
        });
    }
    
    private void applyReferralCode(String code) {
        // TODO: Validate referral code with backend
        if (config.isReferralSystemEnabled()) {
            int discountPercent = config.getReferralDiscountPercent();
            discount = itemTotal * discountPercent / 100.0;
            updateTotals();
            Toast.makeText(this, "Referral code applied! " + discountPercent + "% off", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
