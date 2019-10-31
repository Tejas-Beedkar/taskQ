package com.taskq.CustomClasses;

import android.app.Application;


//==========================================================================================
//          Feature - 008
//          General Purpose Global Class
//==========================================================================================
public class taskQGlobal extends Application {

    private static String strUserEntryStatus;
    private long idModify;
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
    public boolean bCheckUserEntryNew(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryNew)){
            fRet = true;
        }
        return fRet;
    }

    public boolean bSetUserEntryModify(long id){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryclr)){
            strUserEntryStatus = fstrUserEntryMod;
            idModify = id;
            fRet = true;
        }
        return fRet;
    }
    public long bGetUserEntryModify(){
        long retValue = 0;
        if(strUserEntryStatus.equals(fstrUserEntryMod)){
            retValue = idModify;
        }
        return retValue;
    }
    public boolean bClearUserEntryModify(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryMod)){
            strUserEntryStatus = fstrUserEntryclr;
            fRet = true;
        }
        return fRet;
    }
    public boolean bCheckUserEntryModify(){
        boolean fRet = false;
        if(strUserEntryStatus.equals(fstrUserEntryMod)){
            fRet = true;
        }
        return fRet;
    }
}
