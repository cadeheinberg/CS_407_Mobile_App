package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    public static ArrayList<Bitmap> thumbnails = new ArrayList<Bitmap>();
    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Gallery");

        imageView1 = findViewById(R.id.galleryImageView1);
        imageView2 = findViewById(R.id.galleryImageView2);

        if(thumbnails.size() >= 1 && thumbnails.get(0) != null){
            imageView1.setImageBitmap(thumbnails.get(0));
        }
        if(thumbnails.size() >= 2 && thumbnails.get(1) != null){
            imageView2.setImageBitmap(thumbnails.get(1));
        }
    }

    //This is the camera branch

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.nav_tasks){
            goToTasksActivity();
        } else if(itemId == R.id.nav_camera){
            goToCameraActivity();
        } else if(itemId == R.id.nav_gallery){
            goToGalleryActivity();
        } else if(itemId == R.id.nav_settings){
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