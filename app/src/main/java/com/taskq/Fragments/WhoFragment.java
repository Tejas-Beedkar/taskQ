package com.taskq.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.customExpandableListAdapter;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseArchitecture_Who;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_Who;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    private ExpandableListView expandableListView;

    //Expandable List Vars
//    private HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
//    private HashMap<String, List<String>> expandableListIDs    = new HashMap<String, List<String>>();
//    private HashMap<String, List<String>> expandableListTagCnt = new HashMap<String, List<String>>();
//    private List<String>                  expandableListTags;
//    private ExpandableListAdapter expandableListAdapter;
    public static String                  strSeparator;

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
        Settings = new taskQSettings(getActivity().getApplicationContext());
        expandableListView = view.findViewById(R.id.ListView_Frag_Who_Expandable);
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

        strSeparator = getString(R.string.strSeparator);
        ArrayList strRawTags = new ArrayList();
        ArrayList strRecurrringTagsBuffer = new ArrayList();
        ArrayList strRecurrringTags = new ArrayList();
        Set<String> hsRecurringTags = new HashSet<>();
        ArrayList strTags = new ArrayList();
        Cursor cursor;
        Cursor cursorWho = dbManager_Who.fetch();
        Cursor cursorWhoTaskList;

        //Expandable List Vars
        //Moving this here allows immidiate effect of "dont't show completed tasks"
        //If globals, the last value is preserved and override the effect of "dont't show completed tasks"
        final HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        final HashMap<String, List<String>> expandableListIDs    = new HashMap<String, List<String>>();
        final HashMap<String, List<String>> expandableListTagCnt = new HashMap<String, List<String>>();
        final List<String>                  expandableListTags;
        final ExpandableListAdapter         expandableListAdapter;

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

        //Step 6 - expandableListDetail - Must be a hash of Keys = Tags and List as List<String> of Task COL_TASK
        cursorWho = dbManager_Who.fetch();
        for(cursorWho.moveToFirst() ; !cursorWho.isAfterLast() ; cursorWho.moveToNext()){
            List<String> strLstTaskNameBuffer = new ArrayList<String>();
            List<String> strLstTaskIDBuffer   = new ArrayList<String>();
            List<String> strLstTaskCntBuffer  = new ArrayList<String>();

            //strCurrentTag now has the Tag Name
            String strCurrentTag = cursorWho.getString(cursorWho.getColumnIndex(dBaseArchitecture_Who.COL_WHO));
            //Get all Tasks that have the set tag
            if(Settings.getSwitchShowCompleted() == true){
                cursorWhoTaskList =  dbManager.fetchEntryByName(strCurrentTag);
            }
            else{
                cursorWhoTaskList =  dbManager.fetchEntryByName_NoCompleted(strCurrentTag);
            }

            //Step 7 - Make the HashMap of the Task Names against Tag
            //Parse the list of Tasks to get make the task Description List
            for(cursorWhoTaskList.moveToFirst() ; !cursorWhoTaskList.isAfterLast() ; cursorWhoTaskList.moveToNext()){
                String strCurrentWhatTask = cursorWhoTaskList.getString(cursorWhoTaskList.getColumnIndex(dBaseArchitecture.COL_TASK));
                strLstTaskNameBuffer.add(strCurrentWhatTask);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListDetail.put(strCurrentTag, strLstTaskNameBuffer);

            //Step 8 - Make the HashMap of the Task IDs against Tag
            //Parse the list of Tasks to get make the task ID List
            for(cursorWhoTaskList.moveToFirst() ; !cursorWhoTaskList.isAfterLast() ; cursorWhoTaskList.moveToNext()){
                String strCurrentWhatTaskID = cursorWhoTaskList.getString(cursorWhoTaskList.getColumnIndex(dBaseArchitecture._ID));
                strLstTaskIDBuffer.add(strCurrentWhatTaskID);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListIDs.put(strCurrentTag, strLstTaskIDBuffer);

            //Step 9 - Make the Hashmap of the Tags againt Tag Count. This is a simple one element value against the Tag Key.
            strLstTaskCntBuffer.add(cursorWho.getString(cursorWho.getColumnIndex(dBaseArchitecture_Who.COL_WHO_COUNT)));
            expandableListTagCnt.put(strCurrentTag, strLstTaskCntBuffer);
        }

        //Step 10 - Call the customExpandableListAdapter to get the list View
        expandableListTags = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new customExpandableListAdapter(getActivity(), expandableListTags, expandableListDetail, expandableListIDs, expandableListTagCnt, maxProgressBar);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                int idModify = Integer.parseInt(expandableListIDs.get(expandableListTags.get(groupPosition)).get( childPosition));
                if(true == (((taskQGlobal) getActivity().getApplication()).bSetUserEntryModify(idModify))){
                    Intent modifyIntent = new Intent(getActivity().getApplicationContext(), taskQEntryActivity.class);
                    startActivity(modifyIntent);
                }
                return false;
            }
        });
    }

















































}
