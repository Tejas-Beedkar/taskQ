package com.taskq.Fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.taskq.DataBase.dBaseManager;
import com.taskq.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private dBaseManager dbManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        dbManager = new dBaseManager(getActivity());
        dbManager.open();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Long lDate_min1;
        Long lDateToday;
        Long lDate_pls1;

        int maxProgress;
        int curProgress[] = new int[15];

        Cursor cursor;

        //Create progress bar handlers
        ProgressBar bar_min7 = view.findViewById(R.id.HomeFrag_pbar_1);
        ProgressBar bar_min6 = view.findViewById(R.id.HomeFrag_pbar_2);
        ProgressBar bar_min5 = view.findViewById(R.id.HomeFrag_pbar_3);
        ProgressBar bar_min4 = view.findViewById(R.id.HomeFrag_pbar_4);
        ProgressBar bar_min3 = view.findViewById(R.id.HomeFrag_pbar_5);
        ProgressBar bar_min2 = view.findViewById(R.id.HomeFrag_pbar_6);
        ProgressBar bar_min1 = view.findViewById(R.id.HomeFrag_pbar_7);
        ProgressBar bar_today = view.findViewById(R.id.HomeFrag_pbar_8);
        ProgressBar bar_pls1 = view.findViewById(R.id.HomeFrag_pbar_9);
        ProgressBar bar_pls2 = view.findViewById(R.id.HomeFrag_pbar_10);
        ProgressBar bar_pls3 = view.findViewById(R.id.HomeFrag_pbar_11);
        ProgressBar bar_pls4 = view.findViewById(R.id.HomeFrag_pbar_12);
        ProgressBar bar_pls5 = view.findViewById(R.id.HomeFrag_pbar_13);
        ProgressBar bar_pls6 = view.findViewById(R.id.HomeFrag_pbar_14);
        ProgressBar bar_pls7 = view.findViewById(R.id.HomeFrag_pbar_15);


        //Step 1 - Construct search queries
        //
        Calendar calenderToday = Calendar.getInstance();
        try {
            String strDateCheck =  new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

            //Construct -1
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            lDate_min1 = calenderToday.getTimeInMillis();

            //Construct Today
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            lDateToday = calenderToday.getTimeInMillis();

            //Construct + 1
            calenderToday.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(strDateCheck));
            calenderToday.add(Calendar.DATE, 1);
            lDate_pls1 = calenderToday.getTimeInMillis();


        }catch(java.text.ParseException e) {
            e.printStackTrace();
            lDate_min1 = Calendar.getInstance().getTimeInMillis();
            lDateToday = Calendar.getInstance().getTimeInMillis();
            lDate_pls1 = Calendar.getInstance().getTimeInMillis();
        }

        //Step 2 - make the counts

        //Get the cursor
        cursor = dbManager.fetchEntryByWhen_NoCompleted(lDate_min1, lDateToday);

        curProgress[0] = 1;
        curProgress[1] = 2;
        curProgress[2] = 3;
        curProgress[3] = 4;
        curProgress[4] = 5;
        curProgress[5] = 6;
        curProgress[6] = 7;
        curProgress[7] = 8;
        curProgress[8] = 9;
        curProgress[9] = 10;
        curProgress[10] = 11;
        curProgress[11] = 12;
        curProgress[12] = 13;
        curProgress[13] = 14;
        curProgress[14] = 15;
        maxProgress = 16;



        bar_min7.setMax(maxProgress);
        bar_min7.setProgress(curProgress[0]);

        bar_min6.setMax(maxProgress);
        bar_min6.setProgress(curProgress[1]);

        bar_min5.setMax(maxProgress);
        bar_min5.setProgress(curProgress[2]);

        bar_min4.setMax(maxProgress);
        bar_min4.setProgress(curProgress[3]);

        bar_min3.setMax(maxProgress);
        bar_min3.setProgress(curProgress[4]);

        bar_min2.setMax(maxProgress);
        bar_min2.setProgress(curProgress[5]);

        bar_min1.setMax(maxProgress);
        bar_min1.setProgress(curProgress[6]);

        bar_today.setMax(maxProgress);
        bar_today.setProgress(curProgress[7]);

        bar_pls1.setMax(maxProgress);
        bar_pls1.setProgress(curProgress[8]);

        bar_pls2.setMax(maxProgress);
        bar_pls2.setProgress(curProgress[9]);

        bar_pls3.setMax(maxProgress);
        bar_pls3.setProgress(curProgress[10]);

        bar_pls4.setMax(maxProgress);
        bar_pls4.setProgress(curProgress[11]);

        bar_pls5.setMax(maxProgress);
        bar_pls5.setProgress(curProgress[12]);

        bar_pls6.setMax(maxProgress);
        bar_pls6.setProgress(curProgress[13]);

        bar_pls7.setMax(maxProgress);
        bar_pls7.setProgress(curProgress[14]);





    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

}
