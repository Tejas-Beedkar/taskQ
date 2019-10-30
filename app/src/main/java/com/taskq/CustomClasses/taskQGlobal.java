package com.taskq.CustomClasses;

import android.app.Application;


//==========================================================================================
//          Feature - 008
//          General Purpose Global Class
//==========================================================================================
public class taskQGlobal extends Application {

    private static String strUserEntryStatus;
    public static final String fstrUserEntryNew = "NewUserEntry";
    public static final String fstrUserEntryMod = "ModUserEntry";
    public static final String fstrUserEntryclr = "ClrUserEntry";

    public taskQGlobal() {
        super();
        strUserEntryStatus = fstrUserEntryclr;
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
