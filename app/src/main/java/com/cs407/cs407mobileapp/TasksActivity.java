package com.cs407.cs407mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs407.cs407mobileapp.Database.DatabaseHandler;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskAdapter;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskInListViewHolder;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskManager;
import com.cs407.cs407mobileapp.TasksRecyclerView.TaskObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static RecyclerView tasksRecyclerView;
    private static TaskAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Today");

        // Get username from shared preferences
        this.sharedPreferences = getSharedPreferences("cs407mobileapp", Context.MODE_PRIVATE);
        String foundUsername = this.sharedPreferences.getString("username", "");

        if(TaskManager.isNull()){
            // Populate tasksList from database
            TaskManager.initializeManager(TasksActivity.this, this);
            TaskManager.readTasksFromDatabase(this, foundUsername);
        }

        FloatingActionButton buttonAddTask = findViewById(R.id.tasksButtonNewTask);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddEditTaskActivity(-1);
            }
        });

        tasksRecyclerView = findViewById(R.id.tasksRecylerViewToday);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TaskAdapter(this, TaskManager.getTasksList());
        tasksRecyclerView.setAdapter(tasksAdapter);
    }

    public void goToAddEditTaskActivity(int taskId){
        Intent intentAddEditTask = new Intent(getApplicationContext(), AddEditTaskActivity.class);
        intentAddEditTask.putExtra("positionInList", taskId);
        startActivity(intentAddEditTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
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

    //Creating a sample Task and adding it to the list
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        String startStrDate = dateFormat.format(new Date());
//        Date endDate = new Date();
//        endDate.setTime(new Date().getTime() + 3600000);
//        String endStrDate = dateFormat.format(endDate);
//        String locationName = "Nick Gym";
//        String locationAddress = "12345 Easy Street Madison Wisconsin";
//        String title = "Meet up with John for a workout";
//        String description = "Make sure you pack protein powder and extra water";
//        TaskManager.addTask(false,
//                foundUsername,
//                startStrDate, endStrDate,
//                locationName,
//                locationAddress,
//                title,
//                description);

}