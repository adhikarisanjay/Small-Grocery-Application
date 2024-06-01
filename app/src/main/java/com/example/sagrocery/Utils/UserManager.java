package com.example.sagrocery.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public UserManager() {

    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Log in the user
    public void loginUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String userName() {
        String storedUserName = sharedPreferences.getString("User_Name", ""); // The second parameter is the default value if the key is not found
        return storedUserName;
    }
    public String userEmail() {
        String storedUserEmail = sharedPreferences.getString("User_Email", ""); // The second parameter is the default value if the key is not found
        return storedUserEmail;
    }
    // Log out the user
    public void storeLoginUser(String userName,String email) {
        editor.putString("User_Name", userName);
        editor.apply();
    }

    // Log out the user
    public void logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.remove("User_Name");
        editor.apply();
    }
}
