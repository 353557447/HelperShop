package com.shuiwangzhijia.shuidian.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class ShareUtils {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("cewayconfig", 0);
        editor = sharedPreferences.edit();

    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void clearData() {
        editor.clear();
        editor.commit();
    }

    public static void putString(String key, String value) {
        if (value != null) {
            editor.putString(key, value);
            editor.commit();
        } else {
            editor.putString(key, "");
            editor.commit();
        }
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getIBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static float getFloat(String key) {
        return sharedPreferences.getFloat(key, -0.00f);
    }

    public static void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static boolean isExitKey(String key) {
        return sharedPreferences.contains(key);
    }

    public static void putDouble(String key, double val) {
        editor.putLong(key, Double.doubleToRawLongBits(val));
        editor.commit();
    }

    public static double getDouble(String key) {
        return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToRawLongBits(114.10138888888889)));
    }

    public static void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public static Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void putDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        //        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
    }


    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<T>();
        String strJson = sharedPreferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
}
