package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_settings).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_tasks) {
                startActivity(new Intent(this, TasksActivity.class));
            } else if (itemId == R.id.nav_camera) {
                startActivity(new Intent(this, CameraActivity.class));
            } else if (itemId == R.id.nav_gallery) {
                startActivity(new Intent(this, GalleryActivity.class));
            }
            return true;
        });
    }
}