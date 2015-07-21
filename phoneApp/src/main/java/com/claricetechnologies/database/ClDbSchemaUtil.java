package com.claricetechnologies.database;

import android.provider.BaseColumns;

public class ClDbSchemaUtil {

    // To prevent the instantiation of this class
    private ClDbSchemaUtil() {
    }

    public static class TableDefination implements BaseColumns {
    }

    public static class TableUtil {
        public static final String TYPE_TEXT = " TEXT";
        public static final String TYPE_NUMBER = " INTEGER";
        public static final String COMMA_SEP = ",";
    }
}