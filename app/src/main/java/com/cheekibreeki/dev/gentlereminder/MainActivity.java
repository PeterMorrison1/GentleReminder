package com.cheekibreeki.dev.gentlereminder;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cheekibreeki.dev.gentlereminder.adapter.MyAdapter;
import com.cheekibreeki.dev.gentlereminder.database.DBHelper;
import com.cheekibreeki.dev.gentlereminder.dialogs.EditReminder;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    // instance vars
    private RecyclerView mRecyclerView;
    private List<Reminder> reminderList;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminderList = db.getAllReminders();

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new MyAdapter(reminderList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration
                (this, LinearLayoutManager.VERTICAL));

        // Hides floating action button when scrolling
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FloatingActionButton myFab = findViewById(R.id.myFab);

                if (dy == 0 && myFab.getVisibility() != View.VISIBLE) {
                    myFab.show();
                } else if (dy > 0 && myFab.getVisibility() == View.VISIBLE) {
                    myFab.hide();
                } else if (dy < 0 && myFab.getVisibility() != View.VISIBLE) {
                    myFab.show();
                }
            }
        });
        addReminder();
    }

    /**
     * Adds a Prompt for the user to enter information for new reminder object
     * then adds the reference to the object to the database and recyclerview
     */
    private void addReminder() {
        FloatingActionButton myFab = findViewById(R.id.myFab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Reminder newReminder = new Reminder();
                EditReminder editReminder = new EditReminder(MainActivity.this, newReminder);
                try {
                editReminder.show();
                editReminder.setCancelable(false);
                editReminder.setCanceledOnTouchOutside(false);
                } catch (Exception e) {
                    Log.wtf("UI Exception", "Exception: " + e);
                }
                editReminder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (!newReminder.isDeleted()) {
                            reminderList.add(newReminder);
                            db.addReminder(newReminder);
                            mRecyclerView.getAdapter().
                                    notifyItemInserted(reminderList.indexOf(newReminder));
                        } else {
                            Toast.makeText(v.getContext(), "Reminder canceled.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
