package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
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

    //This method is called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the orientation of the screen to portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Removes the title bar of the app
        requetsWindowFeature(Window.FEATURE_NO_TITLE);

        //Sets the app to run in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hides the action bar of the app
        getSupportActionBar().hide();

        //Sets the content view of the activity to activity_create_journey.xml
        setContentView(R.layout.activity_create_journey);

        //Assigns the Button view with id "done" to the buttonDone variable
        buttonDone = (Button) findViewById(R.id.done);

        //Checks if Google Play Services is available on the device and logs the result
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            Log.v("BING", "Connection worked"); // Google Play services is installed on the device and is up-to-date
        } else {
            Log.d("BING", "Google Play services is not available"); // Google Play services is not available or not up-to-date, prompt the user to update it
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }

        //Sets an onClickListener for the buttonDone button
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Gets the text input from the TextInputLayout with id "textInputCountry"
                textInputCountry = findViewById(R.id.textInputCountry);
                String country = textInputCountry.getEditText().getText().toString().toLowerCase().trim();

                //Gets the text input from the TextInputLayout with id "textInputStartLocation"
                textInputStartLocation = findViewById(R.id.textInputStartLocation);
                String startLocation = textInputStartLocation.getEditText().getText().toString();

                //Gets the text input from the TextInputLayout with id "textInputNumberOfDays"
                textInputNumberOfDays = findViewById(R.id.textInputNumberOfDays);
                String numberOfDays = textInputNumberOfDays.getEditText().getText().toString();

                //Creates a list of country names from ISO country codes using the Locale class
                ArrayList<String> countryList = new ArrayList<>();
                String[] isoCountries = Locale.getISOCountries();
                for (String countryCode : isoCountries) {
                    Locale locale = new Locale("", countryCode);
                    String countryName = locale.getDisplayCountry().toLowerCase();
                    countryList.add(countryName);
                }

                //Checks if any of the text input fields are empty and displays an error message if they are
                if (numberOfDays.isEmpty() || country.isEmpty() || startLocation.isEmpty()) {
                    if (numberOfDays.isEmpty()) {
                        textInputNumberOfDays.setError("Field is required");
                    }
                    else{
                        textInputNumberOfDays.setError(null);
                    }
                    if (country.isEmpty()) {
                        textInputCountry.setError("Field is required");
                    }
                    else {
                        textInputCountry.setError(null);
                    }
                    if (startLocation.isEmpty()) {
                        textInputStartLocation.setError("Field is required");
                    }
                    else{
                        textInputStartLocation.setError(null);
                    }
                } else {
                    // if all required fields are filled, proceed with geocoding and validation
                    Geocoder geocoder = new Geocoder(CreateJourneyActivity.this);
                    try {
                        // clear any previous error message
                        if (!numberOfDays.isEmpty()) {
                            textInputNumberOfDays.setError(null);
                        }
                        // get the starting location address from the entered string
                        List<Address> startAddresses = geocoder.getFromLocationName(startLocation, 1);
                        if (startAddresses.size() > 0) {
                            textInputStartLocation.setError(null);
                            Address startAddress = startAddresses.get(0);
                            if (startAddress.hasLatitude() && startAddress.hasLongitude()) {
                                // clear any previous error message
                                textInputStartLocation.setError(null);

                                // check if the entered country is valid and get its address
                                if (countryList.contains(country)) {
                                    textInputCountry.setError(null);
                                    List<Address> countryAddresses = geocoder.getFromLocationName(country, 1);
                                    if (countryAddresses.size() > 0) {
                                        textInputCountry.setError(null);
                                        Address countryAddress = countryAddresses.get(0);
                                        // check if the starting location and entered country are in the same country
                                        if (countryAddress.getCountryName().equalsIgnoreCase(startAddress.getCountryName())) {
                                            textInputStartLocation.setError(null);
                                            try {
                                                // parse the entered number of days as an integer and pass it to the next activity
                                                int numDays = Integer.parseInt(numberOfDays);
                                                textInputNumberOfDays.setError(null); // clear any previous error message
                                                Intent intent2 = new Intent(CreateJourneyActivity.this, AddLocationActivity.class);
                                                intent2.putExtra("NUMBER_OF_DAYS", numberOfDays);
                                                intent2.putExtra("START_LOCATION", startLocation);
                                                intent2.putExtra("COUNTRY",country);
                                                startActivity(intent2);
                                            } catch (NumberFormatException e) {
                                                // if the entered number of days cannot be parsed as an integer, display an error message
                                                textInputNumberOfDays.setError("Please enter a valid number");
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
                                // if the starting location cannot be geocoded, display an error message
                                textInputStartLocation.setError("Please enter a valid location");
                            }
                        } else {
                            // if the starting location cannot be geocoded, display an error message
                            textInputStartLocation.setError("Please enter a valid location");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // set a listener for the bottom navigation bar
        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // If "Home" button is clicked, start HomePageActivity
                        Intent intent = new Intent(CreateJourneyActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // If "Journeys" button is clicked, start AllDaysActivity and pass number of days as extra
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