package com.cs407.SnapTask.TasksRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.SnapTask.AddEditTaskActivity;
import com.cs407.SnapTask.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        TaskInListViewHolder tlvh = new TaskInListViewHolder(LayoutInflater.from(context).inflate(R.layout.single_task_layout, parent, false));
        tlvh.itemView.setOnClickListener(view -> {
            // Get the position of the item that was clicked.
            int position = tlvh.getAbsoluteAdapterPosition();
            
            // Get the data for the item that was clicked.
            Intent intent = new Intent(view.getContext(), AddEditTaskActivity.class);
            
            // Add the task data to the intent.
            intent.putExtra("positionInList", position);
            
            // Start the new activity.
            view.getContext().startActivity(intent);
        });
        return tlvh;
    }
    
    @Override
    public void onBindViewHolder(@NonNull TaskInListViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.title.setText(taskObjects.get(position).getTitle());
        holder.checkbox.setChecked(taskObjects.get(position).isChecked());
        holder.locationName.setText(taskObjects.get(position).getLocationName());
        Date startDate = taskObjects.get(position).getStartDate();
        if (startDate != null) {
            holder.startDate.setText(dateFormat.format(startDate));
        } else {
            holder.startDate.setText("Any Time");
        }
        
        Date endDate = taskObjects.get(position).getEndDate();
        if (endDate != null) {
            holder.endDate.setText(dateFormat.format(endDate));
        } else {
            holder.endDate.setText("Any Time");
        }
    }
    
    @Override
    public int getItemCount() {
        return taskObjects.size();
    }
    
    public void updateTasks(List<TaskObject> taskObjects) {
        this.taskObjects = taskObjects;
        notifyDataSetChanged();
    }
}