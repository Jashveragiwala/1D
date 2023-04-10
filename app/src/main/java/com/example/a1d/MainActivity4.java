package com.example.a1d;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.a1d.R;
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
import java.util.List;

public class MainActivity4 extends AppCompatActivity {
    Button ButtonA;
    MaterialButton AddLoc;
    String Location;
    TextView ErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// tell akash
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main4);
        ErrorView = findViewById(R.id.error);
        ErrorView.setVisibility(View.GONE);


        ButtonA = (Button) findViewById(R.id.doneadding);

        String TAG = "mainActivity4";
        Log.i(TAG, "onCreate: I am inside activity4");
        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        System.out.println(numberOfDays);


        MaterialButton AddLoc = findViewById(R.id.moreinput);


        AddLoc.setOnClickListener(new View.OnClickListener() {

            int count = 1;
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(MainActivity4.this);
                editText.setId(View.generateViewId());
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(1220, 100);
                layoutParams.setMargins(0, 10, 10, 10); // set the top margin to 20 pixels
                editText.setLayoutParams(layoutParams);
                editText.setHint("Location");
                count++;
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                editText.setPadding(100, 2, 100, 2);
                editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText.setTextColor(Color.parseColor("#1F1F1F"));
                editText.setHintTextColor(Color.parseColor("#1F1F1F"));
                editText.setTextSize(20);
                editText.getGravity();
                editText.setBackgroundColor(Color.parseColor("#FFFFFF"));


                // delete feature

                Drawable deleteIcon = ContextCompat.getDrawable(MainActivity4.this, R.drawable.baseline_remove_24);
                deleteIcon.setBounds(0, 0, 100, 50);
                Drawable AddIcon = ContextCompat.getDrawable(MainActivity4.this, R.drawable.baseline_add_task_24);
                AddIcon.setBounds(0, 0, 90, 50);
                editText.setCompoundDrawables(deleteIcon, null, AddIcon, null);


                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int viewWidth = v.getWidth();
                        int iconWidth = deleteIcon.getIntrinsicWidth();
                        int iconLeft = editText.getPaddingLeft();
                        int iconRight = iconLeft + iconWidth;
                        System.out.println(viewWidth);
                        int addiconRight = viewWidth-editText.getPaddingRight();
                        int addiconWidth = AddIcon.getIntrinsicWidth();
                        int addiconleft =  viewWidth - editText.getPaddingRight() - addiconWidth;
                        System.out.println(addiconleft);
                        System.out.println(addiconRight);
                        System.out.println(addiconWidth);

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getX() >= iconLeft && event.getX() <= iconRight) {
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
                                        Geocoder geocoder = new Geocoder(MainActivity4.this);
                                        try {
                                            List<Address> addresses = geocoder.getFromLocationName(Location, 1);
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
                                                Marker marker = googleMap.addMarker(markerOptions);

// Remove the marker
                                                marker.remove();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            ErrorView.setVisibility(View.VISIBLE);
                                            System.out.println("ERROR No Location Found");
                                        }
                                    }
                                });
                                ((ViewGroup)editText.getParent()).removeView(editText);
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
                                        Geocoder geocoder = new Geocoder(MainActivity4.this);
                                        try {
                                            List<Address> addresses = geocoder.getFromLocationName(Location, 1);
                                            if (addresses.size() == 0) {
                                                ErrorView.setVisibility(View.VISIBLE);
                                                System.out.println("ERROR No Location Found");
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
                                                ErrorView.setVisibility(View.GONE);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            ErrorView.setVisibility(View.VISIBLE);
                                            System.out.println("ERROR No Location Found");
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

        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity4.this,MainActivity5.class);
                intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                startActivity(intent);

            }}
        );

        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(MainActivity4.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((MainActivity4.this), MainActivity5.class);
                        intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;}
            }
        });


    }





    private void requetsWindowFeature(int featureNoTitle) {
    }
}