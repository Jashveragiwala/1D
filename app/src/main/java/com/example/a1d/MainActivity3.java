package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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


        //TODO 1: get the location that the person searched using a searchview
        //TODO 2: check whether the location is a valid location
        //TODO 3: pass the location to MainActivity4 and display it on the map
        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextInputLayout textField2 = findViewById(R.id.textField3);

                String numberOfDays = textField2.getEditText().getText().toString();
                System.out.println(numberOfDays);
                if (numberOfDays.isEmpty()) {
                    textField2.setError("Field is required");
                } else {
                    try {
                        int numDays = Integer.parseInt(numberOfDays); // try to parse the string as an integer
                        textField2.setError(null); // clear any previous error message
                        Intent intent2 = new Intent(MainActivity3.this,MainActivity4.class);
                        intent2.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        startActivity(intent2);
                    } catch (NumberFormatException e) { // if the string cannot be parsed as an integer
                        textField2.setError("Please enter a valid number"); // display an error message
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