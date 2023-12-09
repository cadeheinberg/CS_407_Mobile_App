package com.cs407.SnapTask.TasksRecyclerView;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class TaskObject {

    private int id;
    private boolean checked;
    private String username;
    private Date startDate;
    private Date endDate;
    private String locationName;
    private String locationAddress;
    private String title;
    private String description;
    private static int nextId = 1;

    public TaskObject(Integer id, boolean checked, String username, Date startDate, Date endDate, String locationName, String locationAddress, String title, String description) {
        this.id = id;
        this.checked = checked;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        TaskManager.modifiedTask(this);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        TaskManager.modifiedTask(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        TaskManager.modifiedTask(this);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        TaskManager.modifiedTask(this);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        TaskManager.modifiedTask(this);
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
        TaskManager.modifiedTask(this);
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
        TaskManager.modifiedTask(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        TaskManager.modifiedTask(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        TaskManager.modifiedTask(this);
    }


    public int compare(TaskObject other) {
        Date thisEndDate = this.getEndDate();
        Date otherEndDate = other.getEndDate();

        // Both dates are "any time"
        if (thisEndDate == null && otherEndDate == null) {
            return 0;
        }

        // This task is "any time", but the other is not
        if (thisEndDate == null) {
            return 1; // or -1, depending on how you want to order "any time" tasks
        }

        // The other task is "any time", but this one is not
        if (otherEndDate == null) {
            return -1; // or 1, accordingly
        }

        // Both tasks have specific due dates
        return thisEndDate.compareTo(otherEndDate);
    }



}