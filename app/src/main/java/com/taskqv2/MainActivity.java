package com.taskqv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_Main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
