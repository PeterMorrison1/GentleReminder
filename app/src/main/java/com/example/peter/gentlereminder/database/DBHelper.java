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

public class DBHelper extends SQLiteOpenHelper {
    // database and table names
    private static final String REMINDERS_DATABASE = "Reminders.db";
    private static final String REMINDERS_TABLE = "reminders";

    // database version number
    private static final int REMINDERS_VERSION = 4;

    // column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_SUN = "sun";
    private static final String COLUMN_MON = "mon";
    private static final String COLUMN_TUE = "tue";
    private static final String COLUMN_WED = "wed";
    private static final String COLUMN_THU = "thu";
    private static final String COLUMN_FRI = "fri";
    private static final String COLUMN_SAT = "sat";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";

    /**
     * Constructor for the database
     *
     * @param context activity context
     */
    public DBHelper(Context context) {
        super(context, REMINDERS_DATABASE, null, REMINDERS_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + REMINDERS_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_MON + " INTEGER,"
                + COLUMN_SUN + " INTEGER,"
                + COLUMN_TUE + " INTEGER,"
                + COLUMN_WED + " INTEGER,"
                + COLUMN_THU + " INTEGER,"
                + COLUMN_FRI + " INTEGER,"
                + COLUMN_SAT + " INTEGER,"
                + COLUMN_HOUR + " INTEGER,"
                + COLUMN_MINUTE + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }

    /**
     * Adds a reminder to the screen and database
     *
     * @param reminder a reference to a reminder object
     * @return returns true when done for error checking
     */
    public boolean addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, reminder.getTitle());
        contentValues.put(COLUMN_NOTE, reminder.getNote());

        contentValues.put(COLUMN_SUN, reminder.getDaysOfWeek().get(0));
        contentValues.put(COLUMN_MON, reminder.getDaysOfWeek().get(1));
        contentValues.put(COLUMN_TUE, reminder.getDaysOfWeek().get(2));
        contentValues.put(COLUMN_WED, reminder.getDaysOfWeek().get(3));
        contentValues.put(COLUMN_THU, reminder.getDaysOfWeek().get(4));
        contentValues.put(COLUMN_FRI, reminder.getDaysOfWeek().get(5));
        contentValues.put(COLUMN_SAT, reminder.getDaysOfWeek().get(6));

        contentValues.put(COLUMN_HOUR, reminder.getHour());
        contentValues.put(COLUMN_MINUTE, reminder.getMinute());

        db.insert(REMINDERS_TABLE, null, contentValues);
        Cursor cursor = db.rawQuery("select * from " + REMINDERS_TABLE, null);
        cursor.moveToLast();
        reminder.setId(cursor.getInt(0));
        cursor.close();
        //TODO: Remove boolean later, make method void
        return true;
    }

    /**
     * Gets the reminder instance based on the id
     *
     * @param id id of the reminder
     * @return returns the reference to the selected reminder
     */
    public Reminder getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + REMINDERS_TABLE + " WHERE "
                + COLUMN_ID + " = ? LIMIT 1", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        Reminder reminder = new Reminder();
        reminder.setId(id);
        reminder.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)));

        List<Integer> mList = new ArrayList<>();
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUN)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MON)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUE)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WED)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_THU)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FRI)));
        mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SAT)));
        reminder.setDaysOfWeek(mList);

        reminder.setHour(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOUR)));
        reminder.setMinute(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MINUTE)));

        return reminder;
    }

    /**
     * Gets a list of all reminder objects in the database
     *
     * @return the list containing all reminder references
     */
    public List<Reminder> getAllReminders() {
        List<Reminder> mReminderList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + REMINDERS_TABLE, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Reminder reminder = new Reminder();
            reminder.setId(Integer.parseInt(cursor.getString(0)));
            reminder.setTitle(cursor.getString(1));
            reminder.setNote(cursor.getString(2));

            List<Integer> mList = new ArrayList<>();
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUN)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MON)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUE)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WED)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_THU)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FRI)));
            mList.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SAT)));
            reminder.setDaysOfWeek(mList);

            reminder.setHour(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOUR)));
            reminder.setMinute(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MINUTE)));

            mReminderList.add(reminder);
            cursor.moveToNext();
        }
        cursor.close();
        return mReminderList;
    }

    /**
     * Gets the number of rows (entries) in the database
     *
     * @return returns the number of rows in the database
     */
    public int numOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REMINDERS_TABLE);
        return numRows;
    }

    /**
     * Update the information of an entry in the database
     *
     * @param reminder reference to the object that contains updated information
     * @return returns true when done for error checking
     */
    public boolean updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        List<Integer> mList = reminder.getDaysOfWeek();


        contentValues.put(COLUMN_TITLE, reminder.getTitle());
        contentValues.put(COLUMN_NOTE, reminder.getNote());

        contentValues.put(COLUMN_SUN, mList.get(0));
        contentValues.put(COLUMN_MON, mList.get(1));
        contentValues.put(COLUMN_TUE, mList.get(2));
        contentValues.put(COLUMN_WED, mList.get(3));
        contentValues.put(COLUMN_THU, mList.get(4));
        contentValues.put(COLUMN_FRI, mList.get(5));
        contentValues.put(COLUMN_SAT, mList.get(6));

        contentValues.put(COLUMN_HOUR, reminder.getHour());
        contentValues.put(COLUMN_MINUTE, reminder.getMinute());

        db.update(REMINDERS_TABLE, contentValues, COLUMN_ID + "=?",
                new String[]{Integer.toString(reminder.getId())});
        //TODO: Remove boolean later, make method void
        return true;
    }

    /**
     * Removes the entry from the database
     *
     * @param reminder the reference to the reminder object being deleted
     */
    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REMINDERS_TABLE, COLUMN_ID + "=?",
                new String[]{String.valueOf(reminder.getId())});
    }
}
