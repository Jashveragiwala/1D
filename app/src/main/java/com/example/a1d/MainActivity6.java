package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


// MainActivity6 -> Page for each Day in Final Optimized path
public class MainActivity6 extends AppCompatActivity {
//    Button ButtonB;
//    Button ButtonC;
    TextView txtView;
    TextView path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main6);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        String numberOfDays = getIntent().getStringExtra("NUMBER_OF_DAYS");
        txtView = (TextView) findViewById(R.id.name6);
        path = (TextView) findViewById(R.id.pathout);

        ArrayList<ArrayList<String>> allPaths = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("COMPLETE_PATH");
        int index = getIntent().getIntExtra("INDEX", 0);

        String finalOutput = "";

        ArrayList<String> currentPath = allPaths.get(index);

        for (int i=0; i < currentPath.size(); i++) {
            if (i == currentPath.size() - 1)
                finalOutput += currentPath.get(i).toUpperCase();
            else
                finalOutput += currentPath.get(i).toUpperCase() + " -> ";
        }

        int dayNo = index + 1;
        txtView.setText("Day " + dayNo);
        path.setText(finalOutput);

        // System.out.println("HELLO");
        // System.out.println(allPaths);
        // System.out.println(index);

        // System.out.println(allPaths.get(index));

//        ButtonB = (Button) findViewById(R.id.button3);
//        ButtonC = (Button) findViewById(R.id.button4);

//        ButtonB.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(MainActivity6.this,MainActivity.class);
//                startActivity(intent);
//            }}
//        );
//
//        ButtonC.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(MainActivity6.this,MainActivity2.class);
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
                        Intent intent = new Intent(MainActivity6.this, HomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent(MainActivity6.this, MainActivity5.class);
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
