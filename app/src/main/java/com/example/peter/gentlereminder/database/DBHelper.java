package com.example.peter.gentlereminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.peter.gentlereminder.Reminder;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    // database and table names
    public static final String REMINDERS_DATABASE = "Reminders.db";
    public static final String REMINDERS_TABLE = "reminders";

    // database version number
    public static final int REMINDERS_VERSION = 1;

    // column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";

    public DBHelper(Context context)
    {
        super(context, REMINDERS_DATABASE, null, REMINDERS_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE " + REMINDERS_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_NOTE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }

    public boolean addReminder(Reminder reminder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, reminder.getId());
        contentValues.put(COLUMN_TITLE, reminder.getTitle());
        contentValues.put(COLUMN_NOTE, reminder.getNote());

        db.insert(REMINDERS_TABLE, null, contentValues);
        return true;
    }

    public Reminder getReminder(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + REMINDERS_TABLE + " WHERE "
                + COLUMN_ID + " = ? LIMIT 1", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        Reminder reminder = new Reminder();
        reminder.setId(id);
        reminder.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)));

        cursor.close();
        return reminder;
    }

    public List<Reminder> getAllReminders()
    {
        List<Reminder> mReminderList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + REMINDERS_TABLE, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Reminder reminder = new Reminder();
            reminder.setId(Integer.parseInt(cursor.getString(0)));
            reminder.setTitle(cursor.getString(1));
            reminder.setNote(cursor.getString(2));

            mReminderList.add(reminder);
            cursor.moveToNext();
        }
        cursor.close();
        return mReminderList;
    }

    public int numOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REMINDERS_TABLE);
        return numRows;
    }

    public boolean updateReminder(Reminder reminder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, reminder.getTitle());
        contentValues.put(COLUMN_NOTE, reminder.getNote());

        db.update(REMINDERS_TABLE, contentValues, COLUMN_ID +"=?",
                new String[]{Integer.toString(reminder.getId())});
        return true;
    }

    public void deleteReminder(Reminder reminder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REMINDERS_TABLE, COLUMN_ID + "=?",
                new String[]{String.valueOf(reminder.getId())});
    }

}
