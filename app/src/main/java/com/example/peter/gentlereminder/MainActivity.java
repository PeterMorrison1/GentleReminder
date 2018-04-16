package com.example.peter.gentlereminder;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // instance vars
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Reminder> reminderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(reminderList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration
                (this, LinearLayoutManager.VERTICAL));
        testData();

        addReminder();
    }

    private void addReminder()
    {
        FloatingActionButton myFab = findViewById(R.id.myFab);
        myFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                final Reminder newReminder = new Reminder();
                EditReminder editReminder = new EditReminder(MainActivity.this, newReminder);
                reminderList.add(newReminder);

                editReminder.show();
                editReminder.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialog)
                    {
                        mRecyclerView.getAdapter().notifyItemInserted(reminderList.indexOf(newReminder));
                        Toast.makeText(v.getContext(), "Edit item at pos: " , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void testData()
    {
        Reminder reminder;

        for(int i = 0; i < 50; i++) {
            reminder = new Reminder();
            reminder.setTitle("Title: " + i);
            reminder.setNote("Note: " + i);
            reminder.setCount(i);
            reminderList.add(reminder);
        }
    }
}
