package com.taskq.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.taskq.Activities.TabActivity;
import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.TextView.BufferType.EDITABLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private taskQSettings Settings;
    private TabLayout tab_TabLayout;
    private ViewPager tab_ViewPager;
    private taskQviewModel tagsDialogViewModel;

    private TextView HomeFrag_pbar_Text_Minus_7;
    private TextView HomeFrag_pbar_Text_Minus_6;
    private TextView HomeFrag_pbar_Text_Minus_5;
    private TextView HomeFrag_pbar_Text_Minus_4;
    private TextView HomeFrag_pbar_Text_Minus_3;
    private TextView HomeFrag_pbar_Text_Minus_2;
    private TextView HomeFrag_pbar_Text_Minus_1;
    private TextView HomeFrag_pbar_Text_Today;
    private TextView HomeFrag_pbar_Text_Plus_1;
    private TextView HomeFrag_pbar_Text_Plus_2;
    private TextView HomeFrag_pbar_Text_Plus_3;
    private TextView HomeFrag_pbar_Text_Plus_4;
    private TextView HomeFrag_pbar_Text_Plus_5;
    private TextView HomeFrag_pbar_Text_Plus_6;
    private TextView HomeFrag_pbar_Text_Plus_7;


    final String[] from = new String[] {
            dBaseArchitecture._ID,
            dBaseArchitecture.COL_TASK,
            dBaseArchitecture.COL_WHEN_TIME,
            dBaseArchitecture.COL_SET_REMINDER,
    };
    final int[] to = new int[] {R.id.userEntry_listView_id, R.id.userEntry_listView_task, R.id.userEntry_listView_date, R.id.userEntry_listView_notification_image};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        dbManager = new dBaseManager(getActivity());
        dbManager.open();

        Settings = new taskQSettings(getActivity().getApplicationContext());

        tab_TabLayout = view.findViewById(R.id.tablayout_home);
        tab_ViewPager = view.findViewById(R.id.viewpager_home);
        setupViewPager(tab_ViewPager);
        tab_TabLayout.setupWithViewPager(tab_ViewPager);

        tab_ViewPager.setCurrentItem( 7, false);

        tagsDialogViewModel = ViewModelProviders.of(getActivity()).get(taskQviewModel.class);

        tagsDialogViewModel.tab_ViewPager = tab_ViewPager;
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_7 = view.findViewById(R.id.HomeFrag_pbar_Text_1);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_6 = view.findViewById(R.id.HomeFrag_pbar_Text_2);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_5 = view.findViewById(R.id.HomeFrag_pbar_Text_3);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_4 = view.findViewById(R.id.HomeFrag_pbar_Text_4);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_3 = view.findViewById(R.id.HomeFrag_pbar_Text_5);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_2 = view.findViewById(R.id.HomeFrag_pbar_Text_6);
        tagsDialogViewModel.HomeFrag_pbar_Text_Min_1 = view.findViewById(R.id.HomeFrag_pbar_Text_7);
        tagsDialogViewModel.HomeFrag_pbar_Text_Today = view.findViewById(R.id.HomeFrag_pbar_Text_8);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_1 = view.findViewById(R.id.HomeFrag_pbar_Text_9);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_2 = view.findViewById(R.id.HomeFrag_pbar_Text_10);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_3 = view.findViewById(R.id.HomeFrag_pbar_Text_11);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_4 = view.findViewById(R.id.HomeFrag_pbar_Text_12);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_5 = view.findViewById(R.id.HomeFrag_pbar_Text_13);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_6 = view.findViewById(R.id.HomeFrag_pbar_Text_14);
        tagsDialogViewModel.HomeFrag_pbar_Text_Pls_7 = view.findViewById(R.id.HomeFrag_pbar_Text_15);

        HomeFrag_pbar_Text_Minus_7 = view.findViewById(R.id.HomeFrag_pbar_Text_1);
        HomeFrag_pbar_Text_Minus_6 = view.findViewById(R.id.HomeFrag_pbar_Text_2);
        HomeFrag_pbar_Text_Minus_5 = view.findViewById(R.id.HomeFrag_pbar_Text_3);
        HomeFrag_pbar_Text_Minus_4 = view.findViewById(R.id.HomeFrag_pbar_Text_4);
        HomeFrag_pbar_Text_Minus_3 = view.findViewById(R.id.HomeFrag_pbar_Text_5);
        HomeFrag_pbar_Text_Minus_2 = view.findViewById(R.id.HomeFrag_pbar_Text_6);
        HomeFrag_pbar_Text_Minus_1 = view.findViewById(R.id.HomeFrag_pbar_Text_7);
        HomeFrag_pbar_Text_Today = view.findViewById(R.id.HomeFrag_pbar_Text_8);
        HomeFrag_pbar_Text_Plus_1 = view.findViewById(R.id.HomeFrag_pbar_Text_9);
        HomeFrag_pbar_Text_Plus_2 = view.findViewById(R.id.HomeFrag_pbar_Text_10);
        HomeFrag_pbar_Text_Plus_3 = view.findViewById(R.id.HomeFrag_pbar_Text_11);
        HomeFrag_pbar_Text_Plus_4 = view.findViewById(R.id.HomeFrag_pbar_Text_12);
        HomeFrag_pbar_Text_Plus_5 = view.findViewById(R.id.HomeFrag_pbar_Text_13);
        HomeFrag_pbar_Text_Plus_6 = view.findViewById(R.id.HomeFrag_pbar_Text_14);
        HomeFrag_pbar_Text_Plus_7 = view.findViewById(R.id.HomeFrag_pbar_Text_15);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        HomeFragment.ViewPagerAdapter adapter = new HomeFragment.ViewPagerAdapter(getFragmentManager());

        adapter.addFrag(new HomeFragment_Minus_7(), "-7");
        adapter.addFrag(new HomeFragment_Minus_6(), "-6");
        adapter.addFrag(new HomeFragment_Minus_5(), "-5");
        adapter.addFrag(new HomeFragment_Minus_4(), "-4");
        adapter.addFrag(new HomeFragment_Minus_3(), "-3");
        adapter.addFrag(new HomeFragment_Minus_2(), "-2");
        adapter.addFrag(new HomeFragment_Minus_1(), "-1");
        adapter.addFrag(new HomeFragment_Today(), "T");
        adapter.addFrag(new HomeFragment_Plus_1(), "+1");
        adapter.addFrag(new HomeFragment_Plus_2(), "+2");
        adapter.addFrag(new HomeFragment_Plus_3(), "+3");
        adapter.addFrag(new HomeFragment_Plus_4(), "+4");
        adapter.addFrag(new HomeFragment_Plus_5(), "+5");
        adapter.addFrag(new HomeFragment_Plus_6(), "+6");
        adapter.addFrag(new HomeFragment_Plus_7(), "+7");

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


    @Override
    public void onResume() {
        super.onResume();

        UpdateGraph();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    private void UpdateGraph(){

        Long lDate_min8;
        Long lDate_min7;
        Long lDate_min6;
        Long lDate_min5;
        Long lDate_min4;
        Long lDate_min3;
        Long lDate_min2;
        Long lDate_min1;
        Long lDateToday;
        Long lDate_pls1;
        Long lDate_pls2;
        Long lDate_pls3;
        Long lDate_pls4;
        Long lDate_pls5;
        Long lDate_pls6;
        Long lDate_pls7;


        int maxProgress;
        int curProgress[] = new int[15];

        Cursor cursor;

        //Create progress bar handlers
        ProgressBar bar_min7 = view.findViewById(R.id.HomeFrag_pbar_1);
        ProgressBar bar_min6 = view.findViewById(R.id.HomeFrag_pbar_2);
        ProgressBar bar_min5 = view.findViewById(R.id.HomeFrag_pbar_3);
        ProgressBar bar_min4 = view.findViewById(R.id.HomeFrag_pbar_4);
        ProgressBar bar_min3 = view.findViewById(R.id.HomeFrag_pbar_5);
        ProgressBar bar_min2 = view.findViewById(R.id.HomeFrag_pbar_6);
        ProgressBar bar_min1 = view.findViewById(R.id.HomeFrag_pbar_7);
        ProgressBar bar_today = view.findViewById(R.id.HomeFrag_pbar_8);
        ProgressBar bar_pls1 = view.findViewById(R.id.HomeFrag_pbar_9);
        ProgressBar bar_pls2 = view.findViewById(R.id.HomeFrag_pbar_10);
        ProgressBar bar_pls3 = view.findViewById(R.id.HomeFrag_pbar_11);
        ProgressBar bar_pls4 = view.findViewById(R.id.HomeFrag_pbar_12);
        ProgressBar bar_pls5 = view.findViewById(R.id.HomeFrag_pbar_13);
        ProgressBar bar_pls6 = view.findViewById(R.id.HomeFrag_pbar_14);
        ProgressBar bar_pls7 = view.findViewById(R.id.HomeFrag_pbar_15);


        //Step 1 - Construct search queries
        //
        Calendar calenderToday = Calendar.getInstance();
        try {
            String strDateCheck =  new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));

            calenderToday.add(Calendar.DATE, -7);
            lDate_min8 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_7.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_7.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_7.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min7 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_6.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_6.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_6.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min6 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_5.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_5.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_5.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min5 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_4.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_4.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_4.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min4 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_3.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_3.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_3.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min3 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_2.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_2.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_2.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min2 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Minus_1.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Minus_1.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Minus_1.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min1 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Today.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Today.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Today.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDateToday = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_1.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_1.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_1.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls1 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_2.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_2.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_2.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls2 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_3.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_3.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_3.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls3 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_4.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_4.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_4.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls4 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_5.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_5.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_5.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls5 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_6.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH))+" ");
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_6.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_6.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls6 = calenderToday.getTimeInMillis();
            HomeFrag_pbar_Text_Plus_7.setText(Integer.toString(calenderToday.get(Calendar.DAY_OF_MONTH)));
            if (calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calenderToday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                HomeFrag_pbar_Text_Plus_7.setTextColor(Color.RED);
            }else{
                HomeFrag_pbar_Text_Plus_7.setTextColor(Color.BLACK);
            }

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls7 = calenderToday.getTimeInMillis();

        }catch(java.text.ParseException e) {
            e.printStackTrace();
            lDate_min8 = Calendar.getInstance().getTimeInMillis();
            lDate_min7 = Calendar.getInstance().getTimeInMillis();
            lDate_min6 = Calendar.getInstance().getTimeInMillis();
            lDate_min5 = Calendar.getInstance().getTimeInMillis();
            lDate_min4 = Calendar.getInstance().getTimeInMillis();
            lDate_min3 = Calendar.getInstance().getTimeInMillis();
            lDate_min2 = Calendar.getInstance().getTimeInMillis();
            lDate_min1 = Calendar.getInstance().getTimeInMillis();
            lDateToday = Calendar.getInstance().getTimeInMillis();
            lDate_pls1 = Calendar.getInstance().getTimeInMillis();
            lDate_pls2 = Calendar.getInstance().getTimeInMillis();
            lDate_pls3 = Calendar.getInstance().getTimeInMillis();
            lDate_pls4 = Calendar.getInstance().getTimeInMillis();
            lDate_pls5 = Calendar.getInstance().getTimeInMillis();
            lDate_pls6 = Calendar.getInstance().getTimeInMillis();
            lDate_pls7 = Calendar.getInstance().getTimeInMillis();
        }

        //Step 2 - make the counts


        //min 7
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min8, lDate_min7);
        curProgress[0] = cursor.getCount();

        //min 6
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min7, lDate_min6);
        curProgress[1] = cursor.getCount();

        //min 5
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min6, lDate_min5);
        curProgress[2] = cursor.getCount();

        //min 4
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min5, lDate_min4);
        curProgress[3] = cursor.getCount();

        //min 3
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min4, lDate_min3);
        curProgress[4] = cursor.getCount();

        //min 2
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min3, lDate_min2);
        curProgress[5] = cursor.getCount();

        //min 1
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min2, lDate_min1);
        curProgress[6] = cursor.getCount();

        //Today
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min1, lDateToday);
        curProgress[7] = cursor.getCount();

        //pls 1
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDateToday, lDate_pls1);
        curProgress[8] = cursor.getCount();

        //pls 2
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls1, lDate_pls2);
        curProgress[9] = cursor.getCount();

        //pls 3
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls2, lDate_pls3);
        curProgress[10] = cursor.getCount();

        //pls 4
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls3, lDate_pls4);
        curProgress[11] = cursor.getCount();

        //pls 5
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls4, lDate_pls5);
        curProgress[12] = cursor.getCount();

        //pls 6
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls5, lDate_pls6);
        curProgress[13] = cursor.getCount();

        //pls 7
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_pls6, lDate_pls7);
        curProgress[14] = cursor.getCount();


        //Get max bar
        maxProgress = 7;
