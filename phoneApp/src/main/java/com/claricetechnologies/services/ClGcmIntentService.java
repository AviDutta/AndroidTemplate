/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.claricetechnologies.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.database.ClDatabaseHelper;
import com.claricetechnologies.receivers.ClGcmBroadcastReceiver;
import com.claricetechnologies.receivers.ClPushNotificationReceiver;
import com.claricetechnologies.utils.ClLogger;
import com.claricetechnologies.web.impl.ClUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code ClGcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class ClGcmIntentService extends IntentService {

    public static ClDatabaseHelper mDatabase;

    public ClGcmIntentService() {
        super("GcmIntentService");
        mDatabase = ClPhoneApplication.getDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification(messageType, extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification(messageType, extras);
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                // Post notification of received message.
                ClLogger.i(ClConstants.TAG, "Received: " + extras.toString());
                sendNotification(messageType, extras);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        ClGcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String type, final Bundle msg) {
        if (!ClUtils.isAppOnForeground(this)) {
            Intent intent = new Intent(this, ClPushNotificationReceiver.class);
            intent.setAction(ClConstants.Gcm.PUSH_BROADCAST_ACTION);
            intent.putExtra(ClConstants.Gcm.PUSH_MESSAGE_TYPE, type);
            intent.putExtras(msg);
            sendBroadcast(intent);
        }
    }
}
