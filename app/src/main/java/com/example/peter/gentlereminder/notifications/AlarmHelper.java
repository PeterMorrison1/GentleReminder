package com.example.peter.gentlereminder.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.peter.gentlereminder.R;
import com.example.peter.gentlereminder.Reminder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmHelper {
    private Reminder mReminder;
    private Context context;
    private final int UNIQUE_MULTIPLIER = 10;

    public AlarmHelper(Context context) {
        this.context = context;
    }

    public Reminder getmReminder() {
        return mReminder;
    }

    /**
     * Sets the reference to the reminder that is being edited or added
     *
     * @param mReminder reference to the reminder being changed
     */
    public void setmReminder(Reminder mReminder) {
        this.mReminder = mReminder;
    }

    /**
     * Schedules a notification with the alarm manager and calendar with the info input in dialog
     *
     * @param notification notification object created in getNotification
     */
    public void scheduleNotification(Notification notification) {
        Intent notificationIntent = new Intent(context, NotificationHelper.class);

        notificationIntent.putExtra(NotificationHelper.NOTIFICATION_ID, mReminder.getId());
        notificationIntent.putExtra(NotificationHelper.NOTIFICATION, notification);

        List<Integer> daysList = mReminder.getDaysOfWeek();

        for (int i = 0; i < daysList.size(); i++) {
            int uniqueReminderId = mReminder.getId() * UNIQUE_MULTIPLIER;

            if (daysList.get(i) == 0) {
                continue;
            }

            int dayId;
            switch (i) {
                // sunday
                case 0:
                    dayId = daysList.get(i);
                    break;

                case 1:
                    dayId = daysList.get(i);
                    break;

                case 2:
                    dayId = daysList.get(i);
                    break;

                case 3:
                    dayId = daysList.get(i);
                    break;

                case 4:
                    dayId = daysList.get(i);
                    break;

                case 5:
                    dayId = daysList.get(i);
                    break;
                // saturday
                case 6:
                    dayId = daysList.get(i);
                    break;

                default:
                    continue;
            }

            /* By adding (reminder id * 10) we get a completely unique number that isn't replicable
             * by another reminder with a different id. So unique id and easy to replicate
             * if you have the same reminder id
             */

            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (context, dayId + uniqueReminderId, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            int hour = mReminder.getHour();
            int minute = mReminder.getMinute();

            // Calendar will be used for scheduling notifications, no use as of May 1, 2018
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, dayId);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 1);

            // get am or pm since hour is stored as 24 hour format
            if (hour < 12) {
                calendar.set(Calendar.AM_PM, Calendar.AM);
            } else {
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }

            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 7);
            }

            // set time for the alarm manager to send the notification
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    24 * 7 * 60 * 60 * 1000, pendingIntent);
        }
    }

    /**
     * Creates the format of the notification
     *
     * @return reference to the notification created
     */
    public Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context.getApplicationContext(), "notify_001");

        builder.setContentTitle(mReminder.getTitle());
        builder.setContentText(mReminder.getNote());
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setShowWhen(false);
        if(!mReminder.isVibrate()) {
            builder.setVibrate(new long[]{0L});
        }
        return builder.build();
    }

    public void deleteNotification(Notification notification) {
        Intent notificationIntent = new Intent(context, NotificationHelper.class);

        notificationIntent.putExtra(NotificationHelper.NOTIFICATION_ID, mReminder.getId());
        notificationIntent.putExtra(NotificationHelper.NOTIFICATION, notification);

        List<Integer> daysList = mReminder.getDaysOfWeek();

        for (int i = 0; i < daysList.size(); i++) {
            int uniqueReminderId = mReminder.getId() * UNIQUE_MULTIPLIER;

            if (daysList.get(i) == 0) {
                continue;
            }

            int dayId;
            switch (i) {
                // sunday
                case 0:
                    dayId = daysList.get(i);
                    Toast.makeText(context, "DAY: SUN", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    dayId = daysList.get(i);
                    Toast.makeText(context, "DAY: MON", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    dayId = daysList.get(i);
                    Toast.makeText(context, "DAY: TUE", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    dayId = daysList.get(i);
                    break;

                case 4:
                    dayId = daysList.get(i);
                    break;

                case 5:
                    dayId = daysList.get(i);
                    break;
                // saturday
                case 6:
                    dayId = daysList.get(i);
                    break;

                default:
                    continue;
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (context, dayId + uniqueReminderId, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }
}
