package com.mmust.leta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mmust.leta.databinding.ActivityStudentHomeBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class StudentHomeActivity extends AppCompatActivity {
    
    private ActivityStudentHomeBinding binding;
    private MyLocationNewOverlay myLocationOverlay;
    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    
    // MMUST Coordinates
    private static final double MMUST_LATITUDE = 0.2827;
    private static final double MMUST_LONGITUDE = 34.7519;
    private static final double DEFAULT_ZOOM = 16.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize OSMDroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        
        binding = ActivityStudentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupMapView();
        setupListeners();
        checkLocationPermission();
    }
    
    private void setupMapView() {
        // Configure map
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.setBuiltInZoomControls(false);
        
        // Set initial position to MMUST
        GeoPoint startPoint = new GeoPoint(MMUST_LATITUDE, MMUST_LONGITUDE);
        binding.mapView.getController().setCenter(startPoint);
        binding.mapView.getController().setZoom(DEFAULT_ZOOM);
        
        // Add location overlay
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), binding.mapView);
        myLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(myLocationOverlay);
    }
    
    private void setupListeners() {
        // My Location FAB
        binding.fabMyLocation.setOnClickListener(v -> {
            if (myLocationOverlay.getMyLocation() != null) {
                binding.mapView.getController().animateTo(myLocationOverlay.getMyLocation());
            } else {
                // If location not available, center on MMUST
                GeoPoint mmustPoint = new GeoPoint(MMUST_LATITUDE, MMUST_LONGITUDE);
                binding.mapView.getController().animateTo(mmustPoint);
            }
        });
        
        // Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_orders) {
                // TODO: Navigate to orders
                return true;
            } else if (itemId == R.id.nav_cart) {
                // TODO: Navigate to cart
                return true;
            } else if (itemId == R.id.nav_profile) {
                // TODO: Navigate to profile
                return true;
            }
            
            return false;
        });
    }
    
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocationOverlay.enableMyLocation();
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
