package com.nicholaspcole.yatl;

import android.provider.BaseColumns;

public class DatabaseContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEPARATOR = ",";

    public DatabaseContract() {
        // An empty constructor prevents someone from accidentally instantiating
        // the contract class.
    }

    public static abstract class Tag implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_LABEL = "label";
    }
}