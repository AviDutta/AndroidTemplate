package com.claricetechnologies.utils;

import android.util.Log;

import com.claricetechnologies.ClPhoneApplication;

public class ClLogger {

    public static void d(String tag, String msg) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.e(tag, msg, tr);
        }
    }

    public static void v(String tag, String msg) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (ClPhoneApplication.mIsDebugable) {
            Log.w(tag, msg);
        }
    }

}
