package com.mmust.leta;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mmust.leta.databinding.ActivityMenuManagerBinding;

public class MenuManagerActivity extends AppCompatActivity {
    
    private ActivityMenuManagerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupRecyclerView();
        setupListeners();
    }
    
    private void setupRecyclerView() {
        binding.rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set adapter with menu items
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnAdd.setOnClickListener(v -> showAddItemDialog());
        
        binding.fabAddItem.setOnClickListener(v -> showAddItemDialog());
        
        // Category filters
        binding.chipAll.setOnClickListener(v -> filterItems("all"));
        binding.chipSnacks.setOnClickListener(v -> filterItems("snacks"));
        binding.chipDrinks.setOnClickListener(v -> filterItems("drinks"));
        binding.chipMeals.setOnClickListener(v -> filterItems("meals"));
    }
    
    private void showAddItemDialog() {
        // TODO: Show dialog to add new menu item
        Toast.makeText(this, "Add menu item - Dialog coming soon", Toast.LENGTH_SHORT).show();
    }
    
    private void filterItems(String category) {
        // TODO: Filter menu items by category
        Toast.makeText(this, "Filtering: " + category, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
