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

    public void setmReminder(Reminder mReminder)
    {
        this.mReminder = mReminder;
    }

    public void scheduleNotification(Notification notification)
    {
        Intent notificationIntent = new Intent(context, NotificationHelper.class);

        notificationIntent.putExtra(NotificationHelper.NOTIFICATION_ID, mReminder.getId());
        notificationIntent.putExtra(NotificationHelper.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, mReminder.getId(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long timeInMillis = SystemClock.elapsedRealtime() + 1000; // 1000 ms is for testing purpose atm
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, timeInMillis, pendingIntent);

    }

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
