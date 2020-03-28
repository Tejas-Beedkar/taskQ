package com.taskq.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.R;
import com.taskq.Settings.taskQSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.widget.TextView.BufferType.EDITABLE;

public class HomeFragment_Plus_5 extends Fragment {
    private View view;
    private taskQviewModel tagsDialogViewModel;
    private ListView listView;
    private taskQSettings Settings;
    private dBaseManager dbManager;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {
            dBaseArchitecture._ID,
            dBaseArchitecture.COL_TASK,
            dBaseArchitecture.COL_WHEN_TIME,
            dBaseArchitecture.COL_SET_REMINDER,
    };
    final int[] to = new int[] {R.id.userEntry_listView_id, R.id.userEntry_listView_task, R.id.userEntry_listView_date, R.id.userEntry_listView_notification_image};


    public HomeFragment_Plus_5() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_plus_5, container, false); //EDIT REQUIRED

        tagsDialogViewModel =  ViewModelProviders.of(getActivity()).get(taskQviewModel.class);

        Settings = new taskQSettings(getActivity().getApplicationContext());

        dbManager = new dBaseManager(getActivity());
        dbManager.open();

        listView = view.findViewById(R.id.ListView_Home_Plus5); // EDIT REQUIRED

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(tagsDialogViewModel != null){
                tagsDialogViewModel.setMainPlus5(); // EDIT REQUIRED
            }
        }
        else {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //EDIT - ADD
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    //EDIT - ADD
    @Override
    public void onResume() {
        super.onResume();

        UpdateList();
    }

    //EDIT - ADD
    private void UpdateList(){

        Cursor cursorToday;
        Long lDateStart;
        Long lDateEnd;

        // EDIT REQUIRED
        //Step 1 - Construct search queries
        //
        Calendar calenderToday = Calendar.getInstance();
        try {
            String strDateCheck =  new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            //Construct Start
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 5); // EDIT REQUIRED
            lDateStart = calenderToday.getTimeInMillis();
            //Construct End
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 6); // EDIT REQUIRED
            lDateEnd = calenderToday.getTimeInMillis();

        }catch(java.text.ParseException e) {
            e.printStackTrace();
            lDateStart = Calendar.getInstance().getTimeInMillis();
            lDateEnd = Calendar.getInstance().getTimeInMillis();
        }

        //Step 3 - Query Main database for items based on Due Date
        //
        if(Settings.getSwitchShowCompleted() == true){
            cursorToday = dbManager.fetchEntryByWhen(lDateStart, lDateEnd);
        }
        else{
            cursorToday = dbManager.fetchEntryByWhen_NoCompleted(lDateStart, lDateEnd);

        }

        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_userentries, cursorToday, from, to, 0);
        adapter.setViewBinder(new HomeFragment_Plus_5.CustomViewBinder());// EDIT REQUIRED
        adapter.notifyDataSetChanged();
        if(adapter != null) {
            listView.setAdapter(adapter);
        }

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
}
