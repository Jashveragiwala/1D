package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;

// MainActivity3 -> CreateJourney

public class MainActivity3 extends AppCompatActivity {
    Button ButtonA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main3);
        ButtonA = (Button) findViewById(R.id.done);

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            Log.v("BING", "Connection worked");
            // Google Play services is installed on the device and is up-to-date
        } else {
            // Google Play services is not available or not up-to-date, prompt the user to update it
            Log.d("BING", "Google Play services is not available");
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }

        //TODO 1: get the location that the person searched using a searchview
        //TODO 2: check whether the location is a valid location
        //TODO 3: pass the location to MainActivity4 and display it on the map
        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextInputLayout textField3 = findViewById(R.id.textField3);
                String numberOfDays = textField3.getEditText().getText().toString();

                TextInputLayout textField2 = findViewById(R.id.textField2);
                String startLocation = textField2.getEditText().getText().toString();

                TextInputLayout textField1 = findViewById(R.id.textField1);
                String country = textField1.getEditText().getText().toString();

                if (numberOfDays.isEmpty() || country.isEmpty() || startLocation.isEmpty()) {
                    if (numberOfDays.isEmpty()) {
                        textField3.setError("Field is required");
                    }
                    if (country.isEmpty()) {
                        textField1.setError("Field is required");
                    }
                    if (startLocation.isEmpty()) {
                        textField2.setError("Field is required");
                    }
                } else {
                    Geocoder geocoder = new Geocoder(MainActivity3.this);
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(startLocation, 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            if (address.hasLatitude() && address.hasLongitude()) {
                                textField2.setError(null); // clear any previous error message
                                try {
                                    int numDays = Integer.parseInt(numberOfDays); // try to parse the string as an integer
                                    textField3.setError(null); // clear any previous error message
                                    Intent intent2 = new Intent(MainActivity3.this,MainActivity4.class);
                                    intent2.putExtra("NUMBER_OF_DAYS", numberOfDays);
                                    intent2.putExtra("START_LOCATION", startLocation);
                                    startActivity(intent2);
                                } catch (NumberFormatException e) { // if the string cannot be parsed as an integer
                                    textField3.setError("Please enter a valid number"); // display an error message
                                }
                            } else {
                                textField2.setError("Please enter a valid location"); // display an error message
                            }
                        } else {
                            textField2.setError("Please enter a valid location"); // display an error message
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



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
                        Intent intent = new Intent(MainActivity3.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        String numberOfDays = "0";
                        Intent intent_journeys = new Intent((MainActivity3.this), MainActivity5.class);
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