package com.example.peter.gentlereminder;

public class Reminder {
    // instance vars
    private String title;
    private String note;

    public Reminder() {
        // empty constructor
    }

    public Reminder(String title, String note) {
        this.title = title;
        this.note = note;
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
