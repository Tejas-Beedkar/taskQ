package com.taskq.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseArchitecture_What;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_What;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhatFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private dBaseManager_What dbManager_What;
    private ListView listView;
    private taskQSettings Settings;
    private int maxProgressBar;
    private SimpleCursorAdapter adapter;
    //ToDo: add this to taskQGlobal
    public static String strSeparator = "__,__";

    final String[] from = new String[] {
            dBaseArchitecture_What.COL_WHAT_COUNT,
            dBaseArchitecture_What.COL_WHAT,
            dBaseArchitecture_What.COL_WHAT_COUNT,
    };
    final int[] to = new int[] {R.id.search_progressBar, R.id.search_category, R.id.search_count};

    public WhatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_what, container, false);
        dbManager = new dBaseManager(getActivity());
        dbManager.open();
        dbManager_What = new dBaseManager_What(getActivity());
        dbManager_What.open();
        listView = view.findViewById(R.id.ListView_Frag_What);
        Settings = new taskQSettings(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
        dbManager_What.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList strRawTags = new ArrayList();
        ArrayList strRecurrringTagsBuffer = new ArrayList();
        ArrayList strRecurrringTags = new ArrayList();
        Set<String> hsRecurringTags = new HashSet<>();
        ArrayList strTags = new ArrayList();

        Cursor cursorWhat = dbManager_What.fetch();
        Cursor cursor;
        if (Settings.getSwitchShowCompleted() == true) {
            cursor = dbManager.fetch();
        } else {
            cursor = dbManager.fetch_NoCompleted();
        }

        //==============================================================================================
        // ToDo: Feature - 018 Create a dBase of only Tags
        //==============================================================================================

        //Step 1 - Get the string to each tag. Store them in a array
        //         Things can go wrong here - best to have a exception catcher
        try {
            //This for loop will go through every member of the table
            for(cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()){
                //Get the Description string from the current entry
                strRawTags.add(cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_TAGS)));
            }
        } catch (Exception e) {
            //Catch any exceptions
            Toast.makeText(getActivity().getApplicationContext(), "taskQ Error 013" , Toast.LENGTH_LONG).show();
        }

        //Step 2 - Sort the array into recurring tags
        //
        for (int x=0; x<strRawTags.size(); x++){
            //This will give us a array list of arrays
            strRecurrringTagsBuffer.add(strRawTags.get(x).toString().split(strSeparator));
        }
        for (int x=0; x<strRecurrringTagsBuffer.size(); x++) {
            //Local Buffer to dereference Array List
            String[] strTest = (String[]) strRecurrringTagsBuffer.get(x);
            //Now split the array
            for(int y=0; y<strTest.length ; y++){
                //Add to Array List Buffer and trim leading-lagging spaces
                strRecurrringTags.add(strTest[y].trim());
            }
        }

        //Step 3 - Sort the array into non recurring tags
        //
        hsRecurringTags.addAll(strRecurrringTags);
        strTags.addAll(hsRecurringTags);
        Collections.sort(strTags);

        //Step 4 - Delete all contents of dbManagerWhat
        //
        dbManager_What.deleteAll();
        maxProgressBar = 0;

        //Step 5 - Populate into WhatToDo_database_What the tags and occurrences
        //
        for (int x = 0; x < strTags.size(); x++) {
            //==============================================================================================
            // ToDo: Feature - 017 Only add Tags that exist. Tasks with no tags are not listed.
            //==============================================================================================
            if(!strTags.get(x).toString().equals("")){
                dbManager_What.insert(strTags.get(x).toString(), (long)Collections.frequency(strRecurrringTags, strTags.get(x).toString()));
            }
            if( maxProgressBar < Collections.frequency(strRecurrringTags, strTags.get(x).toString())){
                maxProgressBar =  Collections.frequency(strRecurrringTags, strTags.get(x).toString());
            }
        }
        maxProgressBar = maxProgressBar + 1;

        //Step 6 - Update Views
        //

        //ToDo - ExpandableListAdapter
        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_countentries, cursorWhat, from, to, 0);
        adapter.setViewBinder(new CustomViewBinder());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        //Step 7 - Setup On Click Listener for new items
        //ToDo: add way to sort ascending
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView SearchCategory = view.findViewById(R.id.search_category);
                //ToDo - Launch Query activity from here.
            }
        });
    }

    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder{
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if(view.getId()==R.id.search_progressBar)
            {
                ProgressBar progress = (ProgressBar) view;
                progress.setMax(maxProgressBar);
                progress.setProgress(cursor.getInt(cursor.getColumnIndex(dBaseArchitecture_What.COL_WHAT_COUNT)));
                progress.setScaleY(3f);
                return true;
            }
            return false;
        }
    }
}
