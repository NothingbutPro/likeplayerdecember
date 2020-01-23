package com.ics.likeplayer.SessionManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Admin on 17-10-2015.
 */
public class SessionManager {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String MyPREFERENCES = "MyPrefss";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_SONG_URL = "song_url";
    public static final String KEY_ID = "Student_id";
    public static final String KEY_SONG_NAME = "song_name";
    public static final String KEY_STATE = "state";
    public static final String KEY_OP_BAL = "opening_bal";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DELETE_KEY = "del_or_not";
    private static final String IS_SKIPPED = "IsSlipped";
    private static final String WAITER_NAME = "waiter_name";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = pref.edit();
        editor = pref.edit();

    }


    public void waiterName(String strName){
        editor.putString(WAITER_NAME, strName);
        editor.commit();
    }

    public void serverLogin(int strey_id, String strName, String strState) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SONG_URL, strName);
        editor.putInt(KEY_ID, strey_id);
        editor.putString(KEY_STATE, strState);
        editor.commit();
    }

    public void setDelete(String song_url, String song_name, String del_or_not) {
//        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SONG_URL, song_url);
        editor.putString(KEY_SONG_NAME, song_name);
        editor.putString(KEY_DELETE_KEY, del_or_not);
        editor.commit();
    }
    public void getDeleteSOng(String strName, String strMobile) {
//        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SONG_URL, strName);
        editor.putString(KEY_SONG_NAME, strMobile);
        editor.putString(KEY_DELETE_KEY, strMobile);
        editor.commit();
    }
    public void serverEmailLogin(int strCoust) {
//        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_DELETE_KEY, strCoust);
        editor.commit();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
    }

    public void setSkipped(boolean isLoggedIn) {
        editor.putBoolean(IS_SKIPPED, isLoggedIn);
        editor.commit();
    }

    // Get Skipped State
    public boolean isSkipped() {
        return pref.getBoolean(IS_SKIPPED, false);
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Clearing all data from Shared Preferences
    public void logoutUser() {
        editor.clear();
        editor.commit();

    }

    public String getGetDeleteURl() {

        return pref.getString(KEY_SONG_URL, null);
    }
    public String getUS() {
        return pref.getString(KEY_DELETE_KEY, null);
    }

    public String getMobile() {
        return pref.getString(KEY_SONG_NAME, null);
    }
    public String getU_Type() {
        return pref.getString(KEY_SONG_NAME, null);
    }

    public String getState() {
        return pref.getString(KEY_STATE, null);
    }

    public String getOpBal() {
        return pref.getString(KEY_OP_BAL, null);
    }

    public String getType() {
        return pref.getString(KEY_TYPE, null);
    }

    public String getWaiterName() {
        return pref.getString(WAITER_NAME, null);
    }

    public int getCoustId() {
        return pref.getInt(KEY_ID, 0);
    }
}