package com.example.eclinic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String email = "email", name = "name", token = "token", newUser = "newUser";
    private boolean isPatient;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, null);
    }

    public void saveEmail(String key){
        editor.putString(email, key);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(name, null);
    }

    public void saveName(String key){
        editor.putString(name, key);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(token, null);
    }

    public void saveToken(String key){
        editor.putString(token, key);
        editor.commit();
    }

    public boolean getNewUser() {
        return sharedPreferences.getBoolean(newUser, true);
    }

    public void saveNewUser(boolean key){
        editor.putBoolean(newUser, key);
        editor.commit();
    }

    public boolean getUserType(){
        return sharedPreferences.getBoolean("isPatient",true);
    }
    public void setUserType(boolean key){
        editor.putBoolean("isPatient",key);
        editor.commit();
    }

    public void clearData(){
        editor.clear();
        editor.commit();
    }
}
