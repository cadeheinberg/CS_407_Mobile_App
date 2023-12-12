package com.cs407.SnapTask.TasksRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.cs407.SnapTask.Database.DatabaseHandler;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManager {
    
    private static final PriorityQueue<TaskObject> taskQueue = new PriorityQueue<>();
    private static DatabaseHandler db;
    private static ArrayList<TaskObject> tasksList;
    private static int nextId = 1;
    
    // add a task to the queue
    public static void addTaskToQueue(TaskObject task) {
        taskQueue.add(task);
    }
    
    // remove a task from the queue (after completion)
    public static void removeTaskFromQueue(TaskObject task) {
        taskQueue.remove(task);
    }
    
    // get the next task from the queue
    public static TaskObject getNextTask() {
        return taskQueue.peek();
    }
    
    public static TaskObject getTaskFromQueueWithID(int id) {
        return (TaskObject) taskQueue.toArray()[id];
    }
    
    public static int getSizeOfTaskQueue() {
        return taskQueue.size();
    }
    
    // call this every minute or however often we want to update the queue based on if
    // its due date is passed
    public static void removeExpiredTasks() {
        while (!taskQueue.isEmpty()) {
            TaskObject task = taskQueue.peek();
//            if (task.getEndDate() == null) {
//                // If endDate is null, it means the task is set for "Any Time"
//                break;
//            }
            
            if (task.getEndDate() != null && task.getEndDate().before(new Date())) {
                taskQueue.poll(); // Removes the expired task
            } else {
                break; // The first task in the queue is not expired, so no further tasks will be expired
            }
        }
    }
    
    public static void initializeManager(Context setterContext, Activity setterActivity) {
        tasksList = new ArrayList<>();
    }
    
    public static boolean isNull() {
        return tasksList == null;
    }
    
    // changed from void to returning the created task
    public static TaskObject addTask(boolean checked, String username, Date startDate, Date endDate, String locationName, String locationAddress, String title, String description, String photoLink) {
        nextId = nextId + 1;
        TaskObject taskObject = new TaskObject(nextId, checked, username, startDate, endDate, locationName, locationAddress, title, description, photoLink);
        tasksList.add(taskObject);
        
        Log.i("Info ", taskObject.getTitle());
        
        // Updating the database and RecyclerView to show changes
        db.addTaskToDatabase(taskObject);
        
        addTaskToQueue(taskObject);
        removeExpiredTasks();
        return (taskObject);
    }
    
    public static void removeTask(TaskObject taskObject) {
        tasksList.remove(taskObject);
        removeTaskFromQueue(taskObject);
        // Updating the database and RecyclerView to show changes
        db.deleteTaskFromDatabase(taskObject);
    }
    
    public static void modifiedTask(TaskObject taskObject) {
        Log.i("Info", "Task modified");
        db.updateTaskInDatabase(taskObject);
    }
    
    public static void readTasksFromDatabase(Context context, String username) {
        db = new DatabaseHandler(context, username);
        setTasksList(db.getTasksFromDatabase());
        
        // The ID of the next new task made will be +1 of
        // the users current highest taskID. For uniqueness
        for (TaskObject taskObject : tasksList) {
            if (taskObject.getId() > nextId) {
                nextId = taskObject.getId();
            }
            addTaskToQueue(taskObject);
            removeExpiredTasks();
        }
    }
    
    public static void setTasksList(ArrayList<TaskObject> setterTasksList) {
        tasksList = setterTasksList;
    }
    
    public static List<TaskObject> getTasksList() {
        return tasksList;
    }
    
    public static TaskObject getTaskObject(int position) {
        return tasksList.get(position);
    }
    
    public static TaskObject getTaskByFileName(String fileName) {
        for (TaskObject task : tasksList){
            if (task.getPhotoLink().equals(fileName)){
                return task;
            }
        }
        return null;
    }
}