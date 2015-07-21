package com.claricetechnologies.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;

import com.claricetechnologies.R;

public class ClSplashActivity extends Activity {

    // Introducing a delay of 2.5 seconds for the splash screen
    private static final int SPLASH_SCREEN_MIN_DISPLAY_TIME = 2500;
    private long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStartTime = SystemClock.uptimeMillis();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        navigateAfterDelay();
    }

    private void navigateAfterDelay() {
        long elapsedTime = SystemClock.uptimeMillis() - mStartTime;

        if (elapsedTime < SPLASH_SCREEN_MIN_DISPLAY_TIME) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                }
            }, SPLASH_SCREEN_MIN_DISPLAY_TIME - elapsedTime);
        }
    }
}