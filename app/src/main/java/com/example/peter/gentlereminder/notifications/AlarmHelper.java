package com.example.peter.gentlereminder.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.example.peter.gentlereminder.R;
import com.example.peter.gentlereminder.Reminder;

import java.util.Calendar;

public class AlarmHelper
{
    private Reminder mReminder;
    private Context context;

    public AlarmHelper(Context context)
    {
        this.context = context;
    }

    public Reminder getmReminder()
    {
        return mReminder;
    }

    /**
     * Sets the reference to the reminder that is being edited or added
     *
     * @param mReminder reference to the reminder being changed
     */
    public void setmReminder(Reminder mReminder)
    {
        this.mReminder = mReminder;
    }

    /**
     * Schedules a notification with the alarm manager and calendar with the info input in dialog
     *
     * @param notification  notification object created in getNotification
     */
    public void scheduleNotification(Notification notification)
    {
        Intent notificationIntent = new Intent(context, NotificationHelper.class);

        notificationIntent.putExtra(NotificationHelper.NOTIFICATION_ID, mReminder.getId());
        notificationIntent.putExtra(NotificationHelper.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, mReminder.getId(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        int hour = mReminder.getHour();
        int minute = mReminder.getMinute();

        // Calendar will be used for scheduling notifications, no use as of May 1, 2018
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hour);

        if(hour < 12)
        {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        }
        else
        {
            calendar.set(Calendar.AM_PM, Calendar.PM);
        }

        // set time for the alarm manager to send the notification
        long timeInMillis = SystemClock.elapsedRealtime() + 1000; // 1000 ms is for testing purpose atm
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, timeInMillis, pendingIntent);

    }

    /**
     * Creates the format of the notification
     *
     * @return  reference to the notification created
     */
    public Notification getNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context.getApplicationContext(), "notify_001");

        builder.setContentTitle(mReminder.getTitle());
        builder.setContentText(mReminder.getNote());
        builder.setSmallIcon(R.mipmap.ic_launcher_round);

        return builder.build();
    }
}
