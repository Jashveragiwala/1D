package com.example.a1d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Button ButtonA;
    Button ButtonB;
    Button ButtonC;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.name);
        ButtonA = (Button) findViewById(R.id.button2);
        ButtonB = (Button) findViewById(R.id.button3);
        ButtonC = (Button) findViewById(R.id.button4);

        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }}
        );

        ButtonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }}
        );

        ButtonC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }}
        );

    }
}