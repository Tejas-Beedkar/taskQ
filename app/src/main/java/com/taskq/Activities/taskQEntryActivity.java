package com.taskq.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.Fragments.TagsDialogFragment;
import com.taskq.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//==========================================================================================
//          Feature - 009
//          Task entry form
//==========================================================================================
public class taskQEntryActivity extends AppCompatActivity {

    private ChipGroup TaskQEntry_Tags;
    private EditText EditText_Task;
    private ImageButton tabTaskQEntry__SetReminder;
    private ImageView imageView_done;
    private TagsDialogFragment TagsDialog;
    private taskQviewModel tagsDialogViewModel;
    private Switch tabTaskQEntry_Switch_Completion;
    private Calendar cUserTimeDate;
    private EditText EditText_Time;
    private EditText EditText_Date;
    private EditText EditText_Day;

    //NOTE - The simple act of creating a date/time object populated it to the current time.
    private SimpleDateFormat sdfDate_Month = new SimpleDateFormat("dd MMMM");
    private SimpleDateFormat sdfHours_Minutes = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE");

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

        //Feature - 002 distributed code
        setTheme(R.style.AppTheme_Main);

        EditText_Task = findViewById(R.id.tabTaskQEntry_Task_Description);
        imageView_done = findViewById(R.id.imageView_done);
        tabTaskQEntry__SetReminder = findViewById(R.id.tabTaskQEntry__SetReminder);
        tabTaskQEntry_Switch_Completion = findViewById(R.id.tabTaskQEntry_Switch_Completion);
        TaskQEntry_Tags = findViewById(R.id.tabTaskQEntry_Tags);

        //Feature - 008 distributed code - init the Calender for the time/date pickers
        cUserTimeDate = Calendar.getInstance();

        //Feature - 007 distributed code
        tagsDialogViewModel =  ViewModelProviders.of(this).get(taskQviewModel.class);
        tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            EditText_Task.setAutofillHints(getString(R.string.taskQEntry_Task_Description));
        }

        EditText_Time = findViewById(R.id.tabTaskQEntry__Time);
        EditText_Date = findViewById(R.id.tabTaskQEntry__Date);
        EditText_Day  = findViewById(R.id.tabTaskQEntry__Day);

        //==========================================================================================
        //          Feature - 010
        //          User Entry- Init of New Entry
        //==========================================================================================
        if(((taskQGlobal) getApplication()).bCheckUserEntryNew()){

            //Init the day and time.
            if(TextUtils.isEmpty(EditText_Date.getText().toString())){
                //==================================================================================
                // ToDo: Feature - 007 Populate the current time into the new entry.
                //==================================================================================
                if(cUserTimeDate.get(Calendar.MINUTE) <= 30){
                    cUserTimeDate.set(Calendar.MINUTE, 30);
                }
                else
                {
                    cUserTimeDate.set(Calendar.MINUTE,0);
                    cUserTimeDate.add(Calendar.HOUR, 1);
                }

                Date d = cUserTimeDate.getTime();
                EditText_Date.setText(sdfDate_Month.format(d));
                EditText_Time.setText(sdfHours_Minutes.format(d));
                EditText_Day.setText(sdfDay.format(d));
            }

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

    @Override
    public void onBackPressed() {
        //if(false == tagsDialogViewModel.bClearUserEntryNew()){
        if(true == ((taskQGlobal) getApplication()).bClearUserEntryNew()){
            //The flag failed to clear. Now what?
            Log.d(this.getClass().getName(), "User Entry Activity is dead locked");
        }
        super.onBackPressed();
    }



    //Feature - 007 distributed code
    public void ClipGroup_AddTags(View v){

        TagsDialog = new TagsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        TagsDialog.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        TagsDialog.show(ft, "dialog");
    }

    //Feature - 006 distributed code
    public void ClipGroup_AddNames(View v){

    }

    //Feature - 006 distributed code
    public void Button_Today(View v){

    }

    //Feature - 006 distributed code
    public void Button_Add_a_Day(View v){

    }

    //Feature - 006 distributed code
    public void Button_Weekend(View v){

    }

    //Feature - 006 distributed code
    public void Button_Sub_a_Day(View v){

    }

    //Feature - 006 distributed code
    public void Button_SetReminder(View v){

        if(true == tabTaskQEntry_Switch_Completion.isChecked()){
            Log.d(getString(R.string.app_name), "taskQEntryActivity - Delete");
        }
        else{
            if((Integer)tabTaskQEntry__SetReminder.getTag() == R.drawable.ic_reminder_on_dark){
                tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_off_dark);
                tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_off_dark);

                //The Off icon is offset by 4 points to the right. So we need to do this.
                int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
                tabTaskQEntry__SetReminder.setPadding(intPadding+4,intPadding,intPadding,intPadding);
                Log.d(getString(R.string.app_name), "taskQEntryActivity - Reminder Off");
            }
            else{
                tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_on_dark);
                tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);

                //The Off icon is offset by 4 points to the right. So we need to do this.
                int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
                tabTaskQEntry__SetReminder.setPadding(intPadding,intPadding,intPadding,intPadding);
                Log.d(getString(R.string.app_name), "taskQEntryActivity - Reminder On");
            }
        }
    }

    //Feature - 006 distributed code
    public void Switch_Completion(View v){
        if(true == tabTaskQEntry_Switch_Completion.isChecked()){
            imageView_done.setVisibility(View.VISIBLE);
            tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_delete);
        }
        else{
            imageView_done.setVisibility(View.INVISIBLE);
            if((Integer)tabTaskQEntry__SetReminder.getTag() == R.drawable.ic_reminder_on_dark){
                tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_on_dark);
                //The Off icon is offset by 4 points to the right. So we need to do this.
                int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
                tabTaskQEntry__SetReminder.setPadding(intPadding,intPadding,intPadding,intPadding);
            }
            else{
                tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_off_dark);
                //The Off icon is offset by 4 points to the right. So we need to do this.
                int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
                tabTaskQEntry__SetReminder.setPadding(intPadding+4,intPadding,intPadding,intPadding);
            }
        }
    }

    public void onDialogDismiss(){
        //finish();
        //startActivity(getIntent());

        //Feature - 007 distributed code
        for (int x=0; x<tagsDialogViewModel. strDialogTags.size(); x++){
            addChips((String)tagsDialogViewModel.strDialogTags.get(x));
            ViewGroup.LayoutParams params = TaskQEntry_Tags.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            TaskQEntry_Tags.setLayoutParams(params);
        }
        tagsDialogViewModel.strDialogTags.clear();
    }

    //Feature - 007 distributed code
    private void addChips(String addchip)
    {
        final Chip chip = new Chip(this);

        int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(addchip);
        chip.setCheckable(true);
        chip.setCloseIconVisible(true);
        chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
        chip.setTextColor(getResources().getColor(R.color.colorThemeLightGrey));

        chip.setOnCloseIconClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TaskQEntry_Tags.removeView(chip);

                if(TaskQEntry_Tags.getChildCount() == 0){
                    ViewGroup.LayoutParams params = TaskQEntry_Tags.getLayoutParams();
                    params.height = (int)getResources().getDimension(R.dimen.taskQ_dialog_tagsChipGroupDefaultHeight);
                    TaskQEntry_Tags.setLayoutParams(params);
                }

            }
        });

        TaskQEntry_Tags.addView(chip);
    }
}
