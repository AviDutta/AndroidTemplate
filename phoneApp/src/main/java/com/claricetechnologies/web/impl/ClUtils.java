package com.claricetechnologies.web.impl;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.utils.ClLogger;

import java.util.List;

public class ClUtils {

    private static ProgressDialog mProgressDialog;
    private static AlertDialog.Builder mAlertDialog;
    private static Toast toast = null;

    /**
     * Method to show generic toast.
     *
     * @param message
     */
    public static void showToast(String message) {
        if (isAppOnForeground(ClPhoneApplication.getContext())) {
            if (null == toast) {
                toast = Toast.makeText(ClPhoneApplication.getContext(), message,
                        Toast.LENGTH_LONG);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }


    /**
     * Method to show the notifications details dialog.
     */
    public static void showDialog(Context context, String title, String message) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context);
        }
        mAlertDialog.setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = mAlertDialog.create();
        // show it
        alertDialog.show();
    }

    /**
     * Method to show the progress dialog.
     */
    public static void showProgressDialog(Context context, String message) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage(message);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Method to hide the progress dialog.
     */
    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        }
    }

    /*
     * Identifies if the app is in foreground or in background.
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (null == appProcesses) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the network is available.
     *
     * @return isConnected
     */
    public static boolean isNetworkConnected() {
        boolean isConnected = false;
        ConnectivityManager nm = (ConnectivityManager) ClPhoneApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo[] allnets = nm.getAllNetworkInfo();
        if (allnets != null) {
            for (android.net.NetworkInfo net : allnets) {
                if (net.isConnected()) {
                    return true;
                } else {
                    isConnected = false;
                }
            }
        }
        return isConnected;
    }

    public static void hideKeyboard(Context mContext, View mEditText) {
        @SuppressWarnings("static-access")
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}

