package com.mmust.leta;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityReferralHubBinding;

public class ReferralHubActivity extends AppCompatActivity {
    
    private ActivityReferralHubBinding binding;
    private String referralCode = "MMUST2024";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferralHubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupUI();
        setupListeners();
    }
    
    private void setupUI() {
        binding.tvReferralCode.setText(referralCode);
        binding.tvTotalReferrals.setText("12");
        binding.tvRewardsEarned.setText("KES 1,200");
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnCopyCode.setOnClickListener(v -> copyCodeToClipboard());
        
        binding.btnShareCode.setOnClickListener(v -> shareReferralCode());
    }
    
    private void copyCodeToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Referral Code", referralCode);
        clipboard.setPrimaryClip(clip);
        
        Toast.makeText(this, "Referral code copied!", Toast.LENGTH_SHORT).show();
    }
    
    private void shareReferralCode() {
        String shareText = "Join me on Leta App and get 10% off your first order! Use my referral code: " 
                + referralCode + "\n\nDownload now: https://letaapp.com";
        
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        
        startActivity(Intent.createChooser(shareIntent, "Share Referral Code"));
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
