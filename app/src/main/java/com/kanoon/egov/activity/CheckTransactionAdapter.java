package com.kanoon.egov.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kanoon.egov.R;

public class CheckTransactionAdapter extends ArrayAdapter<String>{
    private String[] datas;
    private Context context;
    private Editor editor;

    public CheckTransactionAdapter(Context context, int resource, String[] datas) {
        super(context, resource, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        SharedPreferences shared = context.getSharedPreferences("shared", Context.MODE_PRIVATE);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_check_transaction, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.tvCheckTran);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxCheckTran);
        name.setText(datas[position]);

        editor = shared.edit();

        cb.setChecked(shared.getBoolean("checkValue" + position,false));
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("checkValue" + pos, isChecked);
                editor.commit();
            }
        });

        return convertView;
    }

}
