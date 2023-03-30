package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    Button ButtonC;
    TextView txtView;

    private LinearLayout buttonContainer;
    private int buttonCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main2);
        txtView = (TextView) findViewById(R.id.name2);
        ButtonC = (Button) findViewById(R.id.journeys3);


//        ButtonC.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                System.out.println(buttonCount);
//                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
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
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((MainActivity2.this), MainActivity2.class);
                        startActivity(intent_journeys);
                        return true;
                    default:
                        return false;}
            }
        });
    }
    public void addButton(View view) {
        buttonContainer = findViewById(R.id.button_container);
        if (buttonCount <= 4) {
            MaterialButton newButton = new MaterialButton(this);
            newButton.setId(View.generateViewId());
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    startActivity(intent);
                }
            });

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(90, 20, 90, 0);
            newButton.setLayoutParams(params);

            // Set the button's corner radius and background color
            newButton.setCornerRadius(60);
            newButton.setBackgroundColor(Color.WHITE);

            // Set the button's height
            newButton.setHeight(300);

            // Set the button's padding
            newButton.setPadding(90, 50, 0, 50);

            newButton.setText("Day " + buttonCount);
            newButton.setTextColor(Color.parseColor("#18C0C1"));
            newButton.setTextSize(45);

            newButton.setIcon(getResources().getDrawable(R.drawable.trash));
            newButton.setIconSize(140);
            newButton.setIconTint(ColorStateList.valueOf(Color.parseColor("#FF0000")));

            // Add the button to the layout
            LinearLayout layout = findViewById(R.id.button_container);
            layout.addView(newButton);

            buttonCount++;

        }
        else if (buttonCount == 5) {
            TextView textView = new TextView(this);
            textView.setText("Only 4 journeys are allowed.");
            textView.setTextColor(Color.RED);
            textView.setGravity(Gravity.CENTER);
            buttonContainer.addView(textView);
            buttonCount++;
        }
        else {
            buttonCount++;
        }
    }


    private void requetsWindowFeature(int featureNoTitle) {
    }
}