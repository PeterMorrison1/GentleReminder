package com.example.peter.gentlereminder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<Reminder> reminderList;

    /**
     * Provides the views for each value of the reminder object
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView note;

        /**
         * Constructs the view holder
         *
         * @param view  the activity view
         */
        public ViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.title);
            note = view.findViewById(R.id.note);
        }
    }

    /**
     * Adds reminder object to the reminderList
     *
     * @param position  index for the object to be added to
     * @param item      reminder object to be added
     */
    public void add(int position, Reminder item)
    {
        reminderList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Removes a reminder object from the reminderList
     *
     * @param position  index of the object to be removed
     */
    public void remove(int position)
    {
        reminderList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Constructs the adapter
     *
     * @param reminderList  the arraylist of reminder objects
     */
    public MyAdapter(List<Reminder> reminderList)
    {
        this.reminderList = reminderList;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Reminder reminder = reminderList.get(position);
        holder.title.setText(reminder.getTitle());
        holder.note.setText(reminder.getNote());
    }

    @Override
    public int getItemCount()
    {
        return reminderList.size();
    }

}
