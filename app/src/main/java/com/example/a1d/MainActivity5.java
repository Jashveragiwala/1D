package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity5 extends AppCompatActivity {
    Button ButtonA;
    Button ButtonB;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main5);
        txtView = (TextView) findViewById(R.id.name5);
//        ButtonA = (Button) findViewById(R.id.days);
        ButtonB = (Button) findViewById(R.id.addlocation);

        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        int num = Integer.parseInt(numberOfDays);
        System.out.println(num);
        LinearLayout linearLayout = findViewById(R.id.dayadd); // get the LinearLayout from the activity's layout// the number of buttons to generate
        if (num == 0){
            TextView textView = new TextView(this); // create a new TextView object
            textView.setText("Number of days not added to Your Journey"); // set the text of the TextView
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.RED);
            linearLayout.addView(textView); // add the TextView to the LinearLayout
        }
        else {
            for (int i = 0; i < num; i++) {
                Button button = new Button(this); // create a new button
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.width = 1000; // set the width of the button
                params.height = 180; // set the height of the button
                params.setMargins(0, 40, 0, 0);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                button.setLayoutParams(params); // apply the layout params to the button
                button.setTextColor(Color.BLUE); // set the text color of the button
                button.setTextSize(22); // set the text size of the button
                button.setText("Day " + (i + 1)); // set the button's text
                linearLayout.addView(button); // add the button to the LinearLayout
                button.setBackgroundColor(Color.RED); // set the background color of the button
                button.setBackgroundResource(R.drawable.button); // set a drawable resource as the background of the button
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // handle button click
                        Intent intent = new Intent(MainActivity5.this,MainActivity6.class);
                        intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
                        startActivity(intent);
                    }
                });
        }


        }

//        ButtonA.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(MainActivity5.this,MainActivity6.class);
//                startActivity(intent);
//            }}
//        );
        ButtonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity5.this,MainActivity4.class);
                intent.putExtra("NUMBER_OF_DAYS", numberOfDays);
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
                        Intent intent = new Intent(MainActivity5.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((MainActivity5.this), MainActivity5.class);
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