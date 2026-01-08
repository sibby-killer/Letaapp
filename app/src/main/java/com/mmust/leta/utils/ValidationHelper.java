package com.mmust.leta.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Input Validation Helper
 */
public class ValidationHelper {
    
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
    
    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && phone.length() >= 10;
    }
    
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }
    
    public static String getEmailError(String email) {
        if (TextUtils.isEmpty(email)) {
            return "Email is required";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid email format";
        }
        return null;
    }
    
    public static String getPasswordError(String password) {
        if (TextUtils.isEmpty(password)) {
            return "Password is required";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }
        return null;
    }
    
    public static String getPhoneError(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "Phone number is required";
        }
        if (phone.length() < 10) {
            return "Invalid phone number";
        }
        return null;
    }
}
