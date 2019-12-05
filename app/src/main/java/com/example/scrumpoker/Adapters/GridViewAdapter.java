package com.example.scrumpoker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scrumpoker.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final int[] values;

    // 1
    public GridViewAdapter(Context context, int[] vals) {
        this.mContext = context;
        this.values = vals;
    }

    // 2
    @Override
    public int getCount() {
        return values.length;//.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int value = values[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
        }
        final TextView nameTextView = convertView.findViewById(R.id.grid_value);

        nameTextView.setText(String.valueOf(value));
        return convertView;
    }
}
