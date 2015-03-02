package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Action;
import com.kanoon.egov.models.Category;
import com.kanoon.egov.persistence.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ArrayList<TransactionGroup> groups = new ArrayList<TransactionGroup>();
    private SearchView search;
    private ExpandableListView lsView;
    private TransactionExpandableListAdapter adapter;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Intent intent = getIntent();
        dao = DAO.getInstance();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        //display the list
        displayList();
        //expand all Groups
//        expandAll();
    }

    //method to expand all groups
    private void expandAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++){
            lsView.expandGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {

        //display the list
        createData();

        lsView = (ExpandableListView)findViewById(R.id.lsTransaction);
        adapter = new TransactionExpandableListAdapter(groups,this);
        lsView.setAdapter(adapter);

    }

    public void createData(){

        List<Category> categories = dao.getCategories(5);
        Log.d("fffff",categories.size()+"");

        for (Category c: categories) {
            TransactionGroup group = new TransactionGroup(c.name);
            Log.d("fffff",c.name+"");
            for (Action a: c.actions) {
                Log.d("fffff",a.name+"");
                group.children.add(a.name);
            }
            groups.add(group);
        }
//
//        for (int j = 0; j < 5; j++) {
//            TransactionGroup group = new TransactionGroup("Test " + j);
//            for (int i = 0; i < 5; i++) {
//                group.children.add("Sub Item" + i);
//            }
//            groups.add(group);
//        }
    }

    @Override
    public boolean onClose() {
        adapter.filterData("");
//        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.filterData(query);
//        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filterData(query);
//        expandAll();
        return false;
    }

    public void nextPage(){
        Intent newActivity = new Intent(TransactionActivity.this, TransactionDetailActivity.class);
//        newActivity.putExtra("MyClass", (Serializable) dao);
        startActivity(newActivity);
    }
}
