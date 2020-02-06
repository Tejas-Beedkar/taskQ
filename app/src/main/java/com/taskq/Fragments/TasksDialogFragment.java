package com.taskq.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.taskq.Activities.taskQEntryActivity;
import com.taskq.R;

public class TasksDialogFragment extends DialogFragment {

    private View view;
    private Button buttonDone;

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

        buttonDone = getDialog().findViewById(R.id.TasksDialog_DoneButton);

        //Return back to the taskQEntryActivity
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((taskQEntryActivity)getActivity()).onTasksDialogDismiss();
                dismiss();
            }
        });
    }



}
