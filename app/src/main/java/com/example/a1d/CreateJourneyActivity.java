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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// CreateJourneyActivity -> CreateJourney

public class CreateJourneyActivity extends AppCompatActivity {
    Button buttonDone;
    TextInputLayout textInputCountry;
    TextInputLayout textInputStartLocation;
    TextInputLayout textInputNumberOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_journey);
        buttonDone = (Button) findViewById(R.id.done);

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            Log.v("BING", "Connection worked"); // Google Play services is installed on the device and is up-to-date
        } else {
            Log.d("BING", "Google Play services is not available"); // Google Play services is not available or not up-to-date, prompt the user to update it
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }

        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                textInputCountry = findViewById(R.id.textInputCountry);
                String country = textInputCountry.getEditText().getText().toString().toLowerCase().trim();

                textInputStartLocation = findViewById(R.id.textInputStartLocation);
                String startLocation = textInputStartLocation.getEditText().getText().toString();

                textInputNumberOfDays = findViewById(R.id.textInputNumberOfDays);
                String numberOfDays = textInputNumberOfDays.getEditText().getText().toString();

                ArrayList<String> countryList = new ArrayList<>();
                String[] isoCountries = Locale.getISOCountries();
                for (String countryCode : isoCountries) {
                    Locale locale = new Locale("", countryCode);
                    String countryName = locale.getDisplayCountry().toLowerCase();
                    countryList.add(countryName);
                }

                if (numberOfDays.isEmpty() || country.isEmpty() || startLocation.isEmpty()) {
                    if (numberOfDays.isEmpty()) {
                        textInputNumberOfDays.setError("Field is required");
                    }
                    if (country.isEmpty()) {
                        textInputCountry.setError("Field is required");
                    }
                    if (startLocation.isEmpty()) {
                        textInputStartLocation.setError("Field is required");
                    }
                } else {
                    Geocoder geocoder = new Geocoder(CreateJourneyActivity.this);
                    try {
                        List<Address> startAddresses = geocoder.getFromLocationName(startLocation, 1);
                        if (startAddresses.size() > 0) {
                            textInputStartLocation.setError(null);
                            Address startAddress = startAddresses.get(0);
                            if (startAddress.hasLatitude() && startAddress.hasLongitude()) {
                                textInputStartLocation.setError(null); // clear any previous error message
                                if (countryList.contains(country)) {
                                    textInputCountry.setError(null);
                                    List<Address> countryAddresses = geocoder.getFromLocationName(country, 1);
                                    if (countryAddresses.size() > 0) {
                                        textInputCountry.setError(null);
                                        Address countryAddress = countryAddresses.get(0);
                                        if (countryAddress.getCountryName().equalsIgnoreCase(startAddress.getCountryName())) {
                                            textInputStartLocation.setError(null);
                                            try {
                                                int numDays = Integer.parseInt(numberOfDays); // try to parse the string as an integer
                                                textInputNumberOfDays.setError(null); // clear any previous error message
                                                Intent intent2 = new Intent(CreateJourneyActivity.this, AddLocationActivity.class);
                                                intent2.putExtra("NUMBER_OF_DAYS", numberOfDays);
                                                intent2.putExtra("START_LOCATION", startLocation);
                                                intent2.putExtra("COUNTRY",country);
                                                startActivity(intent2);
                                            } catch (NumberFormatException e) { // if the string cannot be parsed as an integer
                                                textInputNumberOfDays.setError("Please enter a valid number"); // display an error message
                                            }
                                        } else {
                                            textInputStartLocation.setError("The starting location must be in the same country as the entered country.");
                                        }
                                    } else {
                                        textInputCountry.setError("Please enter a valid country");
                                    }
                                } else{
                                    textInputCountry.setError("Please enter a valid country");
                                }
                            } else {
                                textInputStartLocation.setError("Please enter a valid location"); // display an error message
                            }
                        } else {
                            textInputStartLocation.setError("Please enter a valid location"); // display an error message
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(CreateJourneyActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        String numberOfDays = "0";
                        Intent intent_journeys = new Intent((CreateJourneyActivity.this), AllDaysActivity.class);
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