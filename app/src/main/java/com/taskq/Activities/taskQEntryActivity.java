package com.taskq.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.taskq.R;

public class taskQEntryActivity extends AppCompatActivity {

    private EditText EditText_Task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskq_entry);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//    }


    @Override
    protected void onResume() {
        super.onResume();

        EditText_Task = findViewById(R.id.tabTaskQEntry_Task_Description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            EditText_Task.setAutofillHints(getString(R.string.taskQEntry_Task_Description));
        }



    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    //==========================================================================================
    // ToDo: Feature - 004 Add Tags using Diaglog Fragment on click
    //==========================================================================================
    public void ClipGroup_AddTags(View v){


        Log.d(getString(R.string.app_name), "taskQ Feature - 004 tabTaskQEntry_Tags click");
    }

}
