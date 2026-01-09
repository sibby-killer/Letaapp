package com.mmust.leta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mmust.leta.databinding.ActivityAuthBinding;
import com.mmust.leta.utils.ConfigManager;
import com.mmust.leta.utils.ErrorHandler;
import com.mmust.leta.utils.SupabaseClient;
import com.mmust.leta.utils.ValidationHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity {
    
    private ActivityAuthBinding binding;
    private SupabaseClient supabase;
    private ConfigManager config;
    private SharedPreferences prefs;
    private boolean isSignUpMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize
        supabase = SupabaseClient.getInstance(this);
        config = ConfigManager.getInstance(this);
        prefs = getSharedPreferences("LetaApp", MODE_PRIVATE);
        
        // Check if already logged in
        checkExistingSession();
        
        setupListeners();
        setupRealTimeValidation();
    }
    
    private void checkExistingSession() {
        if (supabase.isLoggedIn()) {
            // User is logged in, route to dashboard
            UserRouter.routeUser(this, supabase.getCurrentUserId());
        }
    }
    
    private void setupListeners() {
        // Main action button
        binding.btnLogin.setOnClickListener(v -> handleAuthAction());
        
        // Forgot Password
        binding.tvForgotPassword.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            if (ValidationHelper.isValidEmail(email)) {
                resetPassword(email);
            } else {
                binding.tilEmail.setError("Enter your email to reset password");
            }
        });
        
        // Tab Layout - Switch between Login and Sign Up
        binding.tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    // Sign Up tab selected
                    isSignUpMode = true;
                    binding.btnLogin.setText(R.string.sign_up);
                    binding.tilConfirmPassword.setVisibility(View.VISIBLE);
                    binding.tvForgotPassword.setVisibility(View.GONE);
                    clearErrors();
                } else {
                    // Login tab selected
                    isSignUpMode = false;
                    binding.btnLogin.setText(R.string.log_in);
                    binding.tilConfirmPassword.setVisibility(View.GONE);
                    binding.tvForgotPassword.setVisibility(View.VISIBLE);
                    clearErrors();
                }
            }
            
            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
            
            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
        });
    }
    
    private void setupRealTimeValidation() {
        // Email validation
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (!email.isEmpty()) {
                    String error = ValidationHelper.getEmailError(email);
                    binding.tilEmail.setError(error);
                }
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        // Password validation
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if (!password.isEmpty() && isSignUpMode) {
                    String error = ValidationHelper.getPasswordError(password);
                    binding.tilPassword.setError(error);
                }
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        // Confirm password validation
        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.etPassword.getText().toString();
                String confirmPassword = s.toString();
                
                if (!confirmPassword.isEmpty()) {
                    if (!password.equals(confirmPassword)) {
                        binding.tilConfirmPassword.setError("Passwords do not match");
                    } else {
                        binding.tilConfirmPassword.setError(null);
                    }
                }
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void clearErrors() {
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);
    }
    
    private void handleAuthAction() {
        if (isSignUpMode) {
            performSignUp();
        } else {
            performLogin();
        }
    }
    
    private void performLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        
        // Clear previous errors
        clearErrors();
        
        // Validate email
        String emailError = ValidationHelper.getEmailError(email);
        if (emailError != null) {
            binding.tilEmail.setError(emailError);
            binding.etEmail.requestFocus();
            return;
        }
        
        // Validate password
        String passwordError = ValidationHelper.getPasswordError(password);
        if (passwordError != null) {
            binding.tilPassword.setError(passwordError);
            binding.etPassword.requestFocus();
            return;
        }
        
        // Show loading
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Logging in...");
        
        // Sign in with Supabase
        supabase.signIn(email, password, new SupabaseClient.SupabaseCallback() {
            @Override
            public void onSuccess(String userId, String accessToken) {
                runOnUiThread(() -> {
                    // Save session in SupabaseClient
                    supabase.saveSession(userId, accessToken);
                    
                    ErrorHandler.showSuccess(AuthActivity.this, "Login successful!");
                    
                    // Route to dashboard
                    UserRouter.routeUser(AuthActivity.this, userId);
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText(R.string.log_in);
                    ErrorHandler.showError(AuthActivity.this, error);
                });
            }
        });
    }
    
    private void performSignUp() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();
        
        // Clear previous errors
        clearErrors();
        
        // Validate email
        String emailError = ValidationHelper.getEmailError(email);
        if (emailError != null) {
            binding.tilEmail.setError(emailError);
            binding.etEmail.requestFocus();
            return;
        }
        
        // Validate password
        String passwordError = ValidationHelper.getPasswordError(password);
        if (passwordError != null) {
            binding.tilPassword.setError(passwordError);
            binding.etPassword.requestFocus();
            return;
        }
        
        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.setError("Please confirm your password");
            binding.etConfirmPassword.requestFocus();
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            binding.tilConfirmPassword.setError("Passwords do not match");
            binding.etConfirmPassword.requestFocus();
            return;
        }
        
        // Show loading
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Creating account...");
        
        // Sign up with Supabase
        supabase.signUp(email, password, new SupabaseClient.SupabaseCallback() {
            @Override
            public void onSuccess(String userId, String data) {
                runOnUiThread(() -> {
                    // Parse response to get access token
                    try {
                        JSONObject jsonResponse = new JSONObject(data);
                        String accessToken = jsonResponse.optString("access_token", "");
                        
                        // Save session in SupabaseClient
                        supabase.saveSession(userId, accessToken);
                        
                        ErrorHandler.showSuccess(AuthActivity.this, "Account created successfully!");
                        
                        // Navigate to role selection
                        Intent intent = new Intent(AuthActivity.this, SelectRoleActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        ErrorHandler.showError(AuthActivity.this, "Error parsing response");
                    }
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText(R.string.sign_up);
                    ErrorHandler.showError(AuthActivity.this, error);
                });
            }
        });
    }
    
    private void resetPassword(String email) {
        supabase.resetPassword(email, new SupabaseClient.SupabaseCallback() {
            @Override
            public void onSuccess(String userId, String data) {
                runOnUiThread(() -> {
                    ErrorHandler.showSuccess(AuthActivity.this, "Password reset email sent! Check your inbox.");
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    ErrorHandler.showError(AuthActivity.this, error);
                });
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
