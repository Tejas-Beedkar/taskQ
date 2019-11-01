package com.taskq.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.R;

public class NamesDialogFragment extends DialogFragment {

    private int height;
    private int width;
    private Button buttonDone;
    private ListView listViewNames;
    private ChipGroup chipGroupSelectedNames;

    public NamesDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //view = inflater.inflate(R.layout.fragment_tags_dialog, container, false);

        getActivity().setTheme(R.style.AppTheme_MaterialTab);
        //getDialog().setTitle("Tags"); //does not do anything

        return inflater.inflate(R.layout.fragment_names_dialog, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        buttonDone = getDialog().findViewById(R.id.NamesDialog_DoneButton);
        listViewNames = getDialog().findViewById(R.id.NamesDialog_LV_Names);
        chipGroupSelectedNames = getDialog().findViewById(R.id.NamesDialog_CG_Selected);

        //Set Dialog Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (int)(displayMetrics.heightPixels * 0.9f);
        width = (int)(displayMetrics.widthPixels * 0.9f);
        getDialog().getWindow().setLayout(width, height);
        //Make Dialog Corners Transparent
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Return back to the taskQEntryActivity
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((taskQEntryActivity)getActivity()).onDialogDismiss();
                dismiss();
            }
        });
    }
}






































