package com.example.peter.gentlereminder.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.peter.gentlereminder.R;
import com.example.peter.gentlereminder.Reminder;

public class EditReminder extends Dialog
{
    private Reminder reminderObject;

    public EditReminder(Context context, Reminder reminderObject)
    {
        super(context);
        this.reminderObject = reminderObject;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_prompt);

        Button confirm = findViewById(R.id.confirmButton);
        Button cancel = findViewById(R.id.cancelButton);

        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                EditText title = findViewById(R.id.editTitle);
                reminderObject.setTitle(title.getText().toString());
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }
}
