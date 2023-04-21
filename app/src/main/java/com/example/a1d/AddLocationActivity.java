package com.example.a1d;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// AddLocationActivity -> Add Locations
public class AddLocationActivity extends AppCompatActivity {
    Button buttonDoneAdding;
    MaterialButton AddLocation;
    String numberOfDays = "";
    String startLocation = "";
    String locationsString = "";
    ArrayList<String> locations = new ArrayList<>();
    String Location;
    TextView ErrorView;
    Marker selectedMarker = null;
    int numberOfLocations = 0;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the orientation of the activity to portrait only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Remove the title from the activity
        requetsWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the activity to full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the action bar
        getSupportActionBar().hide();

        // Set the layout for the activity
        setContentView(R.layout.activity_add_location);

        // Find the error view and set it to be initially invisible
        ErrorView = findViewById(R.id.error);
        ErrorView.setVisibility(View.GONE);

        // Find the "Done Adding" button
        buttonDoneAdding = findViewById(R.id.doneAdding);

        // Get the number of days, start location, and country from the previous activity
        numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        startLocation = getIntent().getStringExtra("START_LOCATION");
        country = getIntent().getStringExtra("COUNTRY");

        // Set a listener for the "Done Adding" butto
        buttonDoneAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if there are enough locations added
                if (numberOfLocations >= Integer.parseInt(numberOfDays)){

                    // If there are enough locations, create a string containing all of the locations
                    ErrorView.setVisibility(View.GONE);
                    for (String s : locations) {
                        locationsString += s + "%7C%";
                    }

                    // Call the getPath function to get all of the possible paths
                    int noOfDays = Integer.parseInt(numberOfDays);

                    getPath(noOfDays, startLocation, locationsString, new PathCallback() {
                        @Override
                        public void onPathsReady(ArrayList<ArrayList<String>> allPaths) {
                            // Store the paths in a singleton class
                            StorePaths s = StorePaths.getInstance();
                            s.setPaths(allPaths);

                            // Start the AllDaysActivity to display the paths
                            Intent intent = new Intent(AddLocationActivity.this, AllDaysActivity.class);
                            intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                            startActivity(intent);
                        }
                    });
                }

