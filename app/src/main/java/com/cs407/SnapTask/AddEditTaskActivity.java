package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs407.SnapTask.Database.DatabaseHandler;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditTaskActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        EditText editTextTaskContent = findViewById(R.id.editTextTaskInput);
        String foundUsername = getSharedPreferences("lab5_milestone", Context.MODE_PRIVATE).getString("username", "");
        Intent intent = getIntent();
        int positionInList = intent.getIntExtra("positionInList", -1);
        TaskObject taskObject = null;
        if (positionInList != -1) {
            taskObject = TaskManager.getTaskObject(positionInList);
            editTextTaskContent.setText(taskObject.getTitle());
        }
        Button saveButton = findViewById(R.id.buttonSaveTask);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: create default values, then if add populate with them and if edit they should already be populated
                // TODO: but add and edit can be the same after values are populated, except edit should delete old vs after creating new
                // TODO: make it so theres an editTextTaskContent for locations and dates too
                // TODO: basically for edit we wanna add an edited copy the task then delete the old one
                if (positionInList == -1) {
                    // creating a new task
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    String startStrDate = dateFormat.format(new Date());
                    Date endDate = null;
                    //endDate.setTime(new Date().getTime() + 3600000);
                    // String endStrDate = dateFormat.format(endDate); // remove notnull tag from parameter if used
                    Date startDate = new Date();
                    String locationName = "Starbucks";
                    String locationAddress = "54312 Main St Chicago Illinois";
                    String title = editTextTaskContent.getText().toString();
                    String description = "No description yet";
                    TaskObject newTask = TaskManager.addTask(false, // creates task, adds it to manager, and returns it
                            foundUsername,
                            startDate, endDate,
                            locationName,
                            locationAddress,
                            title,
                            description);
                    TaskManager.addTaskToQueue(newTask);
                    TaskManager.removeExpiredTasks();
                    Log.i("info: ", "ADDED NEW TASK");
                } else {
                    // editing a task
                    String title = editTextTaskContent.getText().toString();
                    TaskManager.getTaskObject(positionInList).setTitle(title);
                }
                goToTasksActivity();
            }
        });
        
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteTask);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionInList != -1) {
                    TaskManager.removeTask(TaskManager.getTaskObject(positionInList));
                }
                goToTasksActivity();
            }
        });
    }
    
    private void goToTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }
    
    
}