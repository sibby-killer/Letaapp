package com.mmust.leta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mmust.leta.databinding.ActivityTrackOrderBinding;
import com.mmust.leta.utils.ConfigManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class TrackOrderActivity extends AppCompatActivity {
    
    private ActivityTrackOrderBinding binding;
    private MyLocationNewOverlay myLocationOverlay;
    private ConfigManager config;
    private static final int LOCATION_PERMISSION_REQUEST = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        
        binding = ActivityTrackOrderBinding.inflate(getLayoutInflater());
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
        
        // Set initial position to MMUST
        GeoPoint startPoint = new GeoPoint(config.getMmustLatitude(), config.getMmustLongitude());
        binding.mapView.getController().setCenter(startPoint);
        binding.mapView.getController().setZoom(config.getDefaultZoom());
        
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), binding.mapView);
        myLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(myLocationOverlay);
    }
    
    private void setupUI() {
        // Set sample data
        binding.tvOrderStatus.setText("ON THE WAY");
        binding.tvRiderName.setText("John Kamau");
        binding.tvRiderRating.setText("4.8");
        binding.tvEta.setText("12 min");
        binding.tvOrderNumber.setText("Order #1234");
    }
    
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnCallRider.setOnClickListener(v -> {
            // TODO: Implement call rider functionality
            // Intent intent = new Intent(Intent.ACTION_DIAL);
            // intent.setData(Uri.parse("tel:" + riderPhoneNumber));
            // startActivity(intent);
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
