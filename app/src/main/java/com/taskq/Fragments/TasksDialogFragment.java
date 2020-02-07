package com.taskq.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.DialogFragment;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseArchitecture_tasks;
import com.taskq.DataBase.dBaseManager_tasks;
import com.taskq.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.widget.TextView.BufferType.EDITABLE;

public class TasksDialogFragment extends DialogFragment {

    private View view;
    private Button buttonDone;
    private Button buttonAdd;
    private int height;
    private int width;
    private dBaseManager_tasks dbManager_Tasks;
    private SimpleCursorAdapter adapter;
    private long lTaskId;
    private ListView listView;
    private EditText editText;
    private boolean bTaskNew = false;
    private long bTaskOldId = 0L;

    final String[] from = new String[] {
            dBaseArchitecture_tasks._ID,
            dBaseArchitecture_tasks.COL_TASK_STATUS,
            dBaseArchitecture_tasks.COL_TASK_DESCRIPTION,
    };
    final int[] to = new int[] {R.id.listView_tasks_id, R.id.listview_tasks_imageView_Status, R.id.listview_tasks_TextView_Description};

    public TasksDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tasks_dialog, container, false);
        getActivity().setTheme(R.style.AppTheme_MaterialTab);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor;

        //Set Dialog Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (int)(displayMetrics.heightPixels * 0.9f);
        width = (int)(displayMetrics.widthPixels * 0.9f);
        getDialog().getWindow().setLayout(width, height);
        //Make Dialog Corners Transparent
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //ToDo - Replace Tags. It's a magic number. Second instance in taskQEntryActivity
        Bundle mArgs = getArguments();
        final Long lTaskId = mArgs.getLong ("TasksID");
        //Toast.makeText(getContext(), "Task Id Received : " + lTaskId, Toast.LENGTH_LONG).show();

        //Prepopulate tasks from the tasks dBase
        dbManager_Tasks = new dBaseManager_tasks(getActivity());
        dbManager_Tasks.open();
        cursor = dbManager_Tasks.fetch_EntryById(lTaskId);
        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_tasks, cursor, from, to, 0);
        adapter.setViewBinder(new TasksDialogFragment.CustomViewBinder());
        adapter.notifyDataSetChanged();
        listView = view.findViewById(R.id.TasksDialog_ListView);
        listView.setAdapter(adapter);
        editText = view.findViewById(R.id.TasksDialog_ET_Selected);

        buttonAdd = getDialog().findViewById(R.id.TasksDialog_EntryButton);
        //Add new Tag
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String strTaskDescription = editText.getText().toString();

                if(bTaskNew ==  false){
                    dbManager_Tasks.insert(Long.toString(lTaskId), "false", strTaskDescription);
                }
                else
                {
                    bTaskNew = false;

                    bTaskOldId = 0L;
                }

                editText.setText("");

                final Cursor cursorButtonAdd = dbManager_Tasks.fetch_EntryById(lTaskId);
                adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_tasks, cursorButtonAdd, from, to, 0);
                adapter.setViewBinder(new TasksDialogFragment.CustomViewBinder());
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

            }
        });

        buttonDone = getDialog().findViewById(R.id.TasksDialog_DoneButton);

        //Return back to the taskQEntryActivity
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dbManager_Tasks.close();
                ((taskQEntryActivity)getActivity()).onTasksDialogDismiss();
                dismiss();

            }
        });

        //==========================================================================================
        //          Feature - TBD
        //          Edit task details
        //==========================================================================================
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bTaskOldId = Long.parseLong( ((TextView)(view.findViewById(R.id.listView_tasks_id))).getText().toString());
                editText.setText(((TextView)(view.findViewById(R.id.listview_tasks_TextView_Description))).getText().toString());

                switch(view.getId()) {
                    case R.id.listview_tasks_imageView_Status:

                    break;

                    case R.id.listview_tasks_TextView_Description:

                    break;

                    case R.id.listview_tasks_imageView_Delete:

                    break;

                }
            }
        });





    }

    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder{
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            boolean retVal = false;

            if(view.getId()==R.id.listview_tasks_imageView_Status)
            {
                ImageView imageView = (ImageView) view;
                if (Boolean.valueOf(cursor.getString(cursor.getColumnIndex(dBaseArchitecture_tasks.COL_TASK_STATUS))) == true) {
                    imageView.setImageResource(R.drawable.ic_task_done);
                    retVal = true;
                } else {
                    imageView.setImageResource(R.drawable.ic_task_not_done);
                    retVal = true;
                }
            }

            if(view.getId()==R.id.listview_tasks_TextView_Description)
            {
                TextView textView = (TextView) view;
                String strBuffer = cursor.getString(cursor.getColumnIndex(dBaseArchitecture_tasks.COL_TASK_DESCRIPTION));
                textView.setText(strBuffer);
                //textView.setText(strBuffer, EDITABLE);
                retVal = true;
            }

            return retVal;
        }
    }

}
