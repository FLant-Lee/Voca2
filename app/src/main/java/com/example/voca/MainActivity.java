package com.example.voca;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button vocaAdd;
    Button vocaList;
    Button adf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vocaAdd = (Button) findViewById(R.id.VocaAdd);
        Button vocaList = (Button) findViewById(R.id.VocaFind);

        vocaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });


        vocaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(intent);
            }
        });
    }


}