package com.nicholaspcole.yatl;

import com.nicholaspcole.yatl.DatabaseContract.Tag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
    public static final String DATABASE_NAME = "Yatl.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TAG = "CREATE TABLE "
            + Tag.TABLE_NAME + " (" + Tag._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Tag.COLUMN_NAME_LABEL + " TEXT";

    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE_TAG);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion,
                int newVersion) {
            // TODO
        }
    }

    public void close() {
        this.databaseHelper.close();
    }

    public DatabaseAdapter open() {
        this.database = this.databaseHelper.getWritableDatabase();
        return this;
    }
}