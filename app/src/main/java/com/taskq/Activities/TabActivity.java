package com.taskq.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.taskq.R;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private Toolbar tab_Toolbar;
    private TabLayout tab_TabLayout;
    private ViewPager tab_ViewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_all_light,
            R.drawable.ic_tab_what_light,
            R.drawable.ic_tab_when_light,
            R.drawable.ic_tab_who_light
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tab_Toolbar = findViewById(R.id.tab_toolbar);
        setSupportActionBar(tab_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tab_ViewPager = findViewById(R.id.tab_ViewPager);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }


}
