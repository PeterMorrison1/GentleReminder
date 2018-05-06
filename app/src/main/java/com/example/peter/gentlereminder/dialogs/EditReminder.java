package com.example.peter.gentlereminder.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.peter.gentlereminder.R;
import com.example.peter.gentlereminder.Reminder;
import com.example.peter.gentlereminder.notifications.AlarmHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditReminder extends Dialog {
    private Reminder reminderObject;

    /**
     * Constructor for the edit reminder dialog
     *
     * @param context        the context of the activity/app
     * @param reminderObject reference to the reminder being edited
     */
    public EditReminder(Context context, Reminder reminderObject) {
        super(context);
        this.reminderObject = reminderObject;
    }

    /**
     * Sets buttons and onClickListeners when dialog is created
     *
     * @param savedInstanceState state of the app
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reminder_prompt);
        EditText title = findViewById(R.id.editTitle);
        EditText note = findViewById(R.id.editNote);
        final CheckBox vibrateBox = findViewById(R.id.vibrate);

        final TimePicker picker = findViewById(R.id.timePicker);
        Button confirm = findViewById(R.id.confirmButton);
        Button cancel = findViewById(R.id.cancelButton);
        final List<CheckBox> checkBoxList = bindCheckBoxes();

        savedState(title, note, picker, checkBoxList, vibrateBox);

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    reminderObject.setHour(picker.getHour());
                    reminderObject.setMinute(picker.getMinute());
                } else {
                    reminderObject.setHour(picker.getCurrentHour());
                    reminderObject.setMinute(picker.getCurrentMinute());
                }

                List<Integer> daysOfWeek = new ArrayList<>();

                // set all 7 days of week to 0 (indicating no scheduled reminder that day)
                daysOfWeek.clear();

                int i = 0;
                for (CheckBox checkBox : checkBoxList) {
                    if (checkBox.isChecked()) {
                        // Calendar.DAY_OF_WEEK counts sunday as 1, saturday as 7
                        daysOfWeek.add(i + 1);
                    } else {
                        daysOfWeek.add(0);
                    }
                    i++;
                }

                EditText title = findViewById(R.id.editTitle);
                EditText note = findViewById(R.id.editNote);

                reminderObject.setTitle(title.getText().toString());
                reminderObject.setNote(note.getText().toString());
                reminderObject.setDaysOfWeek(daysOfWeek);
                reminderObject.setDeleted(false);
                reminderObject.setVibrate(vibrateBox.isChecked());

                AlarmHelper alarmHelper = new AlarmHelper(getContext());
                alarmHelper.setmReminder(reminderObject);
                alarmHelper.scheduleNotification(alarmHelper.getNotification());

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderObject.setDeleted(true);
                dismiss();
            }
        });
    }

    /**
     * Binds the checkbox views in the dialog and adds the binded views to a list.
     *
     * @return list of bound checkbox views
     */
    private List<CheckBox> bindCheckBoxes(){
        CheckBox sunBox = findViewById(R.id.checkBoxSun);
        CheckBox monBox = findViewById(R.id.checkBoxMon);
        CheckBox tueBox = findViewById(R.id.checkBoxTue);
        CheckBox wedBox = findViewById(R.id.checkBoxWed);
        CheckBox thuBox = findViewById(R.id.checkBoxThu);
        CheckBox friBox = findViewById(R.id.checkBoxFri);
        CheckBox satBox = findViewById(R.id.checkBoxSat);

        // for looping through the checkboxes

        final List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(sunBox);
        checkBoxList.add(monBox);
        checkBoxList.add(tueBox);
        checkBoxList.add(wedBox);
        checkBoxList.add(thuBox);
        checkBoxList.add(friBox);
        checkBoxList.add(satBox);

        return checkBoxList;
    }

    /**
     * Fills the parameters or checkboxes of the dialog with previously entered information for
     * the reminder, if it exists.
     *
     * @param title         dialog's title edittext view
     * @param note          dialog's note edittext view
     * @param picker        dialog's spinner time picker
     * @param checkBoxList  list of all checkbox views in the dialog
     */
    private void savedState(EditText title, EditText note, TimePicker picker,
                           List<CheckBox> checkBoxList, CheckBox vibrate){
        List<Integer> savedDaysOfWeek;
        if (reminderObject.getDaysOfWeek() != null) {
            savedDaysOfWeek = reminderObject.getDaysOfWeek();
            if(reminderObject.getId() >= 0)
            {
                title.setText(reminderObject.getTitle());
                note.setText(reminderObject.getNote());
                vibrate.setChecked(reminderObject.isVibrate());
            }

            if (reminderObject.getDaysOfWeek().get(1) >= 0) {
                for(int i = 0; i < checkBoxList.size(); i++) {
                    if (savedDaysOfWeek.get(i) > 0) {
                        checkBoxList.get(i).setChecked(true);
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= 23) {
                picker.setHour(reminderObject.getHour());
                picker.setMinute(reminderObject.getMinute());
            }
        }
    }
}
