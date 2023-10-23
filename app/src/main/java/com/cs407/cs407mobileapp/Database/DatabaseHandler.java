package com.cs407.cs407mobileapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs407.cs407mobileapp.TasksRecyclerView.TaskObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tasks_database";
    private static final String TABLE_NAME = "tasks_table";
    private static final String ID = "id";
    private static final String CHECKED = "checked";
    private static final String USERNAME = "username";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String LOCATION_NAME = "location_name";
    private static final String LOCATION_ADDRESS = "location_address";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static SQLiteDatabase sqLiteDatabase;
    private String currentUsername;
    public DatabaseHandler (Context context, String username){
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        this.sqLiteDatabase = sqLiteDatabase;
        this.currentUsername = username;
    }
    private void createTableInDatabase(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CHECKED + " INTEGER, " +
                USERNAME + " TEXT, " + START_DATE + " TEXT, " + END_DATE + " TEXT, " +
                LOCATION_NAME + " TEXT, " + LOCATION_ADDRESS + " TEXT, " + TITLE + " TEXT, " +
                DESCRIPTION + " TEXT)");
    }

    public ArrayList<TaskObject> getTasksFromDatabase(){
        createTableInDatabase();
        ArrayList<TaskObject> fromDatabaseList = new ArrayList<TaskObject>();

        // Populate tasksList from tasks in the SQL table with
        // a matching username
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " LIKE ?",
                new String[]{"%" + currentUsername + "%"});
        int idHeader = c.getColumnIndex(ID);
        int checkedHeader = c.getColumnIndex(CHECKED);
        int usernameHeader = c.getColumnIndex(USERNAME);
        int startDateHeader = c.getColumnIndex(START_DATE);
        int endDateHeader = c.getColumnIndex(END_DATE);
        int locationNameHeader = c.getColumnIndex(LOCATION_NAME);
        int locationAddressHeader = c.getColumnIndex(LOCATION_ADDRESS);
        int titleHeader = c.getColumnIndex(TITLE);
        int descriptionHeader = c.getColumnIndex(DESCRIPTION);
        c.moveToFirst();
        while(!c.isAfterLast()){
            fromDatabaseList.add(new TaskObject(c.getInt(idHeader), intToBoolean(c.getInt(checkedHeader)),
                    c.getString(usernameHeader), c.getString(startDateHeader), c.getString(endDateHeader),
                    c.getString(locationNameHeader), c.getString(locationAddressHeader), c.getString(titleHeader),
                    c.getString(descriptionHeader)));
            c.moveToNext();
        }
        c.close();

        return fromDatabaseList;
    }

    public void addTaskToDatabase(TaskObject task){
        createTableInDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHECKED, booleanToInt(task.isChecked()));
        cv.put(USERNAME, task.getUsername());
        cv.put(START_DATE, task.getStartDate());
        cv.put(END_DATE, task.getEndDate());
        cv.put(LOCATION_NAME, task.getLocationName());
        cv.put(LOCATION_ADDRESS, task.getLocationAddress());
        cv.put(TITLE, task.getTitle());
        cv.put(DESCRIPTION, task.getDescription());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public void updateTaskInDatabase(TaskObject task){
        createTableInDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHECKED, booleanToInt(task.isChecked()));
        cv.put(USERNAME, task.getUsername());
        cv.put(START_DATE, task.getStartDate());
        cv.put(END_DATE, task.getEndDate());
        cv.put(LOCATION_NAME, task.getLocationName());
        cv.put(LOCATION_ADDRESS, task.getLocationAddress());
        cv.put(TITLE, task.getTitle());
        cv.put(DESCRIPTION, task.getDescription());
        sqLiteDatabase.update(TABLE_NAME, cv, ID + "=? and " + USERNAME + "=?", new String[]{String.valueOf(task.getId()), task.getUsername()});
    }

    public void deleteTaskFromDatabase(TaskObject task){
        createTableInDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + "=? and " + USERNAME + "=?", new String[]{String.valueOf(task.getId()), task.getUsername()});
    }

    private boolean intToBoolean(int n){
        if(n == 1){
            return true;
        }
        return false;
    }

    private int booleanToInt(boolean b){
        if(b){
            return 1;
        }
        return 0;
    }

}
