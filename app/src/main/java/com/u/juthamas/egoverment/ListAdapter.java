package com.u.juthamas.egoverment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    List<String> data;

    ListAdapter(Context context, List<String> data) {
        ctx = context;
        this.data = data;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_row, parent, false);
        }
        view.setBackgroundResource(R.drawable.artists_list_backgroundcolor);

        ((TextView) view.findViewById(R.id.lsText)).setText(data.get(position));
        return view;
    }
}
