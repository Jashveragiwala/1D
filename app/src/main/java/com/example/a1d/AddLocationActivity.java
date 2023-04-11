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
    Button ButtonA;
    MaterialButton AddLoc;

    String numberOfDays = "";
    String startLocation = "";
    String locationsString = "";
    ArrayList<String> locations = new ArrayList<>();
    String Location;
    TextView ErrorView;
    Marker selectedmarker = null;
    int numberoflocations = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_location);
        ErrorView = findViewById(R.id.error);
        ErrorView.setVisibility(View.GONE);
        ButtonA = (Button) findViewById(R.id.doneadding);

        String TAG = "mainActivity4";
        Log.i(TAG, "onCreate: I am inside activity4");

        numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        startLocation = getIntent().getStringExtra("START_LOCATION");

        ButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberoflocations >= Integer.parseInt(numberOfDays)){
                    ErrorView.setVisibility(View.GONE);
                    for (String s : locations) {
                        locationsString += s + "%7C%";
                    }

                    int noOfDays = Integer.parseInt(numberOfDays);

                    getPath(noOfDays, startLocation, locationsString, new PathCallback() {
                        @Override
                        public void onPathsReady(ArrayList<ArrayList<String>> allPaths) {
                            System.out.println(allPaths);
                            Intent intent = new Intent(AddLocationActivity.this, AllDaysActivity.class);
                            intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                            intent.putExtra("ALL_PATHS", allPaths);
                            startActivity(intent);
                        }
                    });
                }

                else {
                    ErrorView.setVisibility(View.VISIBLE);
                    ErrorView.setText("Enter More Locations");
                }

            }
        });

        MaterialButton AddLoc = findViewById(R.id.moreinput);
        AddLoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText editText = new EditText(AddLocationActivity.this);
                editText.setId(View.generateViewId());

                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(1220, 100);
                layoutParams.setMargins(0, 10, 10, 10); // set the top margin to 20 pixels
                editText.setLayoutParams(layoutParams);
                editText.setHint("Location");
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                editText.setPadding(100, 2, 100, 2);
                editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText.setTextColor(Color.parseColor("#1F1F1F"));
                editText.setHintTextColor(Color.parseColor("#1F1F1F"));
                editText.setTextSize(20);
                editText.getGravity();
                editText.setBackgroundColor(Color.parseColor("#FFFFFF"));

                Toast.makeText(AddLocationActivity.this,"New Location field added", Toast.LENGTH_LONG).show();

                // delete feature

                Drawable deleteIcon = ContextCompat.getDrawable(AddLocationActivity.this, R.drawable.baseline_remove_24);
                deleteIcon.setBounds(0, 0, 100, 50);
                Drawable AddIcon = ContextCompat.getDrawable(AddLocationActivity.this, R.drawable.baseline_add_task_24);
                AddIcon.setBounds(0, 0, 90, 50);
                editText.setCompoundDrawables(deleteIcon, null, AddIcon, null);

                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int viewWidth = v.getWidth();
                        int iconWidth = deleteIcon.getIntrinsicWidth();
                        int iconLeft = editText.getPaddingLeft();
                        int iconRight = iconLeft + iconWidth;
                        int addiconRight = viewWidth-editText.getPaddingRight();
                        int addiconWidth = AddIcon.getIntrinsicWidth();
                        int addiconleft =  viewWidth - editText.getPaddingRight() - addiconWidth;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getX() >= iconLeft && event.getX() <= iconRight) {
                                // Remove the EditText view from its parent layout
                                if (selectedmarker!=null){
                                    locations.remove(editText.getText().toString());
                                    ((ViewGroup)editText.getParent()).removeView(editText);
                                    selectedmarker.remove();
                                    selectedmarker = null;
                                    Toast.makeText(AddLocationActivity.this,"Location field deleted", Toast.LENGTH_LONG).show();
                                    numberoflocations--;
                                }
                                if (editText.getText().toString().equals("")){
                                    ((ViewGroup)editText.getParent()).removeView(editText);
                                    Toast.makeText(AddLocationActivity.this,"Location field deleted", Toast.LENGTH_LONG).show();
                                }
                                return true;
                            }
                            if (event.getX() >= addiconleft && event.getX() <= addiconRight) {
                                // Remove the EditText view from its parent layout
                                Location = editText.getText().toString();
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
                                            if (addresses.size() == 0) {
                                                ErrorView.setVisibility(View.VISIBLE);
                                                ErrorView.setText("No such Location Found");
                                            }
                                            if (addresses.size() > 0) {
                                                Address address = addresses.get(0);
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
                                                locations.add(Location);
                                                Toast.makeText(AddLocationActivity.this,"Location added on map", Toast.LENGTH_LONG).show();
                                                numberoflocations++;
                                                ErrorView.setVisibility(View.GONE);

                                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                    @Override
                                                    public boolean onMarkerClick(@NonNull Marker marker) {

                                                        selectedmarker = marker;
                                                        return  true;
                                                    }
                                                });
                                            }
                                        } catch (IOException e) {
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


        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(AddLocationActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((AddLocationActivity.this), AllDaysActivity.class);
                        intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    void getPath(int numberOfDays, String startLocation, String locationsString, PathCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {

            @Override
            public void run() {
                // int numberOfDays = 2;
                // String startLocation = "SUTD";
                // String locationsString = "HomeTeamNS Bukit Batok%7C%SUTD%7C%NUS%7C%NTU%7C%Singapore Management University%7C%Singapore Institute of Technology%7C%Simei MRT%7C%51 Changi Village Rd%7C%Toppers Education Centre%7C%Waterway Point%7C%";

                String locationsStringNew = startLocation + "%7C%" + locationsString;

                ArrayList<ArrayList<String>> allPaths = null;

                Trip t = new Trip(numberOfDays, startLocation, locationsStringNew);
                t.setCluster();
                try {
                    t.setItinerary();
                    allPaths = t.getItinerary();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayList<ArrayList<String>> finalAllPaths = allPaths;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(finalAllPaths);
                        callback.onPathsReady(finalAllPaths);
                        //Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                        //intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        //intent.putExtra("ALL_PATHS", allPaths);
                        //startActivity(intent);
                    }
                });

            }
        });
    }

    private void requetsWindowFeature ( int featureNoTitle){

    }
}
