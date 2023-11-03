package com.cs407.cs407mobileapp.TasksRecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cs407.cs407mobileapp.CameraActivity;
import com.cs407.cs407mobileapp.GalleryActivity;
import com.cs407.cs407mobileapp.R;
import com.cs407.cs407mobileapp.TasksActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // assuming your base layout is named activity_base
        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_tasks) {
                if (!(this instanceof TasksActivity)) {
                    startActivity(new Intent(this, TasksActivity.class));
                }
                return true;
            } else if (itemId == R.id.nav_camera) {
                if (!(this instanceof CameraActivity)) {
                    startActivity(new Intent(this, CameraActivity.class));
                }
                return true;
            } else if (itemId == R.id.nav_gallery) {
                if (!(this instanceof GalleryActivity)) {
                    startActivity(new Intent(this, GalleryActivity.class));
                }
                return true;
            } else {
                return false;
            }
        });
    }
}
