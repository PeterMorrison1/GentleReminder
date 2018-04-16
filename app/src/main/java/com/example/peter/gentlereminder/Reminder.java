package com.example.peter.gentlereminder;

public class Reminder {
    // instance vars
    private String title;
    private String note;
    private int id;

    public Reminder() {
        // empty constructor
    }

    public Reminder(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
