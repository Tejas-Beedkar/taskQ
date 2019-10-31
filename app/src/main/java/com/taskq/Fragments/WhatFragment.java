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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.customExpandableListAdapter;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseArchitecture_What;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_What;
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
public class WhatFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private dBaseManager_What dbManager_What;
    private ExpandableListView expandableListView;
    private taskQSettings Settings;
    private int maxProgressBar;
    private SimpleCursorAdapter adapter;

    //Expandable List Vars
    private HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    private HashMap<String, List<String>> expandableListIDs    = new HashMap<String, List<String>>();
    private HashMap<String, List<String>> expandableListTagCnt = new HashMap<String, List<String>>();
    private List<String>                  expandableListTags;
    private ExpandableListAdapter         expandableListAdapter;

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
        expandableListView = view.findViewById(R.id.ListView_Frag_What_Expandable);
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
        Cursor cursor;
        Cursor cursorWhat;
        Cursor cursorWhatTaskList;

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

        //Step 6 - expandableListDetail - Must be a hash of Keys = Tags and List as List<String> of Task COL_TASK
        cursorWhat = dbManager_What.fetch();
        for(cursorWhat.moveToFirst() ; !cursorWhat.isAfterLast() ; cursorWhat.moveToNext()){
            List<String> strLstTaskNameBuffer = new ArrayList<String>();
            List<String> strLstTaskIDBuffer   = new ArrayList<String>();
            List<String> strLstTaskCntBuffer  = new ArrayList<String>();

            //strCurrentTag now has the Tag Name
            String strCurrentTag = cursorWhat.getString(cursorWhat.getColumnIndex(dBaseArchitecture_What.COL_WHAT));
            //Get all Tasks that have the set tag
            if(Settings.getSwitchShowCompleted() == true){
                cursorWhatTaskList =  dbManager.fetchEntryByTag(strCurrentTag);
            }
            else{
                cursorWhatTaskList =  dbManager.fetchEntryByTag_NoCompleted(strCurrentTag);
            }

            //Step 7 - Make the HashMap of the Task Names against Tag
            //Parse the list of Tasks to get make the task Description List
            for(cursorWhatTaskList.moveToFirst() ; !cursorWhatTaskList.isAfterLast() ; cursorWhatTaskList.moveToNext()){
                String strCurrentWhatTask = cursorWhatTaskList.getString(cursorWhatTaskList.getColumnIndex(dBaseArchitecture.COL_TASK));
                strLstTaskNameBuffer.add(strCurrentWhatTask);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListDetail.put(strCurrentTag, strLstTaskNameBuffer);

            //Step 8 - Make the HashMap of the Task IDs against Tag
            //Parse the list of Tasks to get make the task ID List
            for(cursorWhatTaskList.moveToFirst() ; !cursorWhatTaskList.isAfterLast() ; cursorWhatTaskList.moveToNext()){
                String strCurrentWhatTaskID = cursorWhatTaskList.getString(cursorWhatTaskList.getColumnIndex(dBaseArchitecture._ID));
                strLstTaskIDBuffer.add(strCurrentWhatTaskID);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListIDs.put(strCurrentTag, strLstTaskIDBuffer);

            //Step 9 - Make the Hashmap of the Tags againt Tag Count. This is a simple one element value against the Tag Key.
            strLstTaskCntBuffer.add(cursorWhat.getString(cursorWhat.getColumnIndex(dBaseArchitecture_What.COL_WHAT_COUNT)));
            expandableListTagCnt.put(strCurrentTag, strLstTaskCntBuffer);

        }

        //Step 10 - Call the customExpandableListAdapter to get the list View
        expandableListTags = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new customExpandableListAdapter(getActivity(), expandableListTags, expandableListDetail, expandableListIDs, expandableListTagCnt, maxProgressBar);
        expandableListView.setAdapter(expandableListAdapter);

//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getActivity().getApplicationContext(),
//                        expandableListTags.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTags.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();

                Toast.makeText( getActivity().getApplicationContext(),
                                expandableListIDs.get(expandableListTags.get(groupPosition)).get( childPosition),
                                Toast.LENGTH_SHORT
                ).show();

                int idModify = Integer.parseInt(expandableListIDs.get(expandableListTags.get(groupPosition)).get( childPosition));
                if(true == (((taskQGlobal) getActivity().getApplication()).bSetUserEntryModify(idModify))){
                    Intent modifyIntent = new Intent(getActivity().getApplicationContext(), taskQEntryActivity.class);
                    startActivity(modifyIntent);
                }

                return false;
            }
        });

//        //Step 6 - Update Views
//        //
//        //ToDo - ExpandableListAdapter
//        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_countentries, cursorWhat, from, to, 0);
//        adapter.setViewBinder(new CustomViewBinder());
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);
//        //Step 7 - Setup On Click Listener for new items
//        //ToDo: add way to sort ascending
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView SearchCategory = view.findViewById(R.id.search_category);
//                //ToDo - Launch Query activity from here.
//            }
//        });
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
