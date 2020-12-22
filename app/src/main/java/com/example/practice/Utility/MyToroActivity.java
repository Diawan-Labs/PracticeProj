package com.example.practice.Utility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.practice.R;

import im.ene.toro.widget.Container;

public class MyToroActivity extends AppCompatActivity {

    Container container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_toro);
        container = findViewById(R.id.player_container);
    }
}