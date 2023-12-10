package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.ListView;

import com.cs407.SnapTask.GalleryListView.ListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    
    ListView galleryListView;
    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ListAdapter listAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setTitle("Gallery");
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_gallery).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_tasks) {
                startActivity(new Intent(this, TasksActivity.class));
            } else if (itemId == R.id.nav_camera) {
                startActivity(new Intent(this, CameraActivity.class));
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            }
            return true;
        });
        
        galleryListView = findViewById(R.id.galleryListView);
        listAdapter = new ListAdapter(GalleryActivity.this, thumbnails);
        galleryListView.setAdapter(listAdapter);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] pictures = storageDir.listFiles();
        thumbnails.clear();
        for (File picture : pictures) {
            if (picture == null) {
                break;
            }
            thumbnails.add(BitmapFactory.decodeFile(picture.getAbsolutePath()));
        }
        listAdapter.notifyDataSetChanged();
    }
}