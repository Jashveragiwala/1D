package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

// AllDaysActivity -> Number of Days (Final Optimized path)
// This class represents the activity where the user can view all the days of a journey
public class AllDaysActivity extends AppCompatActivity {
    ArrayList<ArrayList<String>> allPaths;// An ArrayList of ArrayLists of String to store all the paths of the journey

    // Called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Request no title for the activity's window
        requetsWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the activity to full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the action bar
        getSupportActionBar().hide();

        // Set the activity layout to the activity_all_days.xml file
        setContentView(R.layout.activity_all_days);

        // Get the number of days of the journey from the previous activity using an intent extra
        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");

        // Get all the paths of the journey from the StorePaths singleton instance
        StorePaths s = StorePaths.getInstance();
        allPaths = s.getPaths();

        // Parse the number of days from a String to an int
        int num = Integer.parseInt(numberOfDays);

        // Get the LinearLayout from the activity's layout
        LinearLayout linearLayout = findViewById(R.id.addDay);

        // If the number of days is 0, display a TextView indicating that the user needs to create a journey first
        if (num == 0){
            TextView textViewNoJourney = new TextView(this); // create a new TextView object
            textViewNoJourney.setText("Create a Journey first"); // set the text of the TextView
            textViewNoJourney.setTextSize(20);
            textViewNoJourney.setGravity(Gravity.CENTER);
            textViewNoJourney.setTextColor(Color.RED);
            linearLayout.addView(textViewNoJourney); // add the TextView to the LinearLayout
        }
        // Otherwise, generate a button for each day of the journey
        else {
            for (int i = 0; i < num; i++) {
                // Create a new button
                Button buttonDayI = new Button(this);

                // Set the layout parameters of the button
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.width = 1000; // set the width of the button
                params.height = 180; // set the height of the button
                params.setMargins(0, 40, 0, 0);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                buttonDayI.setLayoutParams(params); // apply the layout params to the button

                buttonDayI.setTextColor(Color.BLUE); // set the text color of the button
                buttonDayI.setTextSize(22); // set the text size of the button
                buttonDayI.setText("Day " + (i + 1)); // set the button's text
                linearLayout.addView(buttonDayI); // add the button to the LinearLayout
                buttonDayI.setBackgroundColor(Color.RED); // set the background color of the button
                buttonDayI.setBackgroundResource(R.drawable.button); // set a drawable resource as the background of the button

                // Add an onClickListener to the button to start the FinalPathActivity when the button is clicked
                int finalI = i;
                buttonDayI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllDaysActivity.this, FinalPathActivity.class);
                        intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        intent.putExtra("INDEX", finalI);
                        startActivity(intent);
                    }
                });
        }
        }

        // Get the bottom navigation bar from the activity's layout
        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    // If "Home" button is clicked, start HomePageActivity
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(AllDaysActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    // If "Journeys" button is clicked, start AllDaysActivity and pass number of days as extra
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((AllDaysActivity.this), AllDaysActivity.class);
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