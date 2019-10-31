package com.taskq.Fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.widget.TextView.BufferType.EDITABLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;
    private ListView listView;
    private taskQSettings Settings;
    private SimpleCursorAdapter adapter;

    //ToDo: add this to taskQGlobal
    public static String strSeparator = "__,__";

    //Feature 14 - List of all entries - components
    final String[] from = new String[] {
            dBaseArchitecture._ID,
            dBaseArchitecture.COL_TASK,
            dBaseArchitecture.COL_WHEN_TIME,
            dBaseArchitecture.COL_SET_REMINDER,
    };
    final int[] to = new int[] {R.id.userEntry_listView_id, R.id.userEntry_listView_task, R.id.userEntry_listView_date, R.id.userEntry_listView_notification_image};


    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_all, container, false);

        //Feature 14 - List of all entries - components
        dbManager = new dBaseManager(getActivity());
        dbManager.open();
        listView = view.findViewById(R.id.ListView_Frag_All);

        //Feature 15 - Hide closed items (get the current setting)
        Settings = new taskQSettings(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor;
        if(Settings.getSwitchShowCompleted() == true){
            cursor = dbManager.fetch();}
        else{
            cursor = dbManager.fetch_NoCompleted();}
        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_userentries, cursor, from, to, 0);
        adapter.setViewBinder(new AllFragment.CustomViewBinder());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    //Feature 14 - List of all entries - CustomViewBinder to customize each list entry`
    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder{
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            boolean retVal = false;

            if(view.getId()==R.id.userEntry_listView_notification_image)
            {
                ImageView imageView = (ImageView) view;
                if(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_SET_REMINDER))) == true){
                    imageView.setImageResource(R.drawable.ic_reminder_on_dark);
                    retVal = true;
                }
                else
                {
                    imageView.setImageResource(R.drawable.ic_reminder_off_dark);
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

}
