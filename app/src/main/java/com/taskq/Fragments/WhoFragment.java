package com.taskq.Fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_Who;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhoFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private dBaseManager_Who dbManager_Who;
    private ListView listView;
    private taskQSettings Settings;
    private int maxProgressBar;

    public WhoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_who, container, false);
        dbManager = new dBaseManager(getActivity());
        dbManager.open();
        dbManager_Who = new dBaseManager_Who(getActivity());
        dbManager_Who.open();
        listView = view.findViewById(R.id.ListView_Frag_Who);
        Settings = new taskQSettings(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
        dbManager_Who.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList strRawTags = new ArrayList();
        ArrayList strRecurrringTagsBuffer = new ArrayList();
        ArrayList strRecurrringTags = new ArrayList();
        Set<String> hsRecurringTags = new HashSet<>();
        ArrayList strTags = new ArrayList();


        Cursor cursorWho = dbManager_Who.fetch();
        Cursor cursor;
        if(Settings.getSwitchShowCompleted() == true){
            cursor = dbManager.fetch();}
        else{
            cursor = dbManager.fetch_NoCompleted();}

        //==============================================================================================
        // ToDo: Feature - 014 List the tasks based on names
        //==============================================================================================

        //Step 1 - Get the string to each tag. Store them in a array
        //         Things can go wrong here - best to have a exception catcher
        try {
            //This for loop will go through every member of the table
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                //Get the Description string from the current entry
                strRawTags.add(cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_NAMES)));
            }
        } catch (Exception e) {
            //Catch any exceptions
            Toast.makeText(getActivity().getApplicationContext(), "taskQ Error 014" , Toast.LENGTH_LONG).show();
        }

        //Step 2 - Sort the array into recurring tags
        //
        for (int x=0; x<strRawTags.size(); x++){
            //This will give us a array list of arrays
            strRecurrringTagsBuffer.add(strRawTags.get(x).toString().split(","));
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

        //Step 4 - Delete all contents of dbManagerWho
        //
        dbManager_Who.deleteAll();
        maxProgressBar = 0;

        //Step 5 - Populate into WhatToDo_database_What the tags and occurrences
        //
        for (int x = 0; x < strTags.size(); x++) {
            //==============================================================================================
            // ToDo: Feature - 018 Only add Names that exist. Tasks with no Names are not listed.
            //==============================================================================================
            if(!strTags.get(x).toString().equals("")) {
                dbManager_Who.insert(strTags.get(x).toString(), (long) Collections.frequency(strRecurrringTags, strTags.get(x).toString()));
            }
            if( maxProgressBar < Collections.frequency(strRecurrringTags, strTags.get(x).toString())){
                maxProgressBar =  Collections.frequency(strRecurrringTags, strTags.get(x).toString());
            }
        }
        maxProgressBar = maxProgressBar + 1;


    }

















































}
