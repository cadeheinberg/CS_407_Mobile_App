package com.cs407.SnapTask.TasksRecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cs407.SnapTask.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNav;
    
    // this is a test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        bottomNav = findViewById(R.id.bottom_navigation);
        // TODO: fix if statements
//        bottomNav.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_tasks) {
//                Log.i("Info", "Test");
//                if (!(this instanceof TasksActivity)) {
//                    startActivity(new Intent(this, TasksActivity.class));
//                }
//                return true;
//            } else if (itemId == R.id.nav_camera) {
//                if (!(this instanceof CameraActivity)) {
//                    startActivity(new Intent(this, CameraActivity.class));
//                }
//                return true;
//            } else if (itemId == R.id.nav_gallery) {
//                if (!(this instanceof GalleryActivity)) {
//                    startActivity(new Intent(this, GalleryActivity.class));
//                }
//                return true;
//            } else {
//                return false;
//            }
//        });
    
    
    }
    
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup contentView = findViewById(R.id.activity_content);
        inflater.inflate(layoutResID, contentView, true);
    }
    
    
}