                // If there are not enough locations, display an error message
                else {
                    ErrorView.setVisibility(View.VISIBLE);
                    ErrorView.setText("Enter More Locations");
                }

            }
        });

        // Find the "Add Location" button
        AddLocation = findViewById(R.id.AddLocation);

        // Set a listener for the "Add Location" button
        AddLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Create a new EditText view to enter a location
                EditText editText = new EditText(AddLocationActivity.this);
                editText.setId(View.generateViewId());

                // Set the layout parameters for the EditText view
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(1220, 100);
                layoutParams.setMargins(0, 10, 10, 10); // set the top margin to 20 pixels
                editText.setLayoutParams(layoutParams);

                // Set the hint, input type, padding, text alignment, and text color for the EditText view
                editText.setHint("Location");
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                editText.setPadding(100, 2, 100, 2);
                editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText.setTextColor(Color.parseColor("#1F1F1F"));
                editText.setHintTextColor(Color.parseColor("#1F1F1F"));
                editText.setTextSize(20);
                editText.getGravity();
                editText.setBackgroundColor(Color.parseColor("#FFFFFF"));

                // Show a toast message to indicate that a new location field has been added
                Toast.makeText(AddLocationActivity.this,"New Location field added", Toast.LENGTH_LONG).show();

                // Set up delete and add icons for the EditText view
                Drawable deleteIcon = ContextCompat.getDrawable(AddLocationActivity.this, R.drawable.baseline_remove_24);
                deleteIcon.setBounds(0, 0, 100, 50);
                Drawable AddIcon = ContextCompat.getDrawable(AddLocationActivity.this, R.drawable.baseline_add_task_24);
                AddIcon.setBounds(0, 0, 90, 50);
                editText.setCompoundDrawables(deleteIcon, null, AddIcon, null);

                // Set up touch listener for the EditText view
                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        // Calculate the positions and sizes of the delete and add icons
                        int viewWidth = v.getWidth();
                        int iconWidth = deleteIcon.getIntrinsicWidth();
                        int iconLeft = editText.getPaddingLeft();
                        int iconRight = iconLeft + iconWidth;
                        int addiconRight = viewWidth-editText.getPaddingRight();
                        int addiconWidth = AddIcon.getIntrinsicWidth();
                        int addiconleft =  viewWidth - editText.getPaddingRight() - addiconWidth;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Handle click on delete icon
                            if (event.getX() >= iconLeft && event.getX() <= iconRight) {
                                // Remove the EditText view from its parent layout
                                if (selectedMarker!=null){
                                    locations.remove(editText.getText().toString());
                                    ((ViewGroup)editText.getParent()).removeView(editText);
                                    selectedMarker.remove();
                                    selectedMarker = null;
                                    Toast.makeText(AddLocationActivity.this,"Location field deleted", Toast.LENGTH_LONG).show();
                                    numberOfLocations--;
                                }
                                if (editText.getText().toString().equals("")){
                                    ((ViewGroup)editText.getParent()).removeView(editText);
                                    Toast.makeText(AddLocationActivity.this,"Location field deleted", Toast.LENGTH_LONG).show();
                                }
                                return true;
                            }

                            // Handle click on add icon
                            if (event.getX() >= addiconleft && event.getX() <= addiconRight) {
                                // Get the location name from the EditText view
                                Location = editText.getText().toString();
                                // Get the map fragment and add a marker for the location
                                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
                                mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        // Map is ready to use
                                        // Set the map type to be normal (default is hybrid)
                                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                        // Convert location name to latitude and longitude
                                        Geocoder geocoder = new Geocoder(AddLocationActivity.this);
                                        try {
                                            List<Address> addresses = geocoder.getFromLocationName(Location, 1);
                                            // Check if the location is not found
                                            if (addresses.size() == 0) {
                                                ErrorView.setVisibility(View.VISIBLE);
                                                ErrorView.setText("No such Location Found");
                                            }
                                            // If the location is found
                                            if (addresses.size() > 0) {
                                                // Geocode the entered location and country names
                                                List<Address> particularlocations = geocoder.getFromLocationName(Location, 1);
                                                Address address = particularlocations.get(0);
                                                List<Address> countryAddresses = geocoder.getFromLocationName(country, 1);
                                                Address countryAddress = countryAddresses.get(0);
                                                // Check if the entered location and country are in the same country
                                                if (countryAddress.getCountryName().equalsIgnoreCase(address.getCountryName())) {
                                                    // Hide the error view
                                                    ErrorView.setVisibility(View.GONE);
                                                    // Get the latitude and longitude of the entered location
                                                    LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                                                    // Move the camera to the desired location and zoom level
                                                    float zoomLevel = 12f;
                                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

                                                    // Add a marker at the desired location
                                                    MarkerOptions markerOptions = new MarkerOptions()
                                                            .position(location)
                                                            .title(address.getLocality())
                                                            .snippet(address.getAdminArea());
                                                    googleMap.addMarker(markerOptions);
                                                    // Add the entered location to the list of locations
                                                    locations.add(Location);
                                                    // Show a toast message to indicate that the location has been added to the map
                                                    Toast.makeText(AddLocationActivity.this,"Location added on map", Toast.LENGTH_LONG).show();
                                                    // Increase the number of locations added
                                                    numberOfLocations++;
                                                    // Hide the error view
                                                    ErrorView.setVisibility(View.GONE);

                                                    // Set a listener for when a marker is clicked on the map
                                                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                        @Override
                                                        public boolean onMarkerClick(@NonNull Marker marker) {
                                                            // Store the clicked marker in a variable
                                                            selectedMarker = marker;
                                                            return  true;
                                                        }
                                                    });
                                                }
                                                else{
                                                    // Show an error message if the entered location and country are not in the same country
                                                    ErrorView.setVisibility(View.VISIBLE);
                                                    ErrorView.setText("The location must exist in the country.");
                                                }

                                            }
                                        } catch (IOException e) {
                                            // Handle any errors that may occur during geocoding
                                            e.printStackTrace();
                                            ErrorView.setVisibility(View.VISIBLE);
                                            ErrorView.setText("Please Enter Location");
                                        }
                                    }
                                });
                                return true;
                            }
                        }
                        return false;
                    }
                });

                // Add the EditText view to a parent view, e.g. a LinearLayout
                LinearLayoutCompat linearLayout = (LinearLayoutCompat) findViewById(R.id.search_bar);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.addView(editText);

            }
        });

        // Set up bottom navigation bar
        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    // If "Home" button is clicked, start HomePageActivity
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(AddLocationActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    // If "Journeys" button is clicked, start AllDaysActivity and pass number of days as extra
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Toast.makeText(AddLocationActivity.this,"Click Done Adding to Proceed", Toast.LENGTH_LONG).show();
                        return true;

                    // Default case
                    default:
                        return false;
                }
            }
        });

    }

    // This method takes in parameters including number of days for the trip, the starting location,
    // a string of locations, and a callback interface to handle the paths
    void getPath(int numberOfDays, String startLocation, String locationsString, PathCallback callback) {
        // Creates a new ExecutorService object with a single thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Creates a new Handler object with the Looper from the main thread
        final Handler handler = new Handler(Looper.getMainLooper());

        // Executes a new Runnable in the executor
        executor.execute(new Runnable() {
            // Overrides the run() method of the Runnable interface
            @Override
            public void run() {
                // Concatenates the startLocation with the locationsString using a specific format
                String locationsStringNew = startLocation + "%7C%" + locationsString;

                // Initializes an ArrayList of ArrayLists of Strings to hold all the possible paths
                ArrayList<ArrayList<String>> allPaths = null;

                // Creates a new Trip object with the parameters
                Trip t = new Trip(numberOfDays, startLocation, locationsStringNew);

                // Calls the setCluster() method of the Trip object
                t.setCluster();
                try {
                    // Calls the setItinerary() method of the Trip object
                    t.setItinerary();
                    // Gets all the possible paths returned by the getItinerary() method of the Trip object
                    allPaths = t.getItinerary();
                } catch (Exception e) {
                    // Prints the stack trace if an exception occurs
                    e.printStackTrace();
                }

                // Initializes a new ArrayList of ArrayLists of Strings to hold the final paths
                ArrayList<ArrayList<String>> finalAllPaths = allPaths;

                // Posts a new Runnable to the Handler object to call the onPathsReady() method of the callback interface
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onPathsReady(finalAllPaths);
                    }
                });

            }
        });
    }

    private void requetsWindowFeature ( int featureNoTitle){

    }
}
