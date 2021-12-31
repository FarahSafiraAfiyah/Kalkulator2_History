package com.example.kalkulator2_history;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class config {

    public static final String LIST_KEY = ("list_key");;

    public static final String NAME_PREF = ("userInfo");
    public static final Integer MODE_PREF = Integer.valueOf(("0"));

    public static void writeListInPref(Context context, List<Nilai> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, MODE_PREF);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<Nilai> readListFromPref(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String  jsonString = preferences.getString(LIST_KEY,null);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Nilai>>() {}.getType();
        List<Nilai> list = gson.fromJson(jsonString,type);
        return list;
    }

    public static void saveTotalInPref(Context context, int total){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LIST_KEY, total);
        editor.apply();
    }

    public static int loadTotalPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(LIST_KEY, 0);
    }

    public static void removeDataFromPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LIST_KEY);
        editor.apply();
    }
}

