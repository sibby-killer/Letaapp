package com.mmust.leta;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mmust.leta.databinding.ActivityOrderProcessingBinding;

public class OrderProcessingActivity extends AppCompatActivity {
    
    private ActivityOrderProcessingBinding binding;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int seconds = 0;
    private int minutes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderProcessingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupUI();
        setupRecyclerView();
        setupListeners();
        startTimer();
    }
    
    private void setupUI() {
        binding.tvOrderNumber.setText("Order #2291");
        binding.tvCustomerName.setText("Customer: John Doe");
        binding.tvStatus.setText("PREPARING...");
        binding.tvCustomerNote.setText("Note from customer: Please include extra napkins.");
    }
    
    private void setupRecyclerView() {
        binding.rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set adapter with order items
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnHelp.setOnClickListener(v -> {
            // TODO: Show help dialog
            Toast.makeText(this, "Help & Support", Toast.LENGTH_SHORT).show();
        });
        
        binding.btnMarkReady.setOnClickListener(v -> markOrderReady());
    }
    
    private void startTimer() {
        timerHandler = new Handler(Looper.getMainLooper());
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                
                binding.tvMinutes.setText(String.format("%02d", minutes));
                binding.tvSeconds.setText(String.format("%02d", seconds));
                
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }
    
    private void markOrderReady() {
        // TODO: Update order status in Firestore
        // TODO: Notify AI dispatch system to assign rider
        Toast.makeText(this, "Order marked as ready! Finding rider...", Toast.LENGTH_LONG).show();
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        binding = null;
    }
}
