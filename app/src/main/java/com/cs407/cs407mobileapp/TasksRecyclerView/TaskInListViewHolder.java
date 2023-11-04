package com.cs407.cs407mobileapp.TasksRecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cs407.cs407mobileapp.R;

public class TaskInListViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkbox;
    public TextView title;
    public TextView startDate;
    public TextView endDate;
    public TextView locationName;

    public TaskInListViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.taskTitleText);
        checkbox = itemView.findViewById(R.id.taskToDoCheckBox);
        locationName = itemView.findViewById(R.id.taskLocationName);
        startDate = itemView.findViewById(R.id.taskStartDateText);
        endDate = itemView.findViewById(R.id.taskEndDateText);
    }

    //        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, TaskManager.getTasksList());
//        ListView taskListItem = findViewById(R.id.tasksRecylerViewToday);
//        taskListItem.setAdapter(adapter);
//        taskListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                goToAddEditTaskActivity(position);
//            }
//        });
}