package com.example.caloriecount;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserList extends ArrayAdapter<UserData> {
    private Activity context;
    private List<UserData> userDataList;

    public UserList(Activity context, List<UserData> userDataList){
        super(context,R.layout.list_layout,userDataList);
        this.context=context;
        this.userDataList=userDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();

        View listViewItem= inflater.inflate(R.layout.list_layout,null ,true);
        TextView ViewName= (TextView) listViewItem.findViewById(R.id.ViewName);
        TextView ViewAge= (TextView) listViewItem.findViewById(R.id.ViewAge);
        TextView ViewCalorie= (TextView) listViewItem.findViewById(R.id.ViewCalorie);

        UserData userData= userDataList.get(position);

        ViewName.setText(userData.getUserName());
        ViewAge.setText(userData.getUserAge());
        ViewCalorie.setText(userData.getUserCalorie());

        return listViewItem;

    }
}
