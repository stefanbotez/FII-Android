package com.example.lab2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab2.R;
import com.example.lab2.Models.RowModel;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends ArrayAdapter<RowModel> {

    private Context mContext;
    private List<RowModel> myList = new ArrayList<>();

    public CustomAdapter(@NonNull Context context, ArrayList<RowModel> list) {
        super(context, 0 , list);
        mContext = context;
        myList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row,parent,false);

        RowModel currentRow = myList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.icon);
        image.setImageResource(currentRow.getImage());

        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(currentRow.getTitle());

        TextView description = (TextView) listItem.findViewById(R.id.desc);
        description.setText(currentRow.getDescription());

        return listItem;
    }
}