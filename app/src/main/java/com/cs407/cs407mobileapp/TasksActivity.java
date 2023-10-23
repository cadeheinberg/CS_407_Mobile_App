package com.cs407.cs407mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.cs407.cs407mobileapp.TasksRecyclerView.TaskAdapter;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskObject;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;

    private TaskAdapter tasksAdapter;
    private List<TaskObject> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getSupportActionBar().hide();

        tasksList = new ArrayList<>();
        tasksList.add(new TaskObject(1, false, "Go to the gym and workout", "9:00am - 10:00am"));
        tasksList.add(new TaskObject(2, false, "Attend COMP SCI 407 Lecture", "10:30am - 12:15pm"));
        tasksList.add(new TaskObject(3, false, "Get lunch with John Doe", "12:30pm - 1:15pm"));
        tasksList.add(new TaskObject(4, false, "Get a second lunch with John Doe", "1:30pm - 2:15pm"));
        tasksList.add(new TaskObject(5, false, "Get a THIRD lunch John Doe", "2:30pm - 3:15pm"));
        tasksList.add(new TaskObject(6, false, "Do homework at the CS Building", "4:30pm - 6:30pm"));
        tasksList.add(new TaskObject(7, false, "Make dinner and do laundry", "7:00pm - 8:30pm"));
        tasksList.add(new TaskObject(8, false, "Go to the gym and workout", "9:00am - 10:00am"));
        tasksList.add(new TaskObject(9, false, "Attend COMP SCI 407 Lecture", "10:30am - 12:15pm"));
        tasksList.add(new TaskObject(10, false, "Get lunch with John Doe", "12:30pm - 1:15pm"));
        tasksList.add(new TaskObject(11, false, "Get a second lunch with John Doe", "1:30pm - 2:15pm"));
        tasksList.add(new TaskObject(12, false, "Get a THIRD lunch John Doe", "2:30pm - 3:15pm"));
        tasksList.add(new TaskObject(13, false, "Do homework at the CS Building", "4:30pm - 6:30pm"));
        tasksList.add(new TaskObject(14, false, "Make dinner and do laundry", "7:00pm - 8:30pm"));
        tasksList.add(new TaskObject(15, false, "Go to the gym and workout", "9:00am - 10:00am"));
        tasksList.add(new TaskObject(16, false, "Attend COMP SCI 407 Lecture", "10:30am - 12:15pm"));
        tasksList.add(new TaskObject(17, false, "Get lunch with John Doe", "12:30pm - 1:15pm"));
        tasksList.add(new TaskObject(18, false, "Get a second lunch with John Doe", "1:30pm - 2:15pm"));
        tasksList.add(new TaskObject(19, false, "Get a THIRD lunch John Doe", "2:30pm - 3:15pm"));
        tasksList.add(new TaskObject(20, false, "Do homework at the CS Building", "4:30pm - 6:30pm"));
        tasksList.add(new TaskObject(21, false, "Make dinner and do laundry", "7:00pm - 8:30pm"));

        tasksRecyclerView = findViewById(R.id.tasksRecylerViewToday);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new TaskAdapter(this, tasksList);
        tasksRecyclerView.setAdapter(tasksAdapter);

        //use this to update tasks on screen in future.
        //tasksAdapter.updateTasks(tasksList);
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