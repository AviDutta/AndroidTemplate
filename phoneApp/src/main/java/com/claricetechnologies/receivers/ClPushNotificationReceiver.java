package com.claricetechnologies.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.activities.ClSplashActivity;
import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.utils.ClFileUtils;

public class ClPushNotificationReceiver extends BroadcastReceiver {

    public static NotificationManager mNotificationManager = (NotificationManager) ClPhoneApplication
            .getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    public static NotificationCompat.Builder pushNotification = null;
    public static NotificationCompat.InboxStyle mInboxStyle = new NotificationCompat.InboxStyle();

    private final String TITLE = "App";
    private final String SUMMERYTEXT = "App Notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long[] pattern = {
                300, 300
        };
        Intent pushIntent = new Intent(context,
                ClSplashActivity.class);
        pushIntent.putExtra(
                ClConstants.Gcm.EXTRA_PUSH_NOTIFCATION_RESET, true);
        pushIntent.putExtra(ClConstants.Gcm.PUSH_MESSAGE_TITLE,
                extras.getString(ClConstants.Gcm.PUSH_MESSAGE_TITLE));
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                pushIntent, PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);
        ClFileUtils.getInstance().setString(
                ClConstants.Gcm.PUSH_MESSAGE_TITLE,
                extras.getString(ClConstants.Gcm.PUSH_MESSAGE_TITLE));
        if (null == pushNotification) {
            pushNotification = new NotificationCompat.Builder(context)
                    .setContentTitle(
                            extras.getString(ClConstants.Gcm.PUSH_MESSAGE_TITLE))
                    .setStyle(
                            mInboxStyle
                                    .addLine(
                                            extras.getString(ClConstants.Gcm.PUSH_MESSAGE_TITLE)
                                                    + ": "
                                                    + extras.getString(ClConstants.Gcm.PUSH_MESSAGE_TEXT))
                                    .setBigContentTitle(TITLE)
                                    .setSummaryText(SUMMERYTEXT))
                    .setGroup(TITLE)
                    .setGroupSummary(true)
                    .setSound(
                            RingtoneManager
                                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(pattern)
                    .setContentText(extras.getString(ClConstants.Gcm
                            .PUSH_MESSAGE_TEXT))
//                    .setNumber(++ClPhoneApplication.mNewNotifications)
                    .setContentIntent(contentIntent).setAutoCancel(true);
        } else {
            pushNotification.setStyle(
                    mInboxStyle
                            .addLine(
                                    extras.getString(ClConstants.Gcm
                                            .PUSH_MESSAGE_TITLE) + ": "
                                            + extras.getString(ClConstants.Gcm
                                            .PUSH_MESSAGE_TEXT))
                            .setBigContentTitle(TITLE));
//                            .setSummaryText(SUMMERYTEXT)).setNumber(
//                    ++ClPhoneApplication.mNewNotifications);
        }
        mNotificationManager.notify(1, pushNotification.build());
    }
}