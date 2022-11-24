package com.example.door;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
        this.sharedPreferences=context.getSharedPreferences(this.context.getString(R.string.Door_SharedPreferences), Context.MODE_PRIVATE);
    }

    public String getDoor(){
        return this.sharedPreferences.getString(this.context.getString(R.string.Door_SharedPreferences_Name_Key),null);
    }

    public void saveDoor(String door){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(this.context.getString(R.string.Door_SharedPreferences_Name_Key),door);
        editor.apply();
    }
}
