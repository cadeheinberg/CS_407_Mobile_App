package com.cs407.SnapTask.TasksRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.SnapTask.Database.DatabaseHandler;
import com.cs407.SnapTask.R;
import com.cs407.SnapTask.TasksActivity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManager {

    private static PriorityQueue<TaskObject> taskQueue = new PriorityQueue<>();
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

    // call this every minute or however often we want to update the queue based on if
    // its due date is passed
    public static void removeExpiredTasks() {
        while (!taskQueue.isEmpty() && taskQueue.peek().getEndDate().before(new Date())) {
            taskQueue.poll(); // Removes the expired task
        }
    }

    public static void initializeManager(Context setterContext, Activity setterActivity) {
        tasksList = new ArrayList<>();
    }
    
    public static boolean isNull() {
        if (tasksList == null) {
            return true;
        }
        return false;
    }
    
    public static void addTask(boolean checked, String username, Date startDate, Date endDate, String locationName, String locationAddress, String title, String description) {
        nextId = nextId + 1;
        TaskObject taskObject = new TaskObject(nextId, checked, username, startDate, endDate, locationName, locationAddress, title, description);
        tasksList.add(taskObject);
        
        Log.i("Info ", taskObject.getTitle());
        
        // Updating the database and RecyclerView to show changes
        db.addTaskToDatabase(taskObject);
    }
    
    public static void removeTask(TaskObject taskObject) {
        tasksList.remove(taskObject);
        
        // Updating the database and RecyclerView to show changes
        db.deleteTaskFromDatabase(taskObject);
    }
    
    public static void modifiedTask(TaskObject taskObject) {
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
}