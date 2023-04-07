package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

// AllJourneys -> Deleted Page
public class All_JourneysActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    Button ButtonC;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_all_journeys);
        txtView = (TextView) findViewById(R.id.All_journeys_heading);

        ButtonC = (Button) findViewById(R.id.journeys3);

//        Button testButton = findViewById(R.id.test_maps_button);
//        testButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(All_JourneysActivity.this, Add_locations_activity.class);
//                startActivity(intent);
//            }
//        });

//        ButtonC.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                String TAG = "HomePage";
//                Log.d(TAG, "onClick: get started is clicked"); // logcat logs that button is clicked
//                Intent intent = new Intent(All_JourneysActivity.this, MainActivity3.class);
//                startActivity(intent);
//            }}
//        );

        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(All_JourneysActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((All_JourneysActivity.this), All_JourneysActivity.class);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;}
            }
        });
    }
    private int buttonCount = 1;
    public void addButton(View view) {
        buttonContainer = findViewById(R.id.button_container);

        MaterialButton newButton = new MaterialButton(this);
        newButton.setId(View.generateViewId());
        System.out.println(newButton.getId());

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(90, 20, 90, 0);
        newButton.setLayoutParams(params);
        // Set the button's corner radius and background color
        newButton.setCornerRadius(60);
        newButton.setBackgroundColor(Color.WHITE);
        // Set the button's height
        newButton.setHeight(300);
        // Set the button's padding
        newButton.setPadding(90, 50, 0, 50);
        newButton.setText("Journey");
        newButton.setTextColor(Color.parseColor("#18C0C1"));
        newButton.setTextSize(45);
        newButton.setIcon(getResources().getDrawable(R.drawable.trash));
        newButton.setIconSize(100); // reduce the icon size
        newButton.setIconTint(ColorStateList.valueOf(Color.parseColor("#FF0000")));
        Drawable icon = getResources().getDrawable(R.drawable.trash);
        icon.setBounds(0, 0, 100, 100);
        newButton.setCompoundDrawables(icon, null, null, null);

        // Set OnClickListener on the button
        LinearLayout layout = findViewById(R.id.button_container);
        layout.addView(newButton);


        buttonCount++;
        newButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                if (x < 220) {
                    // Delete the button
                    layout.removeView(newButton);
                    buttonCount--;
                } else {
                    // Start the new activity
                    Intent intent = new Intent(All_JourneysActivity.this, MainActivity4.class);
                    startActivity(intent);
                }
                return true;
            }});
    }

    private void requetsWindowFeature(int featureNoTitle) {
    }
}