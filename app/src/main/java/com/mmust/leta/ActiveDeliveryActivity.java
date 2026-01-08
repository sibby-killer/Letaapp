package com.mmust.leta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mmust.leta.databinding.ActivityActiveDeliveryBinding;
import com.mmust.leta.utils.ConfigManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class ActiveDeliveryActivity extends AppCompatActivity {
    
    private ActivityActiveDeliveryBinding binding;
    private MyLocationNewOverlay myLocationOverlay;
    private ConfigManager config;
    private static final int LOCATION_PERMISSION_REQUEST = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        
        binding = ActivityActiveDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        config = ConfigManager.getInstance(this);
        
        setupMapView();
        setupUI();
        setupListeners();
        checkLocationPermission();
    }
    
    private void setupMapView() {
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.setBuiltInZoomControls(false);
        
        GeoPoint startPoint = new GeoPoint(config.getMmustLatitude(), config.getMmustLongitude());
        binding.mapView.getController().setCenter(startPoint);
        binding.mapView.getController().setZoom(config.getDefaultZoom());
        
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), binding.mapView);
        myLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(myLocationOverlay);
    }
    
    private void setupUI() {
        binding.tvDeliveryStatus.setText("PICKING UP");
        binding.tvOrderNumber.setText("#1234");
        binding.tvEarning.setText("KES 100");
        binding.tvPickupLocation.setText("Kwa Mathe, MMUST");
        binding.tvDropoffLocation.setText("Hall 4, Room 202");
        binding.tvEta.setText("8 min");
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnCallCustomer.setOnClickListener(v -> {
            // TODO: Implement call customer
            Toast.makeText(this, "Calling customer...", Toast.LENGTH_SHORT).show();
        });
        
        binding.btnCompleteDelivery.setOnClickListener(v -> completeDelivery());
    }
    
    private void completeDelivery() {
        // TODO: Update delivery status in Firestore
        // TODO: Trigger split payment via Paystack
        Toast.makeText(this, "Delivery completed! Payment processed.", Toast.LENGTH_LONG).show();
        finish();
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
