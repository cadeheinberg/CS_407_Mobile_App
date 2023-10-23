package com.cs407.cs407mobileapp.TasksRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.cs407mobileapp.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    Context context;
    List<TaskObject> taskObjects;

    public TaskAdapter(Context context, List<TaskObject> taskObjects) {
        this.context = context;
        this.taskObjects = taskObjects;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.title.setText(taskObjects.get(position).getTitle());
        holder.date.setText(taskObjects.get(position).getDate());
        holder.checkbox.setChecked(taskObjects.get(position).isChecked());
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
