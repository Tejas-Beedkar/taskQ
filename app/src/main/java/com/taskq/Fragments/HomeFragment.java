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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.DataBase.dBaseManager_When;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.widget.TextView.BufferType.EDITABLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private ExpandableListView expandableListView;
    private taskQSettings Settings;
    private SimpleCursorAdapter adapter;
    private ListView listView;

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

        expandableListView = view.findViewById(R.id.ListView_Frag_When_Expandable);
        Settings = new taskQSettings(getActivity().getApplicationContext());

        listView = view.findViewById(R.id.ListView_Frag_Home_Today);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        UpdateGraph();
        UpdateTodayList();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }


    private void UpdateTodayList(){

        Cursor cursorToday;
        Long lDateToday;
        Long lDateTomorrow;

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

        }catch(java.text.ParseException e) {
            e.printStackTrace();
            lDateToday = Calendar.getInstance().getTimeInMillis();
            lDateTomorrow = Calendar.getInstance().getTimeInMillis();
        }

        //Step 3 - Query Main database for items based on Due Date
        //
        if(Settings.getSwitchShowCompleted() == true){
            cursorToday = dbManager.fetchEntryByWhen(lDateToday, lDateTomorrow);
         }
        else{
            cursorToday = dbManager.fetchEntryByWhen_NoCompleted(lDateToday, lDateTomorrow);

        }

        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_userentries, cursorToday, from, to, 0);
        adapter.setViewBinder(new CustomViewBinder());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        //==========================================================================================
        //          Feature - 016
        //          Allow items to be opened from the All-Fragment
        //==========================================================================================
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the SQL Entry index as retrieved from the view.
                //TextView idTextView = view.findViewById(R.id.userEntry_listView_id);
                //long idModify = Long.parseLong(idTextView.getText().toString());
                long idModify = Long.parseLong( ((TextView)(view.findViewById(R.id.userEntry_listView_id))).getText().toString());

                //Set the ID into the taskQGlobal class and load the taskQEntryActivity
                if(true == (((taskQGlobal) getActivity().getApplication()).bSetUserEntryModify(idModify))){
                    Intent modifyIntent = new Intent(getActivity().getApplicationContext(), taskQEntryActivity.class);
                    startActivity(modifyIntent);
                }
            }
        });


    }

    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder{
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            boolean retVal = false;

            if(view.getId()==R.id.userEntry_listView_notification_image)
            {
                ImageView imageView = (ImageView) view;
                if(false == Boolean.valueOf(cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_STATUS)))) {
                    if (Boolean.valueOf(cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_SET_REMINDER))) == true) {
                        imageView.setImageResource(R.drawable.ic_reminder_on_dark);
                        retVal = true;
                    } else {
                        imageView.setImageResource(R.drawable.ic_reminder_off_dark);
                        retVal = true;
                    }
                }else{
                    imageView.setImageResource(R.drawable.ic_delete);
                    retVal = true;
                }

            }

            if(view.getId()==R.id.userEntry_listView_date)
            {
                //Declare and init vars
                Calendar cUserTimeDate = Calendar.getInstance();;
                SimpleDateFormat sdfDate_Month = new SimpleDateFormat("dd MMMM");
                SimpleDateFormat sdfHours_Minutes = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE");
                String strTimeBuffer;
                TextView textView = (TextView) view;
                //Get Time form dBase
                cUserTimeDate.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(dBaseArchitecture.COL_WHEN_TIME)));
                //Construct the time string
                strTimeBuffer = sdfDay.format(cUserTimeDate.getTime()) +", " + sdfDate_Month.format(cUserTimeDate.getTime());
                //Show string in list view
                textView.setText(strTimeBuffer, EDITABLE);
                retVal = true;
            }

            return retVal;
        }
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

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min7 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min6 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min5 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min4 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min3 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min2 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_min1 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDateToday = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls1 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls2 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls3 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls4 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls5 = calenderToday.getTimeInMillis();

            //calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls6 = calenderToday.getTimeInMillis();

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
