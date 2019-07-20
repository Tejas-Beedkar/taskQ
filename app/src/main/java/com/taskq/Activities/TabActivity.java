package com.taskq.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.taskq.Fragments.AllFragment;
import com.taskq.Fragments.WhatFragment;
import com.taskq.Fragments.WhenFragment;
import com.taskq.Fragments.WhoFragment;
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
        setupViewPager(tab_ViewPager);

        tab_TabLayout = findViewById(R.id.tab_TabLayout);
        tab_TabLayout.setupWithViewPager(tab_ViewPager);
        setupTabIcons();

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


    private void setupTabIcons() {
        tab_TabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tab_TabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tab_TabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tab_TabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllFragment(), "ALL");
        adapter.addFrag(new WhatFragment(), "WHAT");
        adapter.addFrag(new WhenFragment(), "WHEN");
        adapter.addFrag(new WhoFragment(), "WHO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
