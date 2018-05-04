package com.example.peter.gentlereminder.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.peter.gentlereminder.Reminder;
import com.example.peter.gentlereminder.database.DBHelper;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            DBHelper dbHelper = new DBHelper(context);
            List<Reminder> myReminders = dbHelper.getAllReminders();

            for (int i = 0; i < myReminders.size(); i++) {
                AlarmHelper alarmHelper = new AlarmHelper(context);
                alarmHelper.setmReminder(myReminders.get(i));
                alarmHelper.scheduleNotification(alarmHelper.getNotification());
            }
        }
    }
}
