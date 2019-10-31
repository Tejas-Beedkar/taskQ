package com.taskq.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.DataBase.dBaseManager;
import com.taskq.Fragments.TagsDialogFragment;
import com.taskq.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//==========================================================================================
//          Feature - 009
//          Task entry form
//==========================================================================================
public class taskQEntryActivity extends AppCompatActivity {

    private ChipGroup TaskQEntry_Tags;
    private ChipGroup TaskQEntry_Names;
    private EditText tabTaskQEntry_Task_Description;
    private ImageButton tabTaskQEntry__SetReminder;
    private ImageView imageView_done;
    private TagsDialogFragment TagsDialog;
    private taskQviewModel tagsDialogViewModel;
    private Switch tabTaskQEntry_Switch_Completion;
    private Calendar cUserTimeDate;
    private EditText EditText_Time;
    private EditText EditText_Date;
    private EditText EditText_Day;
    private EditText tabTaskQEntry_Description;
    private dBaseManager dbManager;
    private static boolean bSetReminder;

    //ToDo: add this to taskQGlobal
    public static String strSeparator = "__,__";

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

        tabTaskQEntry_Task_Description = findViewById(R.id.tabTaskQEntry_Task_Description);
        imageView_done = findViewById(R.id.imageView_done);
        tabTaskQEntry__SetReminder = findViewById(R.id.tabTaskQEntry__SetReminder);
        tabTaskQEntry_Switch_Completion = findViewById(R.id.tabTaskQEntry_Switch_Completion);
        TaskQEntry_Tags = findViewById(R.id.tabTaskQEntry_Tags);
        TaskQEntry_Names = findViewById(R.id.tabTaskQEntry_Who);
        tabTaskQEntry_Description = findViewById(R.id.tabTaskQEntry_Description);

        //Feature - 008 distributed code - init the Calender for the time/date pickers
        cUserTimeDate = Calendar.getInstance();

        //Feature - 007 distributed code
        tagsDialogViewModel =  ViewModelProviders.of(this).get(taskQviewModel.class);
        tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tabTaskQEntry_Task_Description.setAutofillHints(getString(R.string.taskQEntry_Task_Description));
        }

        EditText_Time = findViewById(R.id.tabTaskQEntry__Time);
        EditText_Date = findViewById(R.id.tabTaskQEntry__Date);
        EditText_Day  = findViewById(R.id.tabTaskQEntry__Day);

        //Feature 12 - Open the dBase
        dbManager = new dBaseManager(this);
        dbManager.open();

        //==========================================================================================
        //          Feature - 010
        //          User Entry- Init of New Entry
        //==========================================================================================
        if(((taskQGlobal) getApplication()).bCheckUserEntryNew()){

            //Init the day and time.
            if(TextUtils.isEmpty(EditText_Date.getText().toString())){
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

                //Feature TBU
                bSetReminder = true; //This is true because wa want to set the alarm by default
            }

        }


        //==========================================================================================
        //          Feature - 011 Allow the user to change the Date and Time the Task is Due
        //==========================================================================================
        EditText_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(taskQEntryActivity.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        cUserTimeDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                        cUserTimeDate.set(Calendar.MINUTE, selectedMinute);
                        EditText_Time.setText(sdfHours_Minutes.format(cUserTimeDate.getTime()));
                    }
                }, cUserTimeDate.get(Calendar.HOUR_OF_DAY), cUserTimeDate.get(Calendar.MINUTE), true);//Yes 24 hour time
                //mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        EditText_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(taskQEntryActivity.this,  R.style.TimePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cUserTimeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cUserTimeDate.set(Calendar.MONTH, monthOfYear);
                        cUserTimeDate.set(Calendar.YEAR, year);
                        EditText_Date.setText(sdfDate_Month.format(cUserTimeDate.getTime()));
                        EditText_Day.setText(sdfDay.format(cUserTimeDate.getTime()));
                    }
                }, cUserTimeDate.get(Calendar.YEAR), cUserTimeDate.get(Calendar.MONTH), cUserTimeDate.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });

    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//    }


    @Override
    protected void onStop() {
        super.onStop();
        //Feature 12 - Close the dBase manager
        dbManager.close();
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed() {

        //Feature - 010
        saveForm();
        //bClearUserEntryNew MUST be the last thing to be done here. Elase saveForm() will break.
        if(false == ((taskQGlobal) getApplication()).bClearUserEntryNew()){
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

    //Feature - TBU distributed code
    public void ClipGroup_AddNames(View v){

    }

    //Feature - TBU distributed code
    public void Button_Today(View v){

    }

    //Feature - TBU distributed code
    public void Button_Add_a_Day(View v){

    }

    //Feature - TBU distributed code
    public void Button_Weekend(View v){

    }

    //Feature - TBU distributed code
    public void Button_Sub_a_Day(View v){

    }

    //Feature - TBU distributed code
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

                //Feature TBU
                bSetReminder = false;
            }
            else{
                tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_on_dark);
                tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);

                //The Off icon is offset by 4 points to the right. So we need to do this.
                int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
                tabTaskQEntry__SetReminder.setPadding(intPadding,intPadding,intPadding,intPadding);
                Log.d(getString(R.string.app_name), "taskQEntryActivity - Reminder On");

                //Feature TBU
                bSetReminder = true;
            }
        }
    }

    //Feature - TBU distributed code
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

    //Feature - 007 distributed code
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

        int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -10, getResources().getDisplayMetrics());
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

    //==============================================================================================
    //      Feature - 010 Save the form to the dBaseArchitecture dBase
    //==============================================================================================
    private void saveForm(){

        String strTask = tabTaskQEntry_Task_Description.getText().toString();
        String strStatus = String.valueOf(tabTaskQEntry_Switch_Completion.isChecked());
        String strDescription = tabTaskQEntry_Description.getText().toString();
        long RowId = 0;

        //Feature - 010 - Needed to convert a list of chips to a store able SQL buffer
        ArrayList<String> alTagsBuffer = new ArrayList<String>();;
        final StringBuilder sbTags = new StringBuilder();
        String strTags;
        for (int i=0; i<TaskQEntry_Tags.getChildCount();i++){
            Chip chip = (Chip)TaskQEntry_Tags.getChildAt(i);
            sbTags.append(chip.getText());
            alTagsBuffer.add(sbTags.toString());
            sbTags.setLength(0);
        }
        strTags = convertArrayToString(alTagsBuffer);

        //Feature - 010 - Needed to convert a list of chips to a store able SQL buffer
        ArrayList<String> alNamesBuffer = new ArrayList<String>();;
        final StringBuilder sbNames = new StringBuilder();
        String strNames;
        for (int i=0; i<TaskQEntry_Names.getChildCount();i++){
            Chip chip = (Chip)TaskQEntry_Names.getChildAt(i);
            sbNames.append(chip.getText());
            alNamesBuffer.add(sbNames.toString());
            sbNames.setLength(0);
        }
        strNames = convertArrayToString(alNamesBuffer);




        //Round off seconds and milliseconds before saving.
        cUserTimeDate.set( Calendar.SECOND, 0 );
        cUserTimeDate.set( Calendar.MILLISECOND, 0 );

        //Was this a new entry?
        if(((taskQGlobal) getApplication()).bCheckUserEntryNew()){
            //Save a new entrys
            RowId = dbManager.insert(strTask, strTags, strNames, strDescription, cUserTimeDate.getTimeInMillis(), strStatus, String.valueOf(bSetReminder));
            Toast.makeText(this, "Task # " + RowId + " has been saved.", Toast.LENGTH_LONG).show();
        }
    }

    //Feature - 010 - support
    public static String convertArrayToString(ArrayList<String> array){
        String str = "";
        for (int i = 0;i<array.size(); i++) {
            str = str+array.get(i);
            // Do not append comma at the end of last element
            if(i<array.size()-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    //ToDo: MUST be fixed to support ArrayLists
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
