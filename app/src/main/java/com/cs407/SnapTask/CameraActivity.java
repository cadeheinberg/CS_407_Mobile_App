package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.util.Log;

import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private TaskObject currentTask;
    int current_task_being_shown;
    
    //could use any number for these
    private static final int PERMISSIONS_REQUEST_CAMERA = 42;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_tasks) {
                startActivity(new Intent(this, TasksActivity.class));
            } else if (itemId == R.id.nav_gallery) {
                startActivity(new Intent(this, GalleryActivity.class));
            }
            return true;
        });
        
        displayCurrentTask();
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
        
        previewView = findViewById(R.id.cameraPreview);
        Button pictureButton = findViewById(R.id.pictureButton);
        
        pictureButton.setOnClickListener(this);
        
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.next_task, menu);
        return true;
    }
    
    // called whenever an item in your options menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.camera_next_task_button) {
            displayNextTask();
        }
        return true;
    }
    
    private void displayNextTask() {
        current_task_being_shown = (current_task_being_shown + 1) % TaskManager.getSizeOfTaskQueue();
        currentTask = TaskManager.getTaskFromQueueWithID(current_task_being_shown);
        if (currentTask != null) {
            String taskDetails = formatDateTime(currentTask.getEndDate());
            getSupportActionBar().setTitle(currentTask.getTitle());
            getSupportActionBar().setSubtitle(taskDetails);
        } else {
            getSupportActionBar().setTitle("No current tasks");
        }
        
    }
    
    private void displayCurrentTask() {
        currentTask = TaskManager.getNextTask();
        current_task_being_shown = 0;
        if (currentTask != null) {
            String taskDetails = formatDateTime(currentTask.getEndDate());
            getSupportActionBar().setTitle(currentTask.getTitle());
            getSupportActionBar().setSubtitle(taskDetails);
        } else {
            getSupportActionBar().setTitle("No current tasks");
        }
    }
    
    private String formatDateTime(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd HH:mm", Locale.getDefault());
            return getMonthFormat(date.getMonth()) + dateFormat.format(date);
        } else {
            return "Any Time";
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }
    
    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        
        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        
        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        
        imageAnalysis.setAnalyzer(getExecutor(), this);
        
        //bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }
    
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pictureButton) {
            capturePhoto();
        }
    }
    
    private void capturePhoto() {
        File photoFile = null;
        try {
            photoFile = getNewPhotoFilePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            File finalPhotoFile = photoFile;
            imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(finalPhotoFile).build(), getExecutor(),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            String fileName = finalPhotoFile.getName();
                            Log.i("Info", "photoLink: " + fileName);
                            currentTask.setPhotoLink(fileName);
                            Toast.makeText(CameraActivity.this, "Photo has been saved", Toast.LENGTH_SHORT).show();
                        }
                        
                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            Toast.makeText(CameraActivity.this, "Error saving photo", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    
    private File getNewPhotoFilePath() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.US).format(new Date());
        String imageName = "jpg_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        return imageFile;
    }
    
    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        image.close();
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
}