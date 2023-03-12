package com.example.a1d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    Button ButtonA;
    Button ButtonB;
    Button ButtonC;
    Button ButtonD;
    Button ButtonE;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtView = (TextView) findViewById(R.id.name2);
        ButtonA = (Button) findViewById(R.id.journey2);
        ButtonB = (Button) findViewById(R.id.button3);
        ButtonC = (Button) findViewById(R.id.button4);
        ButtonD = (Button) findViewById(R.id.journeys1);
        ButtonE = (Button) findViewById(R.id.journeys2);
        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }}
        );

        ButtonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }}
        );

        ButtonC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this,MainActivity2.class);
                startActivity(intent);
            }}
        );

        ButtonD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this,MainActivity5.class);
                startActivity(intent);
            }}
        );

        ButtonE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this,MainActivity5.class);
                startActivity(intent);
            }}
        );
    }
}