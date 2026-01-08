package com.mmust.leta;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.mmust.leta.utils.ConfigManager;
import com.mmust.leta.utils.ErrorHandler;
import com.mmust.leta.utils.ValidationHelper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmust.leta.databinding.ActivityAuthBinding;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    
    private ActivityAuthBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    private ConfigManager config;
    private static final int RC_SIGN_IN = 9001;
    private boolean isSignUpMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        config = ConfigManager.getInstance(this);
        
        // Configure Google Sign In
        String webClientId = getString(R.string.default_web_client_id);
        if (!webClientId.contains("YOUR_")) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(webClientId)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        } else {
            // Hide Google Sign-In button if not configured
            binding.btnGoogleSignIn.setVisibility(View.GONE);
        }
        
        setupListeners();
        setupRealTimeValidation();
    }
    
    private void setupListeners() {
        // Login Button
        binding.btnLogin.setOnClickListener(v -> handleAuthAction());
        
        // Google Sign In Button
        if (mGoogleSignInClient != null) {
            binding.btnGoogleSignIn.setOnClickListener(v -> signInWithGoogle());
        }
        
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
        
        // Perform login
        signInWithEmail(email, password);
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
        
        // Perform signup
        signUpWithEmail(email, password);
    }
    
    private void signInWithEmail(String email, String password) {
        // Show loading
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Logging in...");
        
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText(R.string.log_in);
                    
                    if (task.isSuccessful()) {
                        // Sign in success
                        ErrorHandler.showSuccess(this, "Login successful!");
                        String uid = mAuth.getCurrentUser().getUid();
                        checkUserRoleAndNavigate(uid);
                    } else {
                        // Sign in failed - show user-friendly error
                        ErrorHandler.handleFirebaseAuthError(AuthActivity.this, task.getException());
                    }
                });
    }
    
    private void signUpWithEmail(String email, String password) {
        // Show loading
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Creating account...");
        
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    binding.btnLogin.setEnabled(true);
                    binding.btnLogin.setText(R.string.sign_up);
                    
                    if (task.isSuccessful()) {
                        // Sign up success
                        ErrorHandler.showSuccess(this, "Account created successfully!");
                        
                        // Navigate to role selection
                        Intent intent = new Intent(AuthActivity.this, SelectRoleActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign up failed - show user-friendly error
                        ErrorHandler.handleFirebaseAuthError(AuthActivity.this, task.getException());
                    }
                });
    }
    
    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> {
                    ErrorHandler.showSuccess(this, "Password reset email sent! Check your inbox.");
                })
                .addOnFailureListener(e -> {
                    ErrorHandler.handleFirebaseAuthError(this, e);
                });
    }
    
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        checkUserRoleAndNavigate(uid);
                    } else {
                        Toast.makeText(AuthActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    private void checkUserRoleAndNavigate(String uid) {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("role")) {
                        // User has a role, navigate to appropriate dashboard
                        UserRouter.routeUser(AuthActivity.this, uid);
                    } else {
                        // New user, needs to select role
                        Intent intent = new Intent(AuthActivity.this, SelectRoleActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AuthActivity.this, "Error checking user role: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
