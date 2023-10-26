package com.cs407.cs407mobileapp.TasksRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.cs407mobileapp.AddEditTaskActivity;
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
        TaskInListViewHolder tlvh =  new TaskInListViewHolder(LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false));
        tlvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the position of the item that was clicked.
                int position = tlvh.getAbsoluteAdapterPosition();

                // Get the data for the item that was clicked.
                Intent intent = new Intent(view.getContext(), AddEditTaskActivity.class);

                // Add the task data to the intent.
                intent.putExtra("positionInList", position);

                // Start the new activity.
                view.getContext().startActivity(intent);
            }
        });
        return tlvh;
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
