package com.claricetechnologies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.data.ClConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class ClPushNotificationUtil {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String regid;
    private static GoogleCloudMessaging gcm;
    private static Context mContext;

    private static Context getContext() {
        if (mContext == null) {
            mContext = ClPhoneApplication.getInstance().getApplicationContext();
        }
        return mContext;
    }

    public static int registerDevice(Activity activity,
                                     PushRegistrationCallback callback) {
        // Check device for Play Services APK. If check succeeds, proceed with
        // GCM registration.
        int returnCode = checkPlayServices(activity);
        if (returnCode == ConnectionResult.SUCCESS) {
            gcm = GoogleCloudMessaging.getInstance(mContext);
            regid = getRegistrationId();
            ClLogger.d(ClConstants.TAG, regid);
            if (TextUtils.isEmpty(regid)) {
                registerInBackground(callback);
            }
        }
//        else {
//            ClUtils.showToast(mContext.getResources().getString(
//                    R.string.no_google_play_app));
//        }
        return returnCode;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it doesn't, display a
     * dialog that allows users to download the APK from the Google Play Store or enable it in the
     * device's system settings.
     */
    private static int checkPlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
//            else {
//                ClLogger.e(ClConstants.TAG,
//                        mContext.getString(R.string.device_not_supported));
//                ClUtils.showToast(mContext.getResources().getString(
//                        R.string.no_gcm_support));
//            }
        }
        return resultCode;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId   registration ID
     */
    public static void setRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
        ClFileUtils.getInstance().setString(ClConstants.Gcm.PROPERTY_REG_ID, regId);
        ClFileUtils.getInstance().setInt(ClConstants.Gcm.PROPERTY_APP_VERSION, appVersion);
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing registration ID.
     */
    public static String getRegistrationId() {
        String registrationId = ClFileUtils.getInstance().getString(
                ClConstants.Gcm.PROPERTY_REG_ID, "");
//        if (TextUtils.isEmpty(registrationId)) {
//            ClLogger.i(ClConstants.TAG, mContext.getString(R.string.registration_id_message));
//            return "";
//        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = ClFileUtils.getInstance().getInt(
                ClConstants.Gcm.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getContext());
//        if (registeredVersion != currentVersion) {
//            ClLogger.i(ClConstants.TAG, mContext.getString(R.string.app_version_message));
//            return "";
//        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and the app versionCode in the application's shared preferences.
     *
     * @param callback
     */
    private static void registerInBackground(
            final PushRegistrationCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    String senderId = ClConstants.Gcm.SENDER_ID;
                    String[] senderIds = senderId.split(";");
                    regid = gcm.register(senderIds);
                    msg = regid;

                    // Persist the regID - no need to register again.
                    setRegistrationId(mContext, regid);
                } catch (IOException ex) {
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                    ClLogger.e(ClConstants.TAG, ex.getLocalizedMessage(), ex);
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String message) {
                callback.onResponse(message);
            }
        }.execute(null, null, null);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // should never happen
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
//            throw new RuntimeException(mContext.getString(R.string.package_name_failed_message) + e);
        }
        return packageInfo.versionCode;
    }

    /**
     * An interface to defines the callback method to receive push notification message.
     */
    public interface PushRegistrationCallback {
        void onResponse(final String registrationId);
    }
}
