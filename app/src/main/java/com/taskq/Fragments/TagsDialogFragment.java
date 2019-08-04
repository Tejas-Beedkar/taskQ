package com.taskq.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.taskq.R;

import android.util.TypedValue;

public class TagsDialogFragment extends DialogFragment {

    private int height;
    private int width;
    private View view;

    public TagsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (int)(displayMetrics.heightPixels * 0.9f);
        width = (int)(displayMetrics.widthPixels * 0.9f);
        getDialog().getWindow().setLayout(width, height);
        //make corners transparent
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addChips();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tags_dialog, container, false);

        getActivity().setTheme(R.style.AppTheme_MaterialTab);

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

    private void addChips()
    {
        ChipGroup chpGrpTagsDialog = getDialog().findViewById(R.id.ChipsGroupTagsDialog);

        for (int i = 1; i <= 3; i++) {
            Chip chip = new Chip(getActivity());
                int paddingDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10,
                        getResources().getDisplayMetrics()
                );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText("Chip #" + i);
            chip.setCheckable(true);
            chip.setCloseIconVisible(true);
            chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
            chip.setTextColor(getContext().getResources().getColor(R.color.colorThemeLightGrey));
            chpGrpTagsDialog.addView(chip);
        }

    }

}
