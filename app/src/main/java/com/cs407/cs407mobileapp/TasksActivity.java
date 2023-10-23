package com.cs407.cs407mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.cs407.cs407mobileapp.Database.DatabaseHandler;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskAdapter;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;

    private TaskAdapter tasksAdapter;
    SharedPreferences sharedPreferences;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getSupportActionBar().hide();

        // Get username from shared preferences
        this.sharedPreferences = getSharedPreferences("cs407mobileapp", Context.MODE_PRIVATE);
        String foundUsername = this.sharedPreferences.getString("username", "");

        // Populate tasksList from database
        TaskObject.setTasksList(new ArrayList<>());
        this.db = new DatabaseHandler(this, foundUsername);
        TaskObject.setTasksList(this.db.getTasksFromDatabase());

        // The ID of the next new task made will be +1 of
        // the users current highest taskID. For uniqueness
        TaskObject.setNextId();

        // Show tasksList in RecyclerView
        this.tasksRecyclerView = findViewById(R.id.tasksRecylerViewToday);
        this.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.tasksAdapter = new TaskAdapter(this, TaskObject.getTasksList());
        this.tasksRecyclerView.setAdapter(this.tasksAdapter);

        // Creating a sample Task and adding it to the list
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String startStrDate = dateFormat.format(new Date());
        Date endDate = new Date();
        endDate.setTime(new Date().getTime() + 3600000);
        String endStrDate = dateFormat.format(endDate);
        String locationName = "Nick Gym";
        String locationAddress = "12345 Easy Street Madison Wisconsin";
        String title = "Meet up with John for a workout";
        String description = "Make sure you pack protein powder and extra water";
        TaskObject firstTask = new TaskObject(null, false,
                foundUsername,
                startStrDate, endStrDate,
                locationName,
                locationAddress,
                title,
                description);
        TaskObject.getTasksList().add(firstTask);

        // Updating the database and RecyclerView to show changes
        db.addTaskToDatabase(firstTask);
        tasksAdapter.updateTasks(TaskObject.getTasksList());
    }

    private void goToTasksActivity(){
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    private void goToCameraActivity(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    private void goToGalleryActivity(){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}