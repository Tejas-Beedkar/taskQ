package com.taskq.CustomClasses;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

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

    public ViewPager tab_ViewPager;

    public TextView HomeFrag_pbar_Text_Min_7;
    public TextView HomeFrag_pbar_Text_Min_6;
    public TextView HomeFrag_pbar_Text_Min_5;
    public TextView HomeFrag_pbar_Text_Min_4;
    public TextView HomeFrag_pbar_Text_Min_3;
    public TextView HomeFrag_pbar_Text_Min_2;
    public TextView HomeFrag_pbar_Text_Min_1;
    public TextView HomeFrag_pbar_Text_Today;
    public TextView HomeFrag_pbar_Text_Pls_1;
    public TextView HomeFrag_pbar_Text_Pls_2;
    public TextView HomeFrag_pbar_Text_Pls_3;
    public TextView HomeFrag_pbar_Text_Pls_4;
    public TextView HomeFrag_pbar_Text_Pls_5;
    public TextView HomeFrag_pbar_Text_Pls_6;
    public TextView HomeFrag_pbar_Text_Pls_7;

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

    public void setMainMinus7(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }

    public void setMainMinus6(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainMinus5(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainMinus4(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainMinus3(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainMinus2(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainMinus1(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }

    public void setMainToday(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }

    public void setMainPlus1(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus2(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus3(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus4(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus5(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus6(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_dark);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        return;
    }
    public void setMainPlus7(){
        HomeFrag_pbar_Text_Min_7.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Min_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Today.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_1.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_2.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_3.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_4.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_5.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_6.setBackgroundResource(R.drawable.border_progress_bar_light);
        HomeFrag_pbar_Text_Pls_7.setBackgroundResource(R.drawable.border_progress_bar_dark);
        return;
    }
}
