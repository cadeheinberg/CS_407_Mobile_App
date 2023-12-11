package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cs407.SnapTask.Database.DatabaseHandler;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {

    Button startTimeButton, endTimeButton;
    int startHour, startMinute, endHour, endMinute = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        EditText editTextTaskContent = findViewById(R.id.editTextTaskInput);

        startTimeButton = findViewById(R.id.buttonSetTimeStart);
        endTimeButton = findViewById(R.id.buttonSetTimeEnd);

        String foundUsername = getSharedPreferences("SnapTask", Context.MODE_PRIVATE).getString("username", "");
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
                    // DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    // String startStrDate = dateFormat.format(new Date());
                    // endDate.setTime(new Date().getTime() + 3600000);
                    // String endStrDate = dateFormat.format(endDate); // remove notnull tag from parameter if used
                    Date startDate = new Date();
                    if(startHour == -1 || startMinute == -1){
                        Toast.makeText(AddEditTaskActivity.this, "Select A Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startDate.setHours(startHour);
                    startDate.setMinutes(startMinute);
                    Date endDate = new Date();
                    if(endHour == -1 || endMinute == -1){
                        Toast.makeText(AddEditTaskActivity.this, "Select A Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    endDate.setHours(endHour);
                    endDate.setMinutes(endMinute);
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
                    TaskManager.removeExpiredTasks();
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

    public void popStartTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startHour = hourOfDay;
                startMinute = minute;
                startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, startHour, startMinute, true);
        timePickerDialog.show();
    }

    public void popEndTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endHour = hourOfDay;
                endMinute = minute;
                endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, endHour, endMinute, true);
        timePickerDialog.show();
    }
}