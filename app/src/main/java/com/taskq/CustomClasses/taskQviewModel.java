package com.taskq.CustomClasses;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.taskq.R;

import java.util.ArrayList;

//==========================================================================================
//          Feature - 008
//          General Purpose ViewModel to store lifecycle independent information.
//==========================================================================================
public class taskQviewModel extends ViewModel {

    //Used to pass data from TagsDialogFragment to the taskQEntryActivity
    public ArrayList strDialogTags = new ArrayList();

    public ArrayList strDialogNames = new ArrayList();

    public TextView HomeFrag_pbar_Text_Min_1;
    public TextView HomeFrag_pbar_Text_Today;
    public TextView HomeFrag_pbar_Text_Pls_1;

    public taskQviewModel() {
        super();
    }

    public void setTagsString(){
        return;
    }

    public void getTagsString(){
        return;
    }

    public void clearTagsString(){
        return;
    }

    public void setMainMinus1(){
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }

    public void setMainToday(){
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }

    public void setMainPlus1(){
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_dark);
        return;
    }
}
