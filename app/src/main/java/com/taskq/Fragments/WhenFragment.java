package com.taskq.Fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_When;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhenFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private dBaseManager_When dBManager_When;
    private ListView listView;
    private taskQSettings Settings;
    private int maxProgressBar;

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
        listView = view.findViewById(R.id.ListView_Frag_When);
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

        //Step 6 - Update Views
        //



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
        dBManager_When.close();
    }
}