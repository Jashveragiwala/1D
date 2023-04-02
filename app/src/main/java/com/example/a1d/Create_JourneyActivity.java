package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;

public class Create_JourneyActivity extends AppCompatActivity {
    Button button_done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_journey);


        button_done = (Button) findViewById(R.id.done);


        button_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // I changed this from MainAcitivity4 to add_locations
                Intent intent = new Intent(Create_JourneyActivity.this, Add_locations_activity.class);
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
                        Intent intent = new Intent(Create_JourneyActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((Create_JourneyActivity.this), MainActivity5.class);
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