package com.mmust.leta;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mmust.leta.utils.ErrorHandler;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserRouter {
    
    /**
     * Routes the user to the appropriate dashboard based on their role in Firestore
     * @param context Current activity context
     * @param uid User's Firebase UID
     */
    public static void routeUser(Context context, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("role")) {
                        String role = documentSnapshot.getString("role");
                        
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
                    } else {
                        // User doesn't have a role, send to role selection
                        Intent intent = new Intent(context, SelectRoleActivity.class);
                        context.startActivity(intent);
                        
                        if (context instanceof android.app.Activity) {
                            ((android.app.Activity) context).finish();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    ErrorHandler.handleFirestoreError(context, e);
                    
                    // Fallback to auth screen
                    Intent intent = new Intent(context, AuthActivity.class);
                    context.startActivity(intent);
                    
                    if (context instanceof android.app.Activity) {
                        ((android.app.Activity) context).finish();
                    }
                });
    }
}
