package com.example.agriculturalapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER_TYPE = "userType";

    // Constants for user types
    public static final String USER_TYPE_ADMIN = "admin";
    public static final String USER_TYPE_USER = "user";
    public static final String USER_TYPE_USER_Logout = "Logout";
    public static final String USER_TYPE_RIDER = "supplier";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save user type to SharedPreferences
    public void setUserType(String userType) {
        editor.putString(KEY_USER_TYPE, userType);
        editor.apply();
    }

    // Retrieve user type from SharedPreferences
    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }
}