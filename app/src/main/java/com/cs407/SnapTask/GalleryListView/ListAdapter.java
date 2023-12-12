package com.cs407.SnapTask.GalleryListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs407.SnapTask.R;
import com.cs407.SnapTask.TasksRecyclerView.TaskManager;
import com.cs407.SnapTask.TasksRecyclerView.TaskObject;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> pictureList;
    ArrayList<String> fileNames;
    LayoutInflater layoutInflater;
    
    public ListAdapter(Context context, ArrayList<Bitmap> pictureList, ArrayList<String> fileNames) {
        this.context = context;
        this.pictureList = pictureList;
        this.fileNames = fileNames;
        layoutInflater = LayoutInflater.from(context);
    }
    
    @Override
    public int getCount() {
        return pictureList.size();
    }
    
    @Override
    public Object getItem(int position) {
        return position;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.single_thumbnail, parent, false);
        ImageView imageView = view.findViewById(R.id.gallerySingleThumbnail);
        imageView.setImageBitmap(pictureList.get(position));
        
        // get task matching filename
        TaskObject currentTask = TaskManager.getTaskByFileName(fileNames.get(position));
        // update task info
        TextView taskInfo = view.findViewById(R.id.taskInfo);
        String taskInfoText = currentTask.getTitle() + "\n" + currentTask.getDescription() + "\n" + currentTask.getEndDate();
        taskInfo.setText(taskInfoText);
        return view;
    }
}
