package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.cs407.SnapTask.GalleryListView.ListAdapter;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    
    ListView galleryListView;
    ArrayList<Bitmap> thumbnails = new ArrayList<Bitmap>();
    ListAdapter listAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Gallery");
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
        for (int i = 0; i < pictures.length; i++) {
            if (pictures[i] == null) {
                break;
            }
            thumbnails.add(BitmapFactory.decodeFile(pictures[i].getAbsolutePath()));
        }
        listAdapter.notifyDataSetChanged();
    }
    
    //This is the camera branch
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_tasks) {
            goToTasksActivity();
        } else if (itemId == R.id.nav_camera) {
            goToCameraActivity();
        } else if (itemId == R.id.nav_gallery) {
            goToGalleryActivity();
        } else if (itemId == R.id.nav_settings) {
            goToSettingsActivity();
        }
        return true;
    }
    
    private void goToTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }
    
    private void goToCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    
    private void goToGalleryActivity() {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }
    
    private void goToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    
}