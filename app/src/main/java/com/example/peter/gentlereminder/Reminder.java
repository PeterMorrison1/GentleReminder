package com.example.peter.gentlereminder;

public class Reminder {
    // instance vars
    private String title;
    private String note;
    private int count;
    private boolean clicked;

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

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
