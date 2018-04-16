package com.example.peter.gentlereminder.adapter;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.gentlereminder.dialogs.EditReminder;
import com.example.peter.gentlereminder.R;
import com.example.peter.gentlereminder.Reminder;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<Reminder> reminderList;

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View button1;
            View button2;

            button1 = v.findViewById(R.id.button1);
            button2 = v.findViewById(R.id.button2);

            updateButtonVisibility(button1, button2);
        }
    };

    /**
     * Provides the views for each value of the reminder object
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView note;
        public ImageButton button1;
        public ImageButton button2;

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
            button1 = view.findViewById(R.id.button1);
            button2 = view.findViewById(R.id.button2);
        }
    }

    public void updateButtonVisibility(View view1, View view2)
    {

        if(view1.getVisibility() == View.VISIBLE && view2.getVisibility() == View.VISIBLE)
        {
            view1.setAnimation(AnimationUtils.loadAnimation(view1.getContext(), R.anim.slide_right));
            view1.setVisibility(View.GONE);

            view2.setAnimation(AnimationUtils.loadAnimation(view2.getContext(), R.anim.slide_right));
            view2.setVisibility(View.GONE);
        }
        else
        {
            view1.setAnimation(AnimationUtils.loadAnimation(view1.getContext(), R.anim.slide_left));
            view1.setVisibility(View.VISIBLE);

            view2.setAnimation(AnimationUtils.loadAnimation(view2.getContext(), R.anim.slide_left));
            view2.setVisibility(View.VISIBLE);
        }
    }

    public void fadeButtonClick(View view)
    {
        view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_on_click));
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
        view.findViewById(R.id.button1).setVisibility(View.GONE);
        view.findViewById(R.id.button2).setVisibility(View.GONE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        final View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeButtonClick(v);
                editPrompt(holder, v);
            }
        };

        final View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeButtonClick(v);
                int pos = holder.getAdapterPosition();
                Toast.makeText(v.getContext(), "Delete item at pos: " + pos, Toast.LENGTH_SHORT).show();
                reminderList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(holder.getAdapterPosition(), reminderList.size());
            }
        };
        final Reminder reminder = reminderList.get(position);
        holder.title.setText(reminder.getTitle());
        holder.note.setText(reminder.getNote());
        holder.itemView.setOnClickListener(listener);
        holder.button1.setOnClickListener(editListener);
        holder.button2.setOnClickListener(deleteListener);
    }

    public void editPrompt(ViewHolder holder, final View v)
    {
        final int pos = holder.getAdapterPosition();
        EditReminder edit = new EditReminder(v.getContext(), reminderList.get(pos));
        edit.show();
        edit.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                Toast.makeText(v.getContext(), "Edit item at pos: " + pos, Toast.LENGTH_SHORT).show();
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return reminderList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}
