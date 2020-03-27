package com.taskq.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.taskq.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dummy Change

        // The NotificationChannel class is not supported below API 26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence strChannelName = this.getText(R.string.Channel_Name);
            String strChannelDescription =  this.getResources().getString(R.string.Channel_Description);
            int intChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chNotificationChannel = new NotificationChannel(this.getResources().getString(R.string.Channel_ID), strChannelName, intChannelImportance);
            chNotificationChannel.setDescription(strChannelDescription);

            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chNotificationChannel);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }


    @Override
    protected void onResume() {
        super.onResume();

        //==========================================================================================
        //          Feature - 002
        //          We do not want the splash screen as our background all the time.
        //==========================================================================================
        setTheme(R.style.AppTheme_Main);
        //==========================================================================================
        //          Feature - 001
        //          Load the TabActivity from the home screen
        //==========================================================================================
        Intent modifyIntent = new Intent(MainActivity.this, TabActivity.class);
        startActivity(modifyIntent);
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

}
