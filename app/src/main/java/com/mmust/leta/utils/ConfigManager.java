package com.mmust.leta.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager - Loads API keys and settings from config.properties
 * NO HARDCODED API KEYS - All configuration loaded from resources
 */
public class ConfigManager {
    
    private static ConfigManager instance;
    private Properties properties;
    
    private ConfigManager(Context context) {
        properties = new Properties();
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    context.getResources().getIdentifier("config", "raw", context.getPackageName()));
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized ConfigManager getInstance(Context context) {
        if (instance == null) {
            instance = new ConfigManager(context.getApplicationContext());
        }
        return instance;
    }
    
    // Firebase Configuration
    public String getFirebaseApiKey() {
        return properties.getProperty("firebase_api_key", "");
    }
    
    // Paystack Configuration
    public String getPaystackPublicKey() {
        return properties.getProperty("paystack_public_key", "");
    }
    
    public String getPaystackSecretKey() {
        return properties.getProperty("paystack_secret_key", "");
    }
    
    // OSM Configuration
    public String getOsmUserAgent() {
        return properties.getProperty("osm_user_agent", "LetaApp/1.0");
    }
    
    // API Configuration
    public String getApiBaseUrl() {
        return properties.getProperty("api_base_url", "https://api.letaapp.com/v1");
    }
    
    public int getApiTimeout() {
        return Integer.parseInt(properties.getProperty("api_timeout", "30"));
    }
    
    // MMUST Campus Coordinates
    public double getMmustLatitude() {
        return Double.parseDouble(properties.getProperty("mmust_latitude", "0.2827"));
    }
    
    public double getMmustLongitude() {
        return Double.parseDouble(properties.getProperty("mmust_longitude", "34.7519"));
    }
    
    public double getDefaultZoom() {
        return Double.parseDouble(properties.getProperty("default_zoom", "16.0"));
    }
    
    // Delivery Settings
    public int getStandardDeliveryFee() {
        return Integer.parseInt(properties.getProperty("standard_delivery_fee", "50"));
    }
    
    public int getUrgentDeliveryFee() {
        return Integer.parseInt(properties.getProperty("urgent_delivery_fee", "100"));
    }
    
    public int getRiderCommissionPercent() {
        return Integer.parseInt(properties.getProperty("rider_commission_percent", "20"));
    }
    
    public int getVendorCommissionPercent() {
        return Integer.parseInt(properties.getProperty("vendor_commission_percent", "5"));
    }
    
    // App Settings
    public boolean isGoogleSignInEnabled() {
        return Boolean.parseBoolean(properties.getProperty("enable_google_signin", "true"));
    }
    
    public boolean isReferralSystemEnabled() {
        return Boolean.parseBoolean(properties.getProperty("enable_referral_system", "true"));
    }
    
    public int getReferralDiscountPercent() {
        return Integer.parseInt(properties.getProperty("referral_discount_percent", "10"));
    }
}
