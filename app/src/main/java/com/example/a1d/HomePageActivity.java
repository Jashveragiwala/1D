package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;

// Home Page
public class HomePageActivity extends AppCompatActivity{

    // Define variables for UI elements
    Button buttonGetStarted;
    TextView textViewTitle;

    // Set up activity on creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide app title bar
        requestsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // Set the layout for the activity
        setContentView(R.layout.activity_home_page);

        // Get UI elements by their IDs
        textViewTitle = findViewById(R.id.title);
        buttonGetStarted = findViewById(R.id.button_get_started);

        // Initialize variable for number of days
        String numberOfDays = "0";

        // Set up onClick listener for "Get Started" button
        buttonGetStarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Log a message to the console
                String TAG = "HomePage";
                Log.d(TAG, "onClick: get started is clicked");

                // Create intent to start CreateJourneyActivity
                Intent intent = new Intent(HomePageActivity.this, CreateJourneyActivity.class);
                startActivity(intent);
            }}
        );

        // Set up bottom navigation bar
        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    // If "Home" button is clicked, start HomePageActivity
                    case R.id.nav_home:
                        Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;

                    // If "Journeys" button is clicked, start AllDaysActivity and pass number of days as extra
                    case R.id.nav_journeys:
                        Intent intent_journeys = new Intent(HomePageActivity.this, AllDaysActivity.class);
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

    // Define a function to request a window feature (in this case, no title bar)
    private void requestsWindowFeature(int featureNoTitle) {
    }
}