package com.taskq.CustomClasses;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

//==========================================================================================
// ToDo:    Feature - 008
//          General Purpose ViewModel to store lifecycle independent information.
//==========================================================================================
public class taskQviewModel extends ViewModel {

    //Used to pass data from TagsDialogFragment to the taskQEntryActivity
    public ArrayList strDialogTags = new ArrayList();

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

}
