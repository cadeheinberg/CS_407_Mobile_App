package com.cs407.SnapTask;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs407.SnapTask.Database.DatabaseHandler;
import com.cs407.SnapTask.TasksRecyclerView.TaskAdapter;
import com.cs407.SnapTask.TasksRecyclerView.TaskInListViewHolder;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;
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
        this.sharedPreferences = getSharedPreferences("SnapTask", Context.MODE_PRIVATE);
        String foundUsername = this.sharedPreferences.getString("username", "");
        
        if (TaskManager.isNull()) {
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
    
    public void goToAddEditTaskActivity(int taskId) {
        Intent intentAddEditTask = new Intent(getApplicationContext(), AddEditTaskActivity.class);
        intentAddEditTask.putExtra("positionInList", taskId);
        startActivity(intentAddEditTask);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.nav_tasks){
            goToTasksActivity();
        } else if(itemId == R.id.nav_camera){
            goToCameraActivity();
        } else if(itemId == R.id.nav_gallery){
            goToGalleryActivity();
        } else if(itemId == R.id.nav_settings){
            goToSettingsActivity();
        }
        return true;
    }
    
    private void goToTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }
    
    private void goToCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    
    private void goToGalleryActivity() {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    private void goToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}