//        for (int i = 1; i < curProgress.length; i++) {
//            if (curProgress[i] > maxProgress) {
//                maxProgress = curProgress[i];
//            }
//        }

        bar_min7.setMax(maxProgress);
        bar_min7.setProgress(curProgress[0]);

        bar_min6.setMax(maxProgress);
        bar_min6.setProgress(curProgress[1]);

        bar_min5.setMax(maxProgress);
        bar_min5.setProgress(curProgress[2]);

        bar_min4.setMax(maxProgress);
        bar_min4.setProgress(curProgress[3]);

        bar_min3.setMax(maxProgress);
        bar_min3.setProgress(curProgress[4]);

        bar_min2.setMax(maxProgress);
        bar_min2.setProgress(curProgress[5]);

        bar_min1.setMax(maxProgress);
        bar_min1.setProgress(curProgress[6]);

        bar_today.setMax(maxProgress);
        bar_today.setProgress(curProgress[7]);

        bar_pls1.setMax(maxProgress);
        bar_pls1.setProgress(curProgress[8]);

        bar_pls2.setMax(maxProgress);
        bar_pls2.setProgress(curProgress[9]);

        bar_pls3.setMax(maxProgress);
        bar_pls3.setProgress(curProgress[10]);

        bar_pls4.setMax(maxProgress);
        bar_pls4.setProgress(curProgress[11]);

        bar_pls5.setMax(maxProgress);
        bar_pls5.setProgress(curProgress[12]);

        bar_pls6.setMax(maxProgress);
        bar_pls6.setProgress(curProgress[13]);

        bar_pls7.setMax(maxProgress);
        bar_pls7.setProgress(curProgress[14]);

        return;
    }

}
