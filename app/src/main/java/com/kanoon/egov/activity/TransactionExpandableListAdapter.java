package com.kanoon.egov.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.kanoon.egov.R;

import java.util.ArrayList;

public class TransactionExpandableListAdapter extends BaseExpandableListAdapter{

    private ArrayList<TransactionGroup> groups;
    private ArrayList<TransactionGroup> temp;
    private LayoutInflater inflater;
    private TransactionActivity transaction;

    public TransactionExpandableListAdapter(ArrayList<TransactionGroup> groups,TransactionActivity t){
        this.groups = groups;
        temp = new ArrayList<TransactionGroup>();
        temp.addAll(groups);
        inflater = t.getLayoutInflater();
        transaction = t;
    }
    @Override
    public int getGroupCount() {
        return temp.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return temp.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return temp.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return temp.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listrow_group_transaction, null);
        }
        TransactionGroup group = (TransactionGroup) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition,childPosition);
        TextView text = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listrow_details_transaction,null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.nextPage(children);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void filterData(String query){
        temp.clear();

        if(query.isEmpty()){
            temp.addAll(groups);
        }
        else{
            for(int i = 0; i < groups.size(); i++){
                TransactionGroup group = (TransactionGroup)groups.get(i);
                String name = group.string.toLowerCase();
                query = query.toLowerCase();
                if(name.contains(query)){
                   temp.add(group);
                }
            }
        }
        Log.v("Query", query);
        notifyDataSetChanged();
    }
}
