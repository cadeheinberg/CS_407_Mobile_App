package com.cs407.cs407mobileapp.TasksRecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.cs407mobileapp.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkbox;
    EditText title;
    EditText date;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.taskTitleText);
        date = itemView.findViewById(R.id.taskDateText);
        checkbox = itemView.findViewById(R.id.taskToDoCheckBox);
    }
}
