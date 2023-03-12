package com.example.a1d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    Button ButtonA;
    Button ButtonB;
    Button ButtonC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ButtonA = (Button) findViewById(R.id.doneadding);
        ButtonB = (Button) findViewById(R.id.button3);
        ButtonC = (Button) findViewById(R.id.button4);

        ButtonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity4.this,MainActivity5.class);
                startActivity(intent);
            }}
        );
        ButtonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity4.this,MainActivity.class);
                startActivity(intent);
            }}
        );

        ButtonC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity4.this,MainActivity2.class);
                startActivity(intent);
            }}
        );

    }
}