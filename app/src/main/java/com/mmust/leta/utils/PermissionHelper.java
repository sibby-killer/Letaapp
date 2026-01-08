package com.mmust.leta.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Permission Helper - Centralized permission management
 */
public class PermissionHelper {
    
    public static final int LOCATION_PERMISSION_REQUEST = 1001;
    public static final int STORAGE_PERMISSION_REQUEST = 1002;
    public static final int CAMERA_PERMISSION_REQUEST = 1003;
    
    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
    
    public static boolean hasStoragePermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
    
    public static boolean hasCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
    
    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
    }
    
    public static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_REQUEST);
    }
    
    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST);
    }
    
    public static boolean shouldShowLocationRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
