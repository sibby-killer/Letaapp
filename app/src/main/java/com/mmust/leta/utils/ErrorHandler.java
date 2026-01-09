package com.mmust.leta.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Centralized Error Handler - Updated for Supabase
 */
public class ErrorHandler {
    
    private static final String TAG = "ErrorHandler";
    
    public static void handleAuthError(Context context, Exception e) {
        String message = "Authentication failed";
        
        if (e.getMessage() != null) {
            String errorMsg = e.getMessage().toLowerCase();
            
            if (errorMsg.contains("invalid email") || errorMsg.contains("email")) {
                message = "Invalid email address";
            } else if (errorMsg.contains("password") || errorMsg.contains("credentials")) {
                message = "Incorrect password";
            } else if (errorMsg.contains("user not found") || errorMsg.contains("not found")) {
                message = "No account found with this email";
            } else if (errorMsg.contains("disabled")) {
                message = "This account has been disabled";
            } else if (errorMsg.contains("too many") || errorMsg.contains("rate limit")) {
                message = "Too many attempts. Please try again later";
            } else if (errorMsg.contains("already exists") || errorMsg.contains("already in use")) {
                message = "Email already registered";
            } else if (errorMsg.contains("weak password") || errorMsg.contains("password")) {
                message = "Password is too weak";
            } else {
                message = "Authentication error: " + e.getMessage();
            }
        }
        
        if (isNetworkError(e)) {
            message = "Network error. Check your connection";
        }
        
        Log.e(TAG, "Auth error: " + message, e);
        showError(context, message);
    }
    
    public static void handleDatabaseError(Context context, Exception e) {
        String message = "Database error";
        
        if (isNetworkError(e)) {
            message = "Network error. Check your connection";
        } else if (e.getMessage() != null) {
            if (e.getMessage().contains("permission") || e.getMessage().contains("unauthorized")) {
                message = "Permission denied. Please contact support";
            } else {
                message = "Error: " + e.getMessage();
            }
        }
        
        Log.e(TAG, "Database error: " + message, e);
        showError(context, message);
    }
    
    private static boolean isNetworkError(Exception e) {
        return e instanceof IOException || 
               e instanceof SocketTimeoutException || 
               e instanceof UnknownHostException ||
               (e.getMessage() != null && (e.getMessage().contains("network") || 
                                          e.getMessage().contains("connection") ||
                                          e.getMessage().contains("timeout")));
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
