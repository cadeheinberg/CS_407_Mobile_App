package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {

    private DatePickerDialog startDatePickerDialog, endDatePickerDialog;
    private Button startTimeButton, endTimeButton, startDateButton, endDateButton;
    private Date startDateChosen, endDateChosen;
    private TaskObject currentTaskObject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        startTimeButton = findViewById(R.id.buttonSetTimeStart);
        startDateButton = findViewById(R.id.buttonSetDateStart);
        endTimeButton = findViewById(R.id.buttonSetTimeEnd);
        endDateButton = findViewById(R.id.buttonSetDateEnd);
        initEditMenu();
        initDateTimePickers();
    }

    private void initEditMenu() {
        EditText editTextTaskContent = findViewById(R.id.editTextTaskInput);
        String foundUsername = getSharedPreferences("SnapTask", Context.MODE_PRIVATE).getString("username", "");
        Intent intent = getIntent();
        int positionInList = intent.getIntExtra("positionInList", -1);
        currentTaskObject = null;
        if (positionInList != -1) {
            currentTaskObject = TaskManager.getTaskObject(positionInList);
            editTextTaskContent.setText(currentTaskObject.getTitle());
        }

        Button saveButton = findViewById(R.id.buttonSaveTask);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionInList == -1) {
                    // make a new task
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
                    // Remove the existing task from the queue
                    TaskManager.removeTaskFromQueue(currentTaskObject);
                    // Update the task details
                    String title = editTextTaskContent.getText().toString();
                    currentTaskObject.setTitle(title);
                    currentTaskObject.setStartDate(startDateChosen);
                    currentTaskObject.setEndDate(endDateChosen);
                    // TODO: Update other properties of taskToEdit as necessary
                    // Add the updated task back to the queue
                    TaskManager.addTaskToQueue(currentTaskObject);
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

    private void initDateTimePickers() {
        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = makeDateString(day, month, year);
                startDateButton.setText(date);
                startDateChosen.setDate(day);
                startDateChosen.setMonth(month);
                startDateChosen.setYear(year);
            }
        };
        if (currentTaskObject != null) {
            startDateChosen = currentTaskObject.getStartDate();
        }
        else {
            startDateChosen = new Date();
            Calendar startCal = Calendar.getInstance();
            int startHour = startCal.get(Calendar.HOUR);
            int startMinute = startCal.get(Calendar.MINUTE);
            int startYear = startCal.get(Calendar.YEAR);
            int startMonth = startCal.get(Calendar.MONTH);
            int startDay = startCal.get(Calendar.DAY_OF_MONTH);
            startDateChosen.setHours(startHour);
            startDateChosen.setMinutes(startMinute);
            startDateChosen.setDate(startDay);
            startDateChosen.setMonth(startMonth);
            startDateChosen.setYear(startYear);
        }
        startDatePickerDialog = new DatePickerDialog(this, style, startDateSetListener, startDateChosen.getYear(), startDateChosen.getMonth(), startDateChosen.getDate());

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = makeDateString(day, month, year);
                endDateButton.setText(date);
                endDateChosen.setDate(day);
                endDateChosen.setMonth(month);
                endDateChosen.setYear(year);
            }
        };
        if (currentTaskObject != null) {
            endDateChosen = currentTaskObject.getEndDate();
        }
        else {
            endDateChosen = new Date();
            Calendar endCal = Calendar.getInstance();
            int endHour = endCal.get(Calendar.HOUR) + 1;
            int endMinute = endCal.get(Calendar.MINUTE);
            int endYear = endCal.get(Calendar.YEAR);
            int endMonth = endCal.get(Calendar.MONTH);
            int endDay = endCal.get(Calendar.DAY_OF_MONTH);
            endDateChosen.setHours(endHour);
            endDateChosen.setMinutes(endMinute);
            endDateChosen.setDate(endDay);
            endDateChosen.setMonth(endMonth);
            endDateChosen.setYear(endYear);
        }
        endDatePickerDialog = new DatePickerDialog(this, style, endDateSetListener, endDateChosen.getYear(), endDateChosen.getMonth(), endDateChosen.getDate());

        startDateButton.setText(makeDateString(startDateChosen.getDate(), startDateChosen.getMonth(), startDateChosen.getYear()));
        endDateButton.setText(makeDateString(endDateChosen.getDate(), endDateChosen.getMonth(), endDateChosen.getYear()));
        startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", startDateChosen.getHours(), startDateChosen.getMinutes()));
        endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", endDateChosen.getHours(), endDateChosen.getMinutes()));
    }

    public void popStartTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                startDateChosen.setHours(hourOfDay);
                startDateChosen.setMinutes(minute);
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, startDateChosen.getHours(), startDateChosen.getMinutes(), true);
        timePickerDialog.show();
    }

    public void popEndTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                endDateChosen.setHours(hourOfDay);
                endDateChosen.setMinutes(minute);
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, endDateChosen.getHours(), endDateChosen.getMinutes(), true);
        timePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private void goToTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    private String getMonthFormat(int month)
    {
        if(month == 0)
            return "JAN";
        if(month == 1)
            return "FEB";
        if(month == 2)
            return "MAR";
        if(month == 3)
            return "APR";
        if(month == 4)
            return "MAY";
        if(month == 5)
            return "JUN";
        if(month == 6)
            return "JUL";
        if(month == 7)
            return "AUG";
        if(month == 8)
            return "SEP";
        if(month == 9)
            return "OCT";
        if(month == 10)
            return "NOV";
        if(month == 11)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void popStartDatePicker(View view) {
        startDatePickerDialog.show();
    }

    public void popEndDatePicker(View view){
        endDatePickerDialog.show();
    }
}