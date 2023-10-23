package com.cs407.cs407mobileapp.TasksRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.cs407mobileapp.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskInListViewHolder> {

    Context context;
    List<TaskObject> taskObjects;

    public TaskAdapter(Context context, List<TaskObject> taskObjects) {
        this.context = context;
        this.taskObjects = taskObjects;
    }

    @NonNull
    @Override
    public TaskInListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskInListViewHolder(LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskInListViewHolder holder, int position) {
        holder.title.setText(taskObjects.get(position).getTitle());
        holder.checkbox.setChecked(taskObjects.get(position).isChecked());
        holder.locationName.setText(taskObjects.get(position).getLocationName());
        holder.startDate.setText(taskObjects.get(position).getStartDate());
        holder.endDate.setText(taskObjects.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return taskObjects.size();
    }

    public void updateTasks(List<TaskObject> taskObjects){
        this.taskObjects = taskObjects;
        notifyDataSetChanged();
    }
}
