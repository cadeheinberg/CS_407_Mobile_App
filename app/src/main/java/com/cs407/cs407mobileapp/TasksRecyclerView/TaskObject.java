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
    private static List<TaskObject> tasksList;
    private static int nextId = 1;

    public TaskObject(Integer id, boolean checked, String username, String startDate, String endDate, String locationName, String locationAddress, String title, String description) {
        if(id == null){
            // Use null for adding a new Task
            this.nextId = nextId + 1;
            this.id = nextId;
        }else{
            // Used when reading from database
            this.id = id;
        }
        this.checked = checked;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.title = title;
        this.description = description;
    }

    public static void setNextId(){
        for (TaskObject taskObject : tasksList){
            if (taskObject.getId() > nextId){
                nextId = taskObject.getId();
            }
        }
    }

    public static void setTasksList(ArrayList<TaskObject> setterTasksList) {
        tasksList = setterTasksList;
    }

    public static List<TaskObject> getTasksList(){
        return tasksList;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
