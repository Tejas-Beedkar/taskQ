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
    private String strUserEntryStatus;
    public static final String fstrUserEntryNew = "NewUserEntry";
    public static final String fstrUserEntryMod = "ModUserEntry";
    public static final String fstrUserEntryclr = "ClrUserEntry";

    public taskQviewModel() {
        super();
        strUserEntryStatus = fstrUserEntryclr;
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


    public boolean bSetUserEntryNew(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryclr)){
            strUserEntryStatus = fstrUserEntryNew;
            fRet = true;
        }
        return fRet;
    }
    public boolean bClearUserEntryNew(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryNew)){
            strUserEntryStatus = fstrUserEntryclr;
            fRet = true;
        }
        return fRet;
    }
    public boolean bSetUserEntryModify(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryclr)){
            strUserEntryStatus = fstrUserEntryMod;
            fRet = true;
        }
        return fRet;
    }
    public boolean bClearUserEntryModify(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryMod)){
            strUserEntryStatus = fstrUserEntryclr;
            fRet = true;
        }
        return fRet;
    }

}
