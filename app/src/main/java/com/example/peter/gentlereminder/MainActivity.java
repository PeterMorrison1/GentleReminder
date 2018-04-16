package com.example.peter.gentlereminder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                FloatingActionButton myFab = findViewById(R.id.floatingActionButton);

                if(dy > 0 && myFab.getVisibility() == View.VISIBLE)
                {
                    myFab.hide();
                }
                else if(dy < 0 && myFab.getVisibility() != View.VISIBLE)
                {
                    myFab.show();
                }
            }
        });

        testData();
    }

    private void editReminder()
    {
        Intent intent = new Intent(this, EditReminder.class);

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
