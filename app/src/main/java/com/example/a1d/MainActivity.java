package com.example.a1d;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Button;

import com.example.a1d.MainActivity2;
import com.example.a1d.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{
    Button ButtonA;
    //    Button ButtonB;
//    Button ButtonC;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requetsWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.name);
        ButtonA = (Button) findViewById(R.id.button2);
        // changed the navigation from table to bottom navigation view
//        ButtonB = (Button) findViewById(R.id.button3);
//        ButtonC = (Button) findViewById(R.id.button4);

        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }}
        );

//        ButtonB.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(MainActivity.this,MainActivity.class);
//                startActivity(intent);
//            }}
//        );
//
//        ButtonC.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
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
                        Intent intent = new Intent(MainActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_journeys:
                        // Handle click on "Journeys" button
                        Intent intent_journeys = new Intent((MainActivity.this),
                                MainActivity2.class);
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