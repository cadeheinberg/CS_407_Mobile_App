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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        if(currentTask == null){
            String taskInfoText = "No Title Found"  + "\n" + "No Time Found";
            taskInfo.setText(taskInfoText);
        }
        else{
            String taskInfoText = currentTask.getTitle()  + "\n" + formatDateTime(currentTask.getEndDate());
            taskInfo.setText(taskInfoText);
        }
        return view;
    }

    private String formatDateTime(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd HH:mm", Locale.getDefault());
            return getMonthFormat(date.getMonth()) + dateFormat.format(date);
        } else {
            return "Any Time";
        }
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
