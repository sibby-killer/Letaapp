package com.mmust.leta;

import android.content.Context;
import android.content.Intent;

import com.mmust.leta.utils.ErrorHandler;
import com.mmust.leta.utils.SupabaseClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRouter {
    
    /**
     * Routes the user to the appropriate dashboard based on their role in Supabase
     * @param context Current activity context
     * @param userId User's Supabase UID
     */
    public static void routeUser(Context context, String userId) {
        SupabaseClient supabase = SupabaseClient.getInstance(context);
        String accessToken = supabase.getAccessToken();
        
        if (accessToken == null) {
            // No access token, redirect to auth
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).finish();
            }
            return;
        }
        
        supabase.getUserData(userId, accessToken, new SupabaseClient.DataCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONArray users = new JSONArray(data);
                    
                    if (users.length() > 0) {
                        JSONObject user = users.getJSONObject(0);
                        String role = user.optString("role", "");
                        
                        if (context instanceof android.app.Activity) {
                            ((android.app.Activity) context).runOnUiThread(() -> {
                                navigateByRole(context, role);
                            });
                        }
                    } else {
                        // User doesn't have a role yet, send to role selection
                        if (context instanceof android.app.Activity) {
                            ((android.app.Activity) context).runOnUiThread(() -> {
                                Intent intent = new Intent(context, SelectRoleActivity.class);
                                context.startActivity(intent);
                                
                                if (context instanceof android.app.Activity) {
                                    ((android.app.Activity) context).finish();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    // Error parsing response, go to role selection
                    if (context instanceof android.app.Activity) {
                        ((android.app.Activity) context).runOnUiThread(() -> {
                            Intent intent = new Intent(context, SelectRoleActivity.class);
                            context.startActivity(intent);
                            
                            if (context instanceof android.app.Activity) {
                                ((android.app.Activity) context).finish();
                            }
                        });
                    }
                }
            }
            
            @Override
            public void onError(String error) {
                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).runOnUiThread(() -> {
                        ErrorHandler.showError(context, "Error loading user data: " + error);
                        
                        // Fallback to role selection
                        Intent intent = new Intent(context, SelectRoleActivity.class);
                        context.startActivity(intent);
                        
                        if (context instanceof android.app.Activity) {
                            ((android.app.Activity) context).finish();
                        }
                    });
                }
            }
        });
    }
    
    private static void navigateByRole(Context context, String role) {
        Intent intent;
        
        switch (role) {
            case "student":
                intent = new Intent(context, StudentHomeActivity.class);
                break;
            case "vendor":
                intent = new Intent(context, VendorDashboardActivity.class);
                break;
            case "rider":
                intent = new Intent(context, RiderHomeActivity.class);
                break;
            default:
                // Default to student if role is unknown
                intent = new Intent(context, StudentHomeActivity.class);
        }
        
        context.startActivity(intent);
        
        // Finish the calling activity if it's an Activity
        if (context instanceof android.app.Activity) {
            ((android.app.Activity) context).finish();
        }
    }
}
