package com.taskq.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.taskq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int maxProgress = 100;
        int curProgress;

        ProgressBar bar_min7 = view.findViewById(R.id.HomeFrag_pbar_1);
        ProgressBar bar_min6 = view.findViewById(R.id.HomeFrag_pbar_2);
        ProgressBar bar_min5 = view.findViewById(R.id.HomeFrag_pbar_3);
        ProgressBar bar_min4 = view.findViewById(R.id.HomeFrag_pbar_4);
        ProgressBar bar_min3 = view.findViewById(R.id.HomeFrag_pbar_5);
        ProgressBar bar_min2 = view.findViewById(R.id.HomeFrag_pbar_6);
        ProgressBar bar_min1 = view.findViewById(R.id.HomeFrag_pbar_7);
        ProgressBar bar_today = view.findViewById(R.id.HomeFrag_pbar_8);
        ProgressBar bar_1 = view.findViewById(R.id.HomeFrag_pbar_9);
        ProgressBar bar_2 = view.findViewById(R.id.HomeFrag_pbar_10);
        ProgressBar bar_3 = view.findViewById(R.id.HomeFrag_pbar_11);
        ProgressBar bar_4 = view.findViewById(R.id.HomeFrag_pbar_12);
        ProgressBar bar_5 = view.findViewById(R.id.HomeFrag_pbar_13);
        ProgressBar bar_6 = view.findViewById(R.id.HomeFrag_pbar_14);
        ProgressBar bar_7 = view.findViewById(R.id.HomeFrag_pbar_15);

        curProgress = 100;

        bar_min7.setMax(maxProgress);
        bar_min7.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min6.setMax(maxProgress);
        bar_min6.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min5.setMax(maxProgress);
        bar_min5.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min4.setMax(maxProgress);
        bar_min4.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min3.setMax(maxProgress);
        bar_min3.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min2.setMax(maxProgress);
        bar_min2.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_min1.setMax(maxProgress);
        bar_min1.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_today.setMax(maxProgress);
        bar_today.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_1.setMax(maxProgress);
        bar_1.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_2.setMax(maxProgress);
        bar_2.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_3.setMax(maxProgress);
        bar_3.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_4.setMax(maxProgress);
        bar_4.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_5.setMax(maxProgress);
        bar_5.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_6.setMax(maxProgress);
        bar_6.setProgress(curProgress);

        curProgress = curProgress - 5;

        bar_7.setMax(maxProgress);
        bar_7.setProgress(curProgress);





    }

}
