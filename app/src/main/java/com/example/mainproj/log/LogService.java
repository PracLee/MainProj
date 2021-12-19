package com.example.mainproj.log;

import android.app.Activity;
import android.util.Log;

import com.example.mainproj.R;

public class LogService {
    public static void info(Activity activity, String msg){
        Log.i(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg);
    }
    public static void verbose(Activity activity, String msg){
        Log.v(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg);
    }
    public static void debug(Activity activity, String msg){
        Log.d(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg);
    }
    public static void warn(Activity activity, String msg){
        Log.w(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg);
    }
    public static void warn(Activity activity, String msg, Throwable e){
        Log.w(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg,e);
    }
    public static void error(Activity activity, String msg){
        Log.e(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg);
    }
    public static void error(Activity activity, String msg, Throwable e){
        Log.e(activity.getString(R.string.app_name)+"["+activity.getClass().getSimpleName()+"]",msg,e);
    }
}
