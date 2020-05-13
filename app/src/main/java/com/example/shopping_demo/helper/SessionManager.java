package com.example.shopping_demo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



public class SessionManager {
    // logCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    //sharedprefrence
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mcontext;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //shared preference file name

    private static final String PREF_NAME = "UserLogin";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        //commit changes
        Log.d(TAG, "User login session modified");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
