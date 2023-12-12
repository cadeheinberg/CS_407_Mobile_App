package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
        Log.i("Info", "storageDir: " + storageDir.toString());
        File[] pictures = storageDir.listFiles();
        thumbnails.clear();
        for (File picture : pictures) {
            if (picture == null) {
                break;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap photoBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath());
            Bitmap rotatedBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, photoBitmap.getWidth(), photoBitmap.getHeight(), matrix, true);
            thumbnails.add(rotatedBitmap);
        }
        listAdapter.notifyDataSetChanged();
    }
}