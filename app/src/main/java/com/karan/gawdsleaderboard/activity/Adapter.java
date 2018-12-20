package com.karan.gawdsleaderboard.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karan.gawdsleaderboard.R;
import com.karan.gawdsleaderboard.model.Repository;

import java.util.List;

public class Adapter extends ArrayAdapter<Repository> {
    private Context context;
    private List<Repository> values;

    public Adapter(Context context, List<Repository> values){
        super(context,R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView textView = (TextView) row.findViewById(R.id.list_item_pagination_text);

        Repository item = values.get(position);
        String message = item.getName();
        textView.setText(message);
        return row;

    }
}
