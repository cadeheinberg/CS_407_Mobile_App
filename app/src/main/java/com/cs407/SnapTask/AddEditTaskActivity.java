package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cs407.SnapTask.Database.DatabaseHandler;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {

    Button startTimeButton, endTimeButton;
    int startHour, startMinute, endHour, endMinute = -1;

    private DatePickerDialog startDatePickerDialog, endDatePickerDialog;
    private Button startDateButton, endDateButton;
    private Date startDateChosen, endDateChosen = new Date();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        EditText editTextTaskContent = findViewById(R.id.editTextTaskInput);

        startTimeButton = findViewById(R.id.buttonSetTimeStart);
        endTimeButton = findViewById(R.id.buttonSetTimeEnd);
        startDateChosen = new Date();

        startDateButton = findViewById(R.id.buttonSetDateStart);
        endDateButton = findViewById(R.id.buttonSetDateEnd);
        endDateChosen = new Date();
        initDatePickers();

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
                    if(startHour == -1 || startMinute == -1){
                        Toast.makeText(AddEditTaskActivity.this, "Select A Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(endHour == -1 || endMinute == -1){
                        Toast.makeText(AddEditTaskActivity.this, "Select A Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String locationName = "Starbucks";
                    String locationAddress = "54312 Main St Chicago Illinois";
                    String title = editTextTaskContent.getText().toString();
                    String description = "No description yet";
                    TaskObject newTask = TaskManager.addTask(false, // creates task, adds it to manager, and returns it
                            foundUsername,
                            startDateChosen, endDateChosen,
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

    private void setTodaysDateInPickers() {
        startDateButton.setText(makeDateString(startDateChosen.getDate(), startDateChosen.getMonth(), startDateChosen.getYear()));
        endDateButton.setText(makeDateString(endDateChosen.getDate(), endDateChosen.getMonth(), endDateChosen.getYear()));
        startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", startDateChosen.getHours(), endDateChosen.getMinutes()));
        endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", endDateChosen.getHours() + 1, endDateChosen.getMinutes()));
    }

    private void initDatePickers() {
        int style = AlertDialog.THEME_HOLO_DARK;

        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                startDateButton.setText(date);
                startDateChosen.setDate(day);
                startDateChosen.setMonth(month);
                startDateChosen.setYear(year);
            }
        };
        Calendar startCal = Calendar.getInstance();
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH);
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
        startDateChosen.setDate(startDay);
        startDateChosen.setMonth(startMonth);
        startDateChosen.setYear(startYear);
        startDatePickerDialog = new DatePickerDialog(this, style, startDateSetListener, startYear, startMonth, startDay);

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                endDateButton.setText(date);
                endDateChosen.setDate(day);
                endDateChosen.setMonth(month);
                endDateChosen.setYear(year);
            }
        };
        Calendar endCal = Calendar.getInstance();
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH);
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
        endDateChosen.setDate(endDay);
        endDateChosen.setMonth(endMonth);
        endDateChosen.setYear(endYear);
        endDatePickerDialog = new DatePickerDialog(this, style, endDateSetListener, endYear, endMonth, endDay);

        setTodaysDateInPickers();
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
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
                startDateChosen.setHours(startHour);
                startDateChosen.setMinutes(startMinute);
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
                endDateChosen.setHours(endHour);
                endDateChosen.setMinutes(endMinute);
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, endHour, endMinute, true);
        timePickerDialog.show();
    }

    public void popStartDatePicker(View view) {
        startDatePickerDialog.show();
    }

    public void popEndDatePicker(View view){
        endDatePickerDialog.show();
    }
}