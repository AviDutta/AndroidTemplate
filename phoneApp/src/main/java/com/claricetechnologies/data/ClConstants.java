package com.claricetechnologies.data;

public class ClConstants {

    public static final String TAG = "AndTemplate";

    // log levels
    public static final int LOG_LEVEL_VERBOSE = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_WARNING = 3;
    public static final int LOG_LEVEL_ERROR = 4;

    // EnCustomDialogActivity Extras
    public static final String EXTRAS_DIALOG_CATEGORY = "DialogCategory";
    public static final String EXTRAS_ISACTIVITY_RUNNING = "IsActivityRunning";

    /**
     * This class contains web server related URLs and other constants.
     */
    public static class WebService {
    }

    /**
     * This class contains preferences.
     */
    public static class Preferences {
    }

    /**
     * This class contains the Web error codes.
     */
    public static class ErrorCode {
    }

    /**
     * This class contains the push notification related constants.
     */
    public static class Gcm {
        public static final String SENDER_ID = "994245079863";
        public static final String PROPERTY_REG_ID = "registration_id";
        public static final String IS_DEVICE_REGISTERED = "IsDeviceRegistered";
        public static final String EXTRA_PUSH_NOTIFCATION_RESET = "PushNotificationReset";
        public static final String PROPERTY_APP_VERSION = "appVersion";
        public static final String PROPERTY_NOTIFICATION_ID = "notificationID";
        public static final String PUSH_MESSAGE_GROUP_ID = "group_id";
        public static final String PUSH_MESSAGE_USER_ID = "user_id";
        public static final String PUSH_MESSAGE_TITLE = "title";
        public static final String PUSH_MESSAGE_TEXT = "message";
        public static final String PUSH_MESSAGE_TYPE = "type";
        public static final String PUSH_BROADCAST_ACTION = "com.wi.GCM_MESSAGE";
        public static final String PUSH_MESSAGE_FORWARD_ACTION = "com.wi.GCM_MESSAGE.foreground";
    }
}
