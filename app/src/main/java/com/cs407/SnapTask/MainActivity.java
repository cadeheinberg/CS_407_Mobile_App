package com.cs407.SnapTask;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    SharedPreferences sharedPreferences = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");
        setButtonOnClickListeners();
        this.sharedPreferences = getSharedPreferences("SnapTask", Context.MODE_PRIVATE);
    }
    
    private void setButtonOnClickListeners() {
        Button buttonLogin = (Button) findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUsername = (EditText) findViewById(R.id.loginUsername);
                sharedPreferences.edit().putString("username", editTextUsername.getText().toString()).apply();
                goToTasksActivity();
            }
        });
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
    
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
}