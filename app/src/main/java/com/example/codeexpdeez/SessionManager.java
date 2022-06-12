package com.example.codeexpdeez;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences sharedPrefer;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared Pref mode
    int PRIVATE_MODE = 0;

    // Shared Pref file name
    private static final String PREF_NAME = "MySession";

    // SHARED PREF KEYS FOR ALL DATA

    public static final String KEY_UID = "UID";

    public static final String KEY_PRIVILEGE = "Privilege";

    public static final String KEY_UNIT = "Unit";

    public static final String KEY_COY = "Coy";

    public static final String KEY_NAME = "Name";

    public static final String KEY_RANK = "Rank";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        sharedPrefer = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPrefer.edit();
    }

    /**
     * Call this method on/after login to store the details in session
     * */

    public void createLoginSession(String UID, String Name, String Rank, String privilege, String unit, String coy) {

        editor.putString(KEY_UID, UID);

        editor.putString(KEY_PRIVILEGE, privilege);

        editor.putString(KEY_UNIT, unit);

        editor.putString(KEY_COY, coy);

        editor.putString(KEY_NAME, Name);

        editor.putString(KEY_RANK, Rank);

        // commit changes
        editor.commit();
    }

    /**
     * Call this method anywhere in the project to get the stored session data
     * */
    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();
        user.put("UID",sharedPrefer.getString(KEY_UID, null));
        user.put("Name", sharedPrefer.getString(KEY_NAME, null));
        user.put("Rank", sharedPrefer.getString(KEY_RANK, null));
        user.put("Privilege",sharedPrefer.getString(KEY_PRIVILEGE, null));
        user.put("Unit", sharedPrefer.getString(KEY_UNIT, null));
        user.put("Coy", sharedPrefer.getString(KEY_COY, null));

        return user;
    }
}
