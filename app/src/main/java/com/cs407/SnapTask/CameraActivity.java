package com.cs407.SnapTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
    
    PreviewView previewView;
    private ImageCapture imageCapture;
    
    
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
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
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
        Button recordButton = findViewById(R.id.recordButton);
        
        pictureButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        
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
    
    private void displayCurrentTask() {
        TaskObject currentTask = TaskManager.getNextTask();
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            return dateFormat.format(date);
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
            imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(photoFile).build(), getExecutor(),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
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
}