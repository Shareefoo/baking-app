package com.udacity.bakingapp.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.net.URISyntaxException;

/**
 * Created by shareefoo
 */

public class SpManager {

    private static final String SP_NAME = "baking_prefs";

    private static SpManager instance = null;

    private SharedPreferences mSharedPreferences;

    private SpManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SpManager getInstance(Context context) {
        if (instance == null) {
            instance = new SpManager(context);
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public boolean putString(String key, String value) {
        return mSharedPreferences.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public boolean putInt(String key, int value) {
        return mSharedPreferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public boolean saveIntent(String key, Intent intent) {
        return mSharedPreferences.edit().putString(key, intent.toURI()).commit();
    }

    public Intent restoreIntent(String key, String defValue) throws URISyntaxException {
        return Intent.getIntent(mSharedPreferences.getString(key, defValue));
    }

    public boolean remove(String key) {
        return mSharedPreferences.edit().remove(key).commit();
    }

}
