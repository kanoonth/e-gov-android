package com.kanoon.egov.activity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kanoon.egov.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter{
    private Context ctx;
    private LayoutInflater lInflater;
    private List<String> data;
    private List<String> temp;

    ListAdapter(Context context, List<String> data) {
        ctx = context;
        this.data = data;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        temp = new ArrayList<String>();
        temp.addAll(data);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        } else {
            view.setBackgroundResource(R.drawable.artists_list_backgroundcolor);

        }


        ((TextView) view.findViewById(R.id.lsText)).setText(data.get(position));
        return view;
    }

    public void filterData(String query){
        data.clear();
        if(query.isEmpty()){
            data.addAll(temp);
        }
        else{
            query = query.toLowerCase();
            for(String q : temp){
                if(q.toLowerCase().contains(query)){
                    data.add(q);
                }
            }
        }
        notifyDataSetChanged();
    }
}
