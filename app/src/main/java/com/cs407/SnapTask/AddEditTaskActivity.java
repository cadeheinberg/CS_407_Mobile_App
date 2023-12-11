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
                    // editing task
                    TaskObject taskToEdit;
                    // Get the existing task
                    taskToEdit = TaskManager.getTaskObject(positionInList);

                    // Remove the existing task from the queue
                    TaskManager.removeTaskFromQueue(taskToEdit);

                    // Update the task details
                    String title = editTextTaskContent.getText().toString();
                    taskToEdit.setTitle(title);
                    // TODO: Update other properties of taskToEdit as necessary

                    // Add the updated task back to the queue
                    TaskManager.addTaskToQueue(taskToEdit);
                }
                goToTasksActivity();
            }
        });
        
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteTask);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionInList != -1) {
                    TaskObject taskToDelete = TaskManager.getTaskObject(positionInList);

                    // Remove the task from the TaskManager
                    TaskManager.removeTask(taskToDelete);

                    // Also remove it from the queue
                    TaskManager.removeTaskFromQueue(taskToDelete);
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