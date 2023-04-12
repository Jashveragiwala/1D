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
public class AllDaysActivity extends AppCompatActivity {
//    TextView txtView;
    ArrayList<ArrayList<String>> allPaths;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_all_days);
//        txtView = (TextView) findViewById(R.id.name5);

        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");

        allPaths = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("ALL_PATHS");
        // log the data that we get

        if (allPaths == null) {
            StorePaths s = StorePaths.getInstance();
            allPaths = s.getPaths();
        }


        int num = Integer.parseInt(numberOfDays);
        System.out.println(num);
        LinearLayout linearLayout = findViewById(R.id.addDay); // get the LinearLayout from the activity's layout// the number of buttons to generate
        if (num == 0){
            TextView textView = new TextView(this); // create a new TextView object
            textView.setText("Number of days not added to Your Journey"); // set the text of the TextView
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.RED);
            linearLayout.addView(textView); // add the TextView to the LinearLayout
        }
        else {
            for (int i = 0; i < num; i++) {
                Button buttonDayI = new Button(this); // create a new button
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
                int finalI = i;
                buttonDayI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllDaysActivity.this, FinalPathActivity.class);
                        intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        intent.putExtra("COMPLETE_PATH", allPaths);
                        intent.putExtra("INDEX", finalI);
                        startActivity(intent);
                    }
                });
        }


        }



        NavigationBarView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        // Handle click on "Home" button
                        Intent intent = new Intent(AllDaysActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        ArrayList<ArrayList<String>> allPaths = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("ALL_PATHS");
                        System.out.println("heyeyeyeye");
                        System.out.println(allPaths);
                        Intent intent_journeys = new Intent((AllDaysActivity.this), AllDaysActivity.class);
                        intent_journeys.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        intent_journeys.putExtra("COMPLETE_PATH", allPaths);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void requetsWindowFeature(int featureNoTitle) {
    }
}