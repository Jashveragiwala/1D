package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// FinalPathActivity -> Page for each Day in Final Optimized path
public class FinalPathActivity extends AppCompatActivity {
    TextView txtView;
    TextView path;
    String Location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_final_path);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        // log the number of days
        Log.d("BING", numberOfDays);

        txtView = (TextView) findViewById(R.id.name6);
        path = (TextView) findViewById(R.id.pathout);

        ArrayList<ArrayList<String>> allPaths = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("COMPLETE_PATH");
        int index = getIntent().getIntExtra("INDEX", 0);

        Log.d("BING", allPaths.toString());

        String finalOutput = "";

        ArrayList<String> currentPath = allPaths.get(index);
        ArrayList<LatLng> locations = new ArrayList<>();

        for (int i=0; i < currentPath.size(); i++) {
            if (i == currentPath.size() - 1)
                finalOutput += currentPath.get(i).toUpperCase() + " -> " + currentPath.get(0).toUpperCase();
            else
                finalOutput += currentPath.get(i).toUpperCase() + " -> ";

            String locationName = currentPath.get(i);
            System.out.println(locationName);
            Geocoder geocoder = new Geocoder(FinalPathActivity.this);
            try {
                List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                    locations.add(location);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR No Location Found");
            }

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Map is ready to use
                // Set the map type to be normal (default is hybrid)
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                // Move the camera to the first location in the list and zoom to a suitable level
                LatLng firstLocation = locations.get(0);
                float zoomLevel = 12f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, zoomLevel));

                // Add markers for all locations
                for (int i = 0; i < locations.size(); i++) {
                    LatLng location = locations.get(i);
                    Geocoder geocoder = new Geocoder(FinalPathActivity.this);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(location)
                                    .title(address.getLocality())
                                    .snippet(address.getAdminArea());
                            googleMap.addMarker(markerOptions);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("ERROR No Location Found");
                    }
                    if (i > 0) {
                        LatLng previousLocation = locations.get(i - 1);
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .add(previousLocation)
                                .add(location)
                                .width(5)
                                .color(Color.RED);
                        googleMap.addPolyline(polylineOptions);
                    }
                }
            }
        });
        int dayNo = index + 1;
        txtView.setText("Day " + dayNo);
        path.setText(finalOutput);

        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(FinalPathActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent(FinalPathActivity.this, AllDaysActivity.class);
                        intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        intent_journeys.putExtra("COMPLETE_PATH", allPaths);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void requetsWindowFeature(int featureNoTitle) {
    }
}
