package com.example.peter.gentlereminder;


import java.util.List;

public class Reminder {
    // instance vars
    private String title;
    private String note;
    private int id;
    private boolean deleted;
    private int hour;
    private int minute;
    private List<Integer> daysOfWeek;

    /**
     * Empty constructor for the reminder class
     */
    public Reminder()
    {
        // empty constructor
    }

    /**
     * Get the deleted status
     *
     * @return  if the reminder is deleted
     */
    public boolean isDeleted()
    {
        return deleted;
    }

    /**
     * Set if the reminder is deleted
     *
     * @param deleted   if the reminder is deleted
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * Gets the id
     *
     * @return  the id number for the reminder object
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id    the id number for the reminder object
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets the title
     *
     * @return  title of the reminder
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     *
     * @param title the title to set for the reminder
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the note
     *
     * @return  note of the reminder
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note
     *
     * @param note  the note to set for the reminder
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return  hour scheduled for the reminder in 24 hour format
     */
    public int getHour()
    {
        return hour;
    }

    /**
     *
     * @param hour schedule hour for the reminder in 24 hour format
     */
    public void setHour(int hour)
    {
        this.hour = hour;
    }

    /**
     *
     * @return  minute scheduled for the reminder (0-59)
     */
    public int getMinute()
    {
        return minute;
    }

    /**
     *
     * @param minute    schedule minute for the reminder (0-59)
     */
    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    /**
     * List of days to schedule a reminder for every week. By default all values are 0
     * if a day is scheduled it will have the corresponding number to the day in the week
     * so sunday is 1, monday 2, saturday 7. (calendar requires sunday as 1)
     * 0 index is sunday 6 index is saturday.
     *
     * @return  list of the days there are reminders set for
     */
    public List<Integer> getDaysOfWeek()
    {
        return daysOfWeek;
    }

    /**
     * List of days to schedule a reminder for every week. By default all values are 0
     * if a day is scheduled it will have the corresponding number to the day in the week
     * so sunday is 1, monday 2, saturday 7. (calendar requires sunday as 1)
     * 0 index is sunday 6 index is saturday.
     *
     * @param daysOfWeek    list of days there are to be reminders set
     */
    public void setDaysOfWeek(List<Integer> daysOfWeek)
    {
        this.daysOfWeek = daysOfWeek;
    }
}
