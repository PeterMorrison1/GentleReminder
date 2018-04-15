package com.example.peter.gentlereminder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EditReminder extends Dialog
{
    private Reminder reminderObject;
    private boolean isDismissed = false;

    public EditReminder(Context context, Reminder reminderObject)
    {
        super(context);
        this.reminderObject = reminderObject;
    }

    public boolean isDismissed()
    {
        return isDismissed;
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
                isDismissed = true;
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
