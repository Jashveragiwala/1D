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

    Button ButtonGetStarted;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        txtView = (TextView) findViewById(R.id.name);
        ButtonGetStarted = (Button) findViewById(R.id.button_get_started);

        String numberOfDays = "0";

        ButtonGetStarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String TAG = "HomePage";
                Log.d(TAG, "onClick: get started is clicked"); // logcat logs that button is clicked
                Intent intent = new Intent(HomePageActivity.this, CreateJourneyActivity.class);
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
                        Intent intent = new Intent(HomePageActivity.this,
                                HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((HomePageActivity.this),
                                AllDaysActivity.class);
                        intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;}
            }
        });

    }

    private void requestsWindowFeature(int featureNoTitle) {
    }
}