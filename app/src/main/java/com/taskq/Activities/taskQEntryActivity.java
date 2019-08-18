package com.taskq.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.Fragments.TagsDialogFragment;
import com.taskq.R;


public class taskQEntryActivity extends AppCompatActivity {

    private EditText EditText_Task;
    private TagsDialogFragment TagsDialog;
    private taskQviewModel tagsDialogViewModel;
    private ChipGroup TaskQEntry_Tags;
    private ImageButton tabTaskQEntry__SetReminder;
    private Switch tabTaskQEntry_Switch_Completion;
    private ImageView imageView_done;

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
        tagsDialogViewModel =  ViewModelProviders.of(this).get(taskQviewModel.class);
        tabTaskQEntry__SetReminder = findViewById(R.id.tabTaskQEntry__SetReminder);
        tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);
        tabTaskQEntry_Switch_Completion = findViewById(R.id.tabTaskQEntry_Switch_Completion);
        TaskQEntry_Tags = findViewById(R.id.tabTaskQEntry_Tags);
        imageView_done = findViewById(R.id.imageView_done);

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

        Log.d(getString(R.string.app_name), "taskQ Feature - 004 tabTaskQEntry_Tags click");
    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void ClipGroup_AddNames(View v){

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void Button_Today(View v){

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void Button_Add_a_Day(View v){

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void Button_Weekend(View v){

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void Button_Sub_a_Day(View v){

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
    public void Button_SetReminder(View v){

        if((Integer)tabTaskQEntry__SetReminder.getTag() == R.drawable.ic_reminder_on_dark){
            tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_off_dark);
            tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_off_dark);

            //The Off icon is offset by 4 points to the right. So we need to do this.
            int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
            tabTaskQEntry__SetReminder.setPadding(intPadding+4,intPadding,intPadding,intPadding);
        }
        else{
            tabTaskQEntry__SetReminder.setImageResource(R.drawable.ic_reminder_on_dark);
            tabTaskQEntry__SetReminder.setTag(R.drawable.ic_reminder_on_dark);

            //The Off icon is offset by 4 points to the right. So we need to do this.
            int intPadding = (int) (getResources().getDimension(R.dimen.taskQ_dialog_corner));
            tabTaskQEntry__SetReminder.setPadding(intPadding,intPadding,intPadding,intPadding);
        }

    }

    //==========================================================================================
    // ToDo: Feature - TBU
    //==========================================================================================
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

        for (int x=0; x<tagsDialogViewModel.strDialogTags.size(); x++){
            addChips((String)tagsDialogViewModel.strDialogTags.get(x));

            ViewGroup.LayoutParams params = TaskQEntry_Tags.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            TaskQEntry_Tags.setLayoutParams(params);
        }
        tagsDialogViewModel.strDialogTags.clear();
    }

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
