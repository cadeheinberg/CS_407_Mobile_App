package com.cs407.cs407mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cs407.cs407mobileapp.TasksRecyclerView.BaseActivity;

public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
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