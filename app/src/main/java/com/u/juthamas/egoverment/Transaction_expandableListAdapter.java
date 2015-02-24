package com.u.juthamas.egoverment;


import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class Transaction_expandableListAdapter extends BaseExpandableListAdapter{

    private SparseArray<Transaction_group> groups;
    private SparseArray<Transaction_group> temp;
    private LayoutInflater inflater;
    private Activity activity;

    public Transaction_expandableListAdapter(Activity act, SparseArray<Transaction_group> groups){
        activity = act;
        this.groups = groups;
        temp = new SparseArray<Transaction_group>();
        inflater = act.getLayoutInflater();
    }
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
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
        Transaction_group group = (Transaction_group) getGroup(groupPosition);
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
                Toast.makeText(activity,children,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void filterData(String query){
        query = query.toLowerCase();
        Log.v("Adapter",String.valueOf(temp.size()));
        temp.clear();
        Log.v("Query", query);

        if(query.isEmpty()){
            temp = groups;
        }
        else{
            int count = 0;
            for(int i = 0; i < getGroupCount(); i++){
                Transaction_group group = (Transaction_group) getGroup(i);
                String g = group.string.toLowerCase();
                if(g.toString().contains(query)){
                    temp.append(count,(Transaction_group)getGroup(i));
                    count++;
                }
            }
        }
        notifyDataSetChanged();
    }
}
