package com.nicholaspcole.yatl;

import com.nicholaspcole.yatl.DatabaseContract.Tag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TagDatabaseAdapter {
    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TagDatabaseAdapter(Context context) {
        this.context = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DatabaseAdapter.DATABASE_NAME, null,
                    DatabaseAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

    public void close() {
        this.databaseHelper.close();
    }

    public long createTag(String label) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Tag.COLUMN_NAME_LABEL, label);
        return this.database.insert(Tag.TABLE_NAME, null, initialValues);
    }

    public boolean deleteTag(long rowId) {
        return this.database
                .delete(Tag.TABLE_NAME, Tag._ID + "=" + rowId, null) > 0;
    }

    public Cursor getAllTags() {
        return this.database.query(Tag.TABLE_NAME, new String[] { Tag._ID,
                Tag.COLUMN_NAME_LABEL }, null, null, null, null, null);
    }

    public Cursor getTag(long rowId) {
        Cursor cursor = this.database.query(true, Tag.TABLE_NAME, new String[] {
                Tag._ID, Tag.COLUMN_NAME_LABEL }, Tag._ID + "=" + rowId, null,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public TagDatabaseAdapter open() {
        this.databaseHelper = new DatabaseHelper(this.context);
        this.database = this.databaseHelper.getWritableDatabase();
        return this;
    }

    public boolean updateTag(long rowId, String label) {
        ContentValues newValues = new ContentValues();
        newValues.put(Tag.COLUMN_NAME_LABEL, label);
        return this.database.update(Tag.TABLE_NAME, newValues, Tag._ID + "="
                + rowId, null) > 0;
    }
}
