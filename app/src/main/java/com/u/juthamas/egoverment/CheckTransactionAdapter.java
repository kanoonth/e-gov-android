package com.u.juthamas.egoverment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckTransactionAdapter extends ArrayAdapter<String>{
    private String[] datas;
    Context context;
    public CheckTransactionAdapter(Context context, int resource, String[] datas) {
        super(context, resource, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_check_transaction, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.tvCheckTran);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxCheckTran);
        name.setText(datas[position]);
        return convertView;
    }
}
