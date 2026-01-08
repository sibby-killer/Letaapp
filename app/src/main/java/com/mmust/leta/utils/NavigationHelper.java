package com.mmust.leta.utils;

import android.content.Context;
import android.content.Intent;

import com.mmust.leta.ActiveDeliveryActivity;
import com.mmust.leta.CartActivity;
import com.mmust.leta.CheckoutActivity;
import com.mmust.leta.GrokAIChatActivity;
import com.mmust.leta.MenuManagerActivity;
import com.mmust.leta.OrderProcessingActivity;
import com.mmust.leta.ReferralHubActivity;
import com.mmust.leta.RiderHomeActivity;
import com.mmust.leta.RiderRecruitmentActivity;
import com.mmust.leta.StudentHomeActivity;
import com.mmust.leta.TrackOrderActivity;
import com.mmust.leta.VendorDashboardActivity;
import com.mmust.leta.VendorProfileActivity;

/**
 * Navigation Helper - Centralized navigation management
 * Makes it easy to navigate between screens throughout the app
 */
public class NavigationHelper {
    
    // Student Navigation
    public static void goToStudentHome(Context context) {
        Intent intent = new Intent(context, StudentHomeActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToCart(Context context) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToCheckout(Context context, double total) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra("total", total);
        context.startActivity(intent);
    }
    
    public static void goToTrackOrder(Context context, String orderId) {
        Intent intent = new Intent(context, TrackOrderActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }
    
    public static void goToReferralHub(Context context) {
        Intent intent = new Intent(context, ReferralHubActivity.class);
        context.startActivity(intent);
    }
    
    // Vendor Navigation
    public static void goToVendorDashboard(Context context) {
        Intent intent = new Intent(context, VendorDashboardActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToVendorProfile(Context context) {
        Intent intent = new Intent(context, VendorProfileActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToMenuManager(Context context) {
        Intent intent = new Intent(context, MenuManagerActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToOrderProcessing(Context context, String orderId) {
        Intent intent = new Intent(context, OrderProcessingActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }
    
    // Rider Navigation
    public static void goToRiderHome(Context context) {
        Intent intent = new Intent(context, RiderHomeActivity.class);
        context.startActivity(intent);
    }
    
    public static void goToActiveDelivery(Context context, String deliveryId) {
        Intent intent = new Intent(context, ActiveDeliveryActivity.class);
        intent.putExtra("deliveryId", deliveryId);
        context.startActivity(intent);
    }
    
    public static void goToRiderRecruitment(Context context) {
        Intent intent = new Intent(context, RiderRecruitmentActivity.class);
        context.startActivity(intent);
    }
    
    // Common Navigation
    public static void goToGrokAIChat(Context context) {
        Intent intent = new Intent(context, GrokAIChatActivity.class);
        context.startActivity(intent);
    }
}
