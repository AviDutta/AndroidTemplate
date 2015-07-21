package com.claricetechnologies;

import android.app.Application;
import android.content.Context;

import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.database.ClDatabaseHelper;
import com.claricetechnologies.factory.ClObjectFactory;
import com.claricetechnologies.utils.ClFileUtils;
import com.crashlytics.android.Crashlytics;

public class ClPhoneApplication extends Application {

    public static boolean mIsDebugable = true;
    public static ClDatabaseHelper mDatabase;
    public static ClObjectFactory mObjectFactory;

    private static ClPhoneApplication mInstance;

    public static synchronized ClConstants.WebService getWebService() {
        return new ClConstants.WebService();
    }

    public static synchronized ClDatabaseHelper getDatabase(Context context) {
        return ClDatabaseHelper.getInstance(context);
    }

    /**
     * To get the singleton instance of this class.
     *
     * @return application
     */
    public static synchronized ClPhoneApplication getInstance() {
        return mInstance;
    }

    public static synchronized Context getContext() {
        return getInstance().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
        }
        ClFileUtils.getInstance().initPreferences(getBaseContext());
        Crashlytics.start(this);
        mDatabase = ClPhoneApplication.getDatabase(this);
        mObjectFactory = ClObjectFactory.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
