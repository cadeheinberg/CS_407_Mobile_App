package com.cs407.cs407mobileapp.TasksRecyclerView;

public class TaskObject {

    private int id;

    private boolean checked;

    private String title;

    private String date;

    public TaskObject(int id, boolean completed, String text, String time){
        this.id = id;
        this.checked = completed;
        this.title = text;
        this.date = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
