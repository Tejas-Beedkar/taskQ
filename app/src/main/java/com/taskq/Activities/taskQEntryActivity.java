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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            EditText_Task.setAutofillHints(getString(R.string.taskQEntry_Task_Description));
        }

        TaskQEntry_Tags = findViewById(R.id.tabTaskQEntry_Tags);

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
            }
        });

        TaskQEntry_Tags.addView(chip);

    }
}
