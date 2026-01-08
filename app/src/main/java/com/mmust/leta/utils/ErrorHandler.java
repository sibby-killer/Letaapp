package com.mmust.leta.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthException;

/**
 * Centralized Error Handler
 */
public class ErrorHandler {
    
    private static final String TAG = "ErrorHandler";
    
    public static void handleFirebaseAuthError(Context context, Exception e) {
        String message = "Authentication failed";
        
        if (e instanceof FirebaseAuthException) {
            FirebaseAuthException authException = (FirebaseAuthException) e;
            String errorCode = authException.getErrorCode();
            
            switch (errorCode) {
                case "ERROR_INVALID_EMAIL":
                    message = "Invalid email address";
                    break;
                case "ERROR_WRONG_PASSWORD":
                    message = "Incorrect password";
                    break;
                case "ERROR_USER_NOT_FOUND":
                    message = "No account found with this email";
                    break;
                case "ERROR_USER_DISABLED":
                    message = "This account has been disabled";
                    break;
                case "ERROR_TOO_MANY_REQUESTS":
                    message = "Too many attempts. Please try again later";
                    break;
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    message = "Email already registered";
                    break;
                case "ERROR_WEAK_PASSWORD":
                    message = "Password is too weak";
                    break;
                default:
                    message = "Authentication error: " + e.getMessage();
            }
        } else if (e instanceof FirebaseNetworkException) {
            message = "Network error. Check your connection";
        }
        
        Log.e(TAG, "Auth error: " + message, e);
        showError(context, message);
    }
    
    public static void handleFirestoreError(Context context, Exception e) {
        String message = "Database error";
        
        if (e instanceof FirebaseNetworkException) {
            message = "Network error. Check your connection";
        } else if (e.getMessage() != null) {
            if (e.getMessage().contains("permission")) {
                message = "Permission denied. Please contact support";
            } else {
                message = "Error: " + e.getMessage();
            }
        }
        
        Log.e(TAG, "Firestore error: " + message, e);
        showError(context, message);
    }
    
    public static void handlePaymentError(Context context, Exception e) {
        String message = "Payment failed";
        
        if (e.getMessage() != null) {
            if (e.getMessage().contains("network")) {
                message = "Network error. Check your connection";
            } else if (e.getMessage().contains("card")) {
                message = "Card payment failed. Check your details";
            } else {
                message = "Payment error: " + e.getMessage();
            }
        }
        
        Log.e(TAG, "Payment error: " + message, e);
        showError(context, message);
    }
    
    public static void handleLocationError(Context context) {
        showError(context, "Location permission required. Please enable in settings");
    }
    
    public static void showError(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
    
    public static void showSuccess(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void logError(String tag, String message, Exception e) {
        Log.e(tag, message, e);
    }
}
