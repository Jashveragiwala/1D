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
    // This is the class for the activity that displays the final path for a given journey day
    public class FinalPathActivity extends AppCompatActivity {
        TextView textViewDayNumber; // TextView to display the day number
        TextView textViewPath; // TextView to display the final path
        ArrayList<ArrayList<String>> allPaths; // List of all paths for the journey

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_final_path);
            requetsWindowFeature(Window.FEATURE_NO_TITLE); // Remove the title bar from the activity
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Set the activity to fullscreen
            getSupportActionBar().hide(); // Hide the action bar from the activity

            String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS"); // Get the number of days for the journey

            textViewDayNumber = findViewById(R.id.dayNumber); // Find the TextView for the day number
            textViewPath = findViewById(R.id.path); // Find the TextView for the final path

            int index = getIntent().getIntExtra("INDEX", 0); // Get the index of the current day

            StorePaths s = StorePaths.getInstance(); // Get the instance of the class that stores all paths for the journey
            allPaths = s.getPaths(); // Get all paths for the journey

            String finalOutput = ""; // String to hold the final output for the path

            ArrayList<String> currentPath = allPaths.get(index); // Get the current path for the day
            ArrayList<LatLng> locations = new ArrayList<>(); // List of all locations on the current path

            // Iterate through each location in the current path
            for (int i=0; i < currentPath.size(); i++) {
                // If it is the last location, add the final connection to the first location
                if (i == currentPath.size() - 1)
                    finalOutput += currentPath.get(i).toUpperCase() + " -> " + currentPath.get(0).toUpperCase();
                // Otherwise, add the connection to the next location
                else
                    finalOutput += currentPath.get(i).toUpperCase() + " -> ";

                String locationName = currentPath.get(i);

                Geocoder geocoder = new Geocoder(FinalPathActivity.this); // Create a Geocoder to convert the location name to coordinates
                try {
                    List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        LatLng location = new LatLng(address.getLatitude(), address.getLongitude()); // Get the coordinates for the location
                        locations.add(location); // Add the location to the list of all locations
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Get the map fragment and set it up
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Set the map type to normal

                    LatLng firstLocation = locations.get(0);
                    float zoomLevel = 12f;
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, zoomLevel)); // Move the camera to the first location on the path
                    // Iterate through each location and add a marker for it on the map
                    for (int i = 0; i < locations.size(); i++) {
                        // Get the latitude and longitude of the location
                        LatLng location = locations.get(i);
                        // Create a new Geocoder object to retrieve the address
                        Geocoder geocoder = new Geocoder(FinalPathActivity.this);
                        try {
                            // Retrieve the address of the location using the Geocoder
                            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                            if (addresses.size() > 0) {
                                // Get the first address in the list
                                Address address = addresses.get(0);
                                // Create a new MarkerOptions object with the address information
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(location)
                                        .title(address.getLocality())
                                        .snippet(address.getAdminArea());
                                // Add the marker to the Google Map
                                googleMap.addMarker(markerOptions);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // Create a new GeoApiContext object with the Google Maps API key
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey("YOUR_API_KEY")
                            .build();

                    // API KEY removed for security reasons

                    // Create a new DirectionsApiRequest object
                    DirectionsApiRequest req = DirectionsApi.newRequest(context);

                    // Get the first and last locations from the list
                    LatLng origin = locations.get(0);
                    LatLng destination = locations.get(locations.size() - 1);

                    // Set the origin and destination for the directions request
                    req.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
                    req.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));

                    // Add any intermediate locations as waypoints
                    List<com.google.maps.model.LatLng> waypoints = new ArrayList<>();
                    for (int i = 1; i < locations.size() - 1; i++) {
                        LatLng waypoint = locations.get(i);
                        waypoints.add(new com.google.maps.model.LatLng(waypoint.latitude, waypoint.longitude));
                    }
                    req.waypoints(waypoints.toArray(new com.google.maps.model.LatLng[waypoints.size()]));

                    // Set the mode of transportation for the directions request
                    req.mode(TravelMode.DRIVING);

                    try {
                        // Send the directions request and retrieve the results
                        DirectionsResult result = req.await();
                        if (result.routes.length > 0) {
                            // Get the first route from the results
                            DirectionsRoute route = result.routes[0];
                            // Decode the overview polyline and convert it to a list of LatLng points
                            List<LatLng> points = new ArrayList<>();
                            for (com.google.maps.model.LatLng latLng : route.overviewPolyline.decodePath()) {
                                points.add(new LatLng(latLng.lat, latLng.lng));
                            }
                            // Create a new PolylineOptions object with the polyline points and add it to the Google Map
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

            // Set up bottom navigation bar
            NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
            bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    switch(id){
                        // If "Home" button is clicked, start HomePageActivity
                        case R.id.nav_home:
                            // Handle click on "Home" button
                            Intent intent = new Intent(FinalPathActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            return true;
                        // If "Journeys" button is clicked, start AllDaysActivity and pass number of days as extra
                        case R.id.nav_journeys:
                            // Handle click on "Journeys" button
                            Intent intent_journeys = new Intent(FinalPathActivity.this, AllDaysActivity.class);
                            intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                            startActivity(intent_journeys);
                            return true;

                        // Default case
                        default:
                            return false;
                    }
                }
            });
        }

        private void requetsWindowFeature(int featureNoTitle) {
        }
    }
