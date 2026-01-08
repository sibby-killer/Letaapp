package com.mmust.leta;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityCheckoutBinding;
import com.mmust.leta.utils.ConfigManager;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class CheckoutActivity extends AppCompatActivity {
    
    private ActivityCheckoutBinding binding;
    private ConfigManager config;
    private double subtotal = 350.0;
    private double deliveryFee = 100.0;
    private String selectedPaymentMethod = "mpesa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        config = ConfigManager.getInstance(this);
        
        // Initialize Paystack
        initializePaystack();
        
        // Get data from intent
        if (getIntent().hasExtra("total")) {
            subtotal = getIntent().getDoubleExtra("total", 350.0);
        }
        
        updateTotals();
        setupListeners();
    }
    
    private void initializePaystack() {
        String publicKey = config.getPaystackPublicKey();
        if (!publicKey.isEmpty() && !publicKey.contains("YOUR_PAYSTACK")) {
            PaystackSdk.setPublicKey(publicKey);
        }
    }
    
    private void updateTotals() {
        binding.tvSubtotal.setText(String.format("KES %.0f", subtotal));
        binding.tvDeliveryFeeCheckout.setText(String.format("KES %.0f", deliveryFee));
        
        double total = subtotal + deliveryFee;
        binding.tvTotalCheckout.setText(String.format("KES %.0f", total));
        binding.btnPay.setText(String.format("Pay KES %.0f", total));
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        // Delivery priority selection
        binding.rgDeliveryPriority.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbStandard) {
                deliveryFee = config.getStandardDeliveryFee();
            } else if (checkedId == R.id.rbUrgent) {
                deliveryFee = config.getUrgentDeliveryFee();
            }
            updateTotals();
        });
        
        // Payment method selection
        binding.rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPaystack) {
                selectedPaymentMethod = "paystack";
            } else if (checkedId == R.id.rbMpesa) {
                selectedPaymentMethod = "mpesa";
            }
        });
        
        // Pay button
        binding.btnPay.setOnClickListener(v -> processPayment());
    }
    
    private void processPayment() {
        String buildingDetails = binding.etBuildingDetails.getText().toString().trim();
        
        if (buildingDetails.isEmpty()) {
            Toast.makeText(this, "Please enter building details", Toast.LENGTH_SHORT).show();
            return;
        }
        
        double total = subtotal + deliveryFee;
        
        if (selectedPaymentMethod.equals("paystack")) {
            initiatePaystackPayment(total);
        } else {
            initiateMpesaPayment(total);
        }
    }
    
    private void initiatePaystackPayment(double amount) {
        // TODO: Implement Paystack payment flow
        // This requires card details collection and charge processing
        Toast.makeText(this, "Paystack payment integration - Coming soon", Toast.LENGTH_SHORT).show();
    }
    
    private void initiateMpesaPayment(double amount) {
        // TODO: Implement M-Pesa payment flow
        Toast.makeText(this, "M-Pesa payment integration - Coming soon", Toast.LENGTH_SHORT).show();
        
        // For now, simulate successful payment
        simulateSuccessfulPayment();
    }
    
    private void simulateSuccessfulPayment() {
        Toast.makeText(this, "Payment successful! Order placed.", Toast.LENGTH_LONG).show();
        
        // Navigate to track order
        // Intent intent = new Intent(CheckoutActivity.this, TrackOrderActivity.class);
        // startActivity(intent);
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
