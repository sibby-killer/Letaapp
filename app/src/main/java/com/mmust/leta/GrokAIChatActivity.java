package com.mmust.leta;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mmust.leta.databinding.ActivityGrokAiChatBinding;

public class GrokAIChatActivity extends AppCompatActivity {
    
    private ActivityGrokAiChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGrokAiChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupRecyclerView();
        setupListeners();
    }
    
    private void setupRecyclerView() {
        binding.rvChatMessages.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set adapter with chat messages
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnMore.setOnClickListener(v -> {
            // TODO: Show more options
            Toast.makeText(this, "More options", Toast.LENGTH_SHORT).show();
        });
        
        binding.fabSend.setOnClickListener(v -> sendMessage());
        
        // Suggested action chips
        binding.chipRouteOptimization.setOnClickListener(v -> 
            sendPredefinedMessage("Show me route optimization tips"));
        
        binding.chipEarningsInsight.setOnClickListener(v -> 
            sendPredefinedMessage("What are my earnings insights?"));
        
        binding.chipHotspots.setOnClickListener(v -> 
            sendPredefinedMessage("Where are the current hotspots?"));
    }
    
    private void sendMessage() {
        String message = binding.etMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            // TODO: Send message to AI backend
            binding.etMessage.setText("");
            Toast.makeText(this, "Message sent: " + message, Toast.LENGTH_SHORT).show();
        }
    }
    
    private void sendPredefinedMessage(String message) {
        binding.etMessage.setText(message);
        sendMessage();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
