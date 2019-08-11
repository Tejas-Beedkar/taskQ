package com.taskq.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.DialogFragment;

import android.util.TypedValue;
import android.util.DisplayMetrics;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.R;


import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TagsDialogFragment extends DialogFragment {

    private int height;
    private int width;
    private View view;
    private final ArrayList<String> list = new ArrayList<>();
    private Button buttonDone;
    private Button buttonAdd;
    private EditText editTextTags;

    public TagsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskQviewModel tagsDialogViewModel =  ViewModelProviders.of(getActivity()).get(taskQviewModel.class);

        tagsDialogViewModel.scoreTeamA = "Value Set By TagsDialogFragment";
    }

    @Override
    public void onResume() {
        super.onResume();

        buttonDone = getDialog().findViewById(R.id.TagsDialog_DoneButton);
        buttonAdd = getDialog().findViewById(R.id.TagsDialog_EntryButton);
        editTextTags = getDialog().findViewById(R.id.TagsDialog_UserEntry);

        //Set Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (int)(displayMetrics.heightPixels * 0.9f);
        width = (int)(displayMetrics.widthPixels * 0.9f);
        getDialog().getWindow().setLayout(width, height);
        //make corners transparent
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ChipGroup chpGrpTagsDialog = getDialog().findViewById(R.id.ChipsGroupTagsDialog);

        for(int i = 0; i < chpGrpTagsDialog.getChildCount(); i++){
            Chip chip = (Chip) chpGrpTagsDialog.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        list.add(buttonView.getText().toString());
                    }else{
                        list.remove(buttonView.getText().toString());
                    }
                    if(!list.isEmpty()){
                        Toast.makeText(getContext(), list.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        final EditText edittext = (EditText) getDialog().findViewById(R.id.TagsDialog_UserEntry);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    buttonAdd.performClick();
                    return true;
                }
                // If the event is a key-up event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_UP) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    editTextTags.requestFocus();
                    return true;
                }

                return false;
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strChip = ((TextView)getDialog().findViewById(R.id.TagsDialog_UserEntry)).getText().toString();
                if(!strChip.isEmpty() && !strChip.equals(getString(R.string.taskQEntry_Task_Tags))){
                    addChips(strChip);
                }
                ((TextView)getDialog().findViewById(R.id.TagsDialog_UserEntry)).setText("");
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((taskQEntryActivity)getActivity()).onDialogDismiss();
                dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tags_dialog, container, false);

        getActivity().setTheme(R.style.AppTheme_MaterialTab);
        //getDialog().setTitle("Tags"); //does not do anything

        return inflater.inflate(R.layout.fragment_tags_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        getActivity().setTheme(R.style.AppTheme_Main);
    }

    private void addChips(String addchip)
    {
        final ChipGroup chpGrpTagsDialog = getDialog().findViewById(R.id.ChipsGroupTagsDialog);
        final Chip chip = new Chip(getActivity());

        int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(addchip);
        chip.setCheckable(true);
        chip.setCloseIconVisible(true);
        chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
        chip.setTextColor(getContext().getResources().getColor(R.color.colorThemeLightGrey));

        chip.setOnCloseIconClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chpGrpTagsDialog.removeView(chip);
            }
        });

        chpGrpTagsDialog.addView(chip);

    }

}
