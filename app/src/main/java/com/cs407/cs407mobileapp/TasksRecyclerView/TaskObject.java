package com.cs407.cs407mobileapp.TasksRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskObject {

    private int id;
    private boolean checked;
    private String username;
    private String startDate;
    private String endDate;
    private String locationName;
    private String locationAddress;
    private String title;
    private String description;
    private static int nextId = 1;

    public TaskObject(Integer id, boolean checked, String username, String startDate, String endDate, String locationName, String locationAddress, String title, String description) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        TaskManager.modifiedTask(this);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
}