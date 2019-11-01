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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taskq.Activities.MainActivity;
import com.taskq.Activities.TabActivity;
import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
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

    public static String strSeparator;

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

        strSeparator = getString(R.string.strSeparator);

        if(Settings.getSwitchShowCompleted() == true){
            cursor = dbManager.fetch();}
        else{
            cursor = dbManager.fetch_NoCompleted();}
        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_userentries, cursor, from, to, 0);
        adapter.setViewBinder(new AllFragment.CustomViewBinder());
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

    //Feature 14 - List of all entries - CustomViewBinder to customize each list entry
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

}
