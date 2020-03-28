package com.taskq.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taskq.CustomClasses.taskQviewModel;
import com.taskq.R;

public class HomeFragment_Minus_1 extends Fragment {

    private View view;
    private taskQviewModel tagsDialogViewModel;

    public HomeFragment_Minus_1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_minus_1, container, false);

        tagsDialogViewModel =  ViewModelProviders.of(getActivity()).get(taskQviewModel.class);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(tagsDialogViewModel != null){
                tagsDialogViewModel.setMainMinus1();
            }
        }
        else {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
