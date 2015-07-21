package com.claricetechnologies.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.claricetechnologies.R;

public class ClActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public static Context getInstace() {
        return new ClActivity();
    }
}
