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
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// FinalPathActivity -> Page for each Day in Final Optimized path
public class FinalPathActivity extends AppCompatActivity {
    TextView textViewDayNumber;
    TextView textViewPath;
    ArrayList<ArrayList<String>> allPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_final_path);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");

        textViewDayNumber = findViewById(R.id.dayNumber);
        textViewPath = findViewById(R.id.path);

        int index = getIntent().getIntExtra("INDEX", 0);

        StorePaths s = StorePaths.getInstance();
        allPaths = s.getPaths();

        String finalOutput = "";

        ArrayList<String> currentPath = allPaths.get(index);
        ArrayList<LatLng> locations = new ArrayList<>();

        for (int i=0; i < currentPath.size(); i++) {
            if (i == currentPath.size() - 1)
                finalOutput += currentPath.get(i).toUpperCase() + " -> " + currentPath.get(0).toUpperCase();
            else
                finalOutput += currentPath.get(i).toUpperCase() + " -> ";

            String locationName = currentPath.get(i);

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
            }

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                LatLng firstLocation = locations.get(0);
                float zoomLevel = 12f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, zoomLevel));

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
                    }
                }

                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey("AIzaSyD03pQpPpanpGgrJyTfCagPxTLAya8pQws")
                        .build();

                DirectionsApiRequest req = DirectionsApi.newRequest(context);

                LatLng origin = locations.get(0);
                LatLng destination = locations.get(locations.size() - 1);
                req.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
                req.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));

                List<com.google.maps.model.LatLng> waypoints = new ArrayList<>();
                for (int i = 1; i < locations.size() - 1; i++) {
                    LatLng waypoint = locations.get(i);
                    waypoints.add(new com.google.maps.model.LatLng(waypoint.latitude, waypoint.longitude));
                }
                req.waypoints(waypoints.toArray(new com.google.maps.model.LatLng[waypoints.size()]));

                req.mode(TravelMode.DRIVING);

                try {
                    DirectionsResult result = req.await();
                    if (result.routes.length > 0) {
                        DirectionsRoute route = result.routes[0];
                        List<LatLng> points = new ArrayList<>();
                        for (com.google.maps.model.LatLng latLng : route.overviewPolyline.decodePath()) {
                            points.add(new LatLng(latLng.lat, latLng.lng));
                        }
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(points)
                                .width(10)
                                .color(Color.RED);
                        googleMap.addPolyline(polylineOptions);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        int dayNo = index + 1;
        textViewDayNumber.setText("Day " + dayNo);
        textViewPath.setText(finalOutput);

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
