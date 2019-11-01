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

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.customExpandableListAdapter;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseArchitecture_When;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_When;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhenFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private dBaseManager_When dBManager_When;
    private ExpandableListView expandableListView;
    private taskQSettings Settings;
    private int maxProgressBar;

    //Expandable List Vars
    //NOTE - Its very important to use LinkedHashMap. Else the order of k,v insertion is not preserved.
    private HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
    private HashMap<String, List<String>> expandableListIDs    = new LinkedHashMap<String, List<String>>();
    private HashMap<String, List<String>> expandableListDatesCnt = new LinkedHashMap<String, List<String>>();
    private List<String>                  expandableListDates;
    private ExpandableListAdapter         expandableListAdapter;

    public WhenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_when, container, false);
        dbManager = new dBaseManager(getActivity());
        dbManager.open();
        dBManager_When = new dBaseManager_When(getActivity());
        dBManager_When.open();
        expandableListView = view.findViewById(R.id.ListView_Frag_When_Expandable);
        Settings = new taskQSettings(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursorOverdue;
        Cursor cursorToday;
        Cursor cursorTomorrow;
        Cursor cursorNextSevenDays;
        Cursor cursorWhen;
        Cursor cursorWhenTaskList;

        Long lDateToday;
        Long lDateTomorrow;
        Long lDateAfterTomorrow;
        Long lDate7DaysFromToday;

        //Step 1 - Construct search queries
        //
        Calendar calenderToday = Calendar.getInstance();
        try {
            String strDateCheck =  new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            //Construct Today
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            lDateToday = calenderToday.getTimeInMillis();
            //Construct Tomorrow
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDateTomorrow = calenderToday.getTimeInMillis();
            //Construct Day After Tomorrow
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 2);
            lDateAfterTomorrow = calenderToday.getTimeInMillis();
            //Construct 7 Days From Today
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 7);
            lDate7DaysFromToday = calenderToday.getTimeInMillis();

        }catch(java.text.ParseException e) {
            e.printStackTrace();
            lDateToday = Calendar.getInstance().getTimeInMillis();
            lDateTomorrow = Calendar.getInstance().getTimeInMillis();
            lDateAfterTomorrow = Calendar.getInstance().getTimeInMillis();
            lDate7DaysFromToday = Calendar.getInstance().getTimeInMillis();
        }

        //Step 2 - Delete all contents of dBaseManagerWhen
        //
        dBManager_When.deleteAll();
        maxProgressBar = 0;

        //Step 3 - Query Main database for items based on Due Date
        //
        Cursor cursor;
        if(Settings.getSwitchShowCompleted() == true){
            cursorOverdue = dbManager.fetchEntryByWhen(0L, lDateToday);
            cursorToday = dbManager.fetchEntryByWhen(lDateToday, lDateTomorrow);
            cursorTomorrow = dbManager.fetchEntryByWhen(lDateTomorrow, lDateAfterTomorrow);
            cursorNextSevenDays = dbManager.fetchEntryByWhen(lDateAfterTomorrow, lDate7DaysFromToday);
        }
        else{
            cursorOverdue = dbManager.fetchEntryByWhen_NoCompleted(0L, lDateToday);
            cursorToday = dbManager.fetchEntryByWhen_NoCompleted(lDateToday, lDateTomorrow);
            cursorTomorrow = dbManager.fetchEntryByWhen_NoCompleted(lDateTomorrow, lDateAfterTomorrow);
            cursorNextSevenDays = dbManager.fetchEntryByWhen_NoCompleted(lDateAfterTomorrow, lDate7DaysFromToday);
        }

        //Step 4 - Scale the progress Bars
        //
        maxProgressBar = cursorOverdue.getCount();
        if(maxProgressBar < cursorToday.getCount()){
            maxProgressBar = cursorToday.getCount();}
        if(maxProgressBar < cursorTomorrow.getCount()){
            maxProgressBar = cursorTomorrow.getCount();}
        if(maxProgressBar < cursorNextSevenDays.getCount()){
            maxProgressBar = cursorNextSevenDays.getCount();}
        maxProgressBar = maxProgressBar+1;

        //Step 5 - Construct the When database
        //
        dBManager_When.insert(getString(R.string.Overdue), cursorOverdue.getCount());
        dBManager_When.insert(getString(R.string.Due_Today), cursorToday.getCount());
        dBManager_When.insert(getString(R.string.Due_Tomorrow), cursorTomorrow.getCount());
        dBManager_When.insert(getString(R.string.Due_Next_7_Days), cursorNextSevenDays.getCount());

        //Step 6 - expandableListDetail - Must be a hash of Keys = Tags and List as List<String> of Task COL_TASK
        cursorWhen = dBManager_When.fetch();
        for(cursorWhen.moveToFirst() ; !cursorWhen.isAfterLast() ; cursorWhen.moveToNext()){
            List<String> strLstTaskNameBuffer = new ArrayList<String>();
            List<String> strLstTaskIDBuffer   = new ArrayList<String>();
            List<String> strLstTaskCntBuffer  = new ArrayList<String>();

            //strCurrentTag now has the Tag Name
            String strCurrentTag = cursorWhen.getString(cursorWhen.getColumnIndex(dBaseArchitecture_When.COL_WHEN));

            //We need to pass params to fetchEntryByWhen based on current value of strCurrentTag
            if(strCurrentTag.equals(getString(R.string.Overdue))){
                if(Settings.getSwitchShowCompleted() == true){
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen(0L, lDateToday);
                }
                else{
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen_NoCompleted(0L, lDateToday);
                }
            } else if(strCurrentTag.equals(getString(R.string.Due_Today))){
                if(Settings.getSwitchShowCompleted() == true){
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen(lDateToday, lDateTomorrow);
                }
                else{
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen_NoCompleted(lDateToday, lDateTomorrow);
                }
            } else if(strCurrentTag.equals(getString(R.string.Due_Tomorrow))){
                if(Settings.getSwitchShowCompleted() == true){

                    cursorWhenTaskList =  dbManager.fetchEntryByWhen(lDateTomorrow, lDateAfterTomorrow);
                }
                else{
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen_NoCompleted(lDateTomorrow, lDateAfterTomorrow);
                }
            } else if(strCurrentTag.equals(getString(R.string.Due_Next_7_Days))){
                if(Settings.getSwitchShowCompleted() == true){

                    cursorWhenTaskList =  dbManager.fetchEntryByWhen(lDateAfterTomorrow, lDate7DaysFromToday);
                }
                else{
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen_NoCompleted(lDateAfterTomorrow, lDate7DaysFromToday);
                }
            } else{
                ;//ToDo: unreachable code!!
                if(Settings.getSwitchShowCompleted() == true){
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen(0L, lDateToday);
                }
                else{
                    cursorWhenTaskList =  dbManager.fetchEntryByWhen_NoCompleted(0L, lDateToday);
                }
            }

            //Step 7 - Make the HashMap of the Task Names against Tag
            //Parse the list of Tasks to get make the task Description List
            for(cursorWhenTaskList.moveToFirst() ; !cursorWhenTaskList.isAfterLast() ; cursorWhenTaskList.moveToNext()){
                String strCurrentWhenTask = cursorWhenTaskList.getString(cursorWhenTaskList.getColumnIndex(dBaseArchitecture.COL_TASK));
                strLstTaskNameBuffer.add(strCurrentWhenTask);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListDetail.put(strCurrentTag, strLstTaskNameBuffer);

            //Step 8 - Make the HashMap of the Task IDs against Tag
            //Parse the list of Tasks to get make the task ID List
            for(cursorWhenTaskList.moveToFirst() ; !cursorWhenTaskList.isAfterLast() ; cursorWhenTaskList.moveToNext()){
                String strCurrentWhenTask = cursorWhenTaskList.getString(cursorWhenTaskList.getColumnIndex(dBaseArchitecture._ID));
                strLstTaskIDBuffer.add(strCurrentWhenTask);
            }
            //Now we have the Tag in strCurrentTag and all Tasks with names in strLstTaskNameBuffer. Add to HashMap as Key and Value
            expandableListIDs.put(strCurrentTag, strLstTaskIDBuffer);

            //Step 9 - Make the Hashmap of the Tags againt Tag Count. This is a simple one element value against the Tag Key.
            strLstTaskCntBuffer.add(cursorWhen.getString(cursorWhen.getColumnIndex(dBaseArchitecture_When.COL_WHEN_COUNT)));
            expandableListDatesCnt.put(strCurrentTag, strLstTaskCntBuffer);
        }

        //Step 10 - Call the customExpandableListAdapter to get the list View
        expandableListDates = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new customExpandableListAdapter(getActivity(), expandableListDates, expandableListDetail, expandableListIDs, expandableListDatesCnt, maxProgressBar);
        expandableListView.setAdapter(expandableListAdapter);


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

//                Toast.makeText( getActivity().getApplicationContext(),
//                        expandableListIDs.get(expandableListTags.get(groupPosition)).get( childPosition),
//                        Toast.LENGTH_SHORT
//                ).show();

                int idModify = Integer.parseInt(expandableListIDs.get(expandableListDates.get(groupPosition)).get( childPosition));
                if(true == (((taskQGlobal) getActivity().getApplication()).bSetUserEntryModify(idModify))){
                    Intent modifyIntent = new Intent(getActivity().getApplicationContext(), taskQEntryActivity.class);
                    startActivity(modifyIntent);
                }

                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
        dBManager_When.close();
    }
}