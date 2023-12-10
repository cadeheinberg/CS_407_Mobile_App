package com.cs407.SnapTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cs407.SnapTask.TasksRecyclerView.TaskAdapter;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksActivity extends AppCompatActivity {
    
    SharedPreferences sharedPreferences;
    
    private static RecyclerView tasksRecyclerView;
    private static TaskAdapter tasksAdapter;
    
    private static final int PERMISSIONS_REQUEST_WRITE = 43;
    private static final int PERMISSIONS_REQUEST_READ = 44;
    
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
        buttonAddTask.setOnClickListener(v -> goToAddEditTaskActivity(-1));
        
        tasksRecyclerView = findViewById(R.id.tasksRecyclerViewToday);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TaskAdapter(this, TaskManager.getTasksList());
        tasksRecyclerView.setAdapter(tasksAdapter);
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE);
        }
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == PERMISSIONS_REQUEST_WRITE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(this, "Write Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Write Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSIONS_REQUEST_READ) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(this, "Read Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Read Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_tasks) {
            goToTasksActivity();
        } else if (itemId == R.id.nav_camera) {
            goToCameraActivity();
        } else if (itemId == R.id.nav_gallery) {
            goToGalleryActivity();
        } else if (itemId == R.id.nav_settings) {
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