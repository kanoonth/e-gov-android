package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.kanoon.egov.R;

import java.util.ArrayList;

public class TransactionActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ArrayList<TransactionGroup> groups = new ArrayList<TransactionGroup>();
    private SearchView search;
    private ExpandableListView lsView;
    private TransactionExpandableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Intent intent = getIntent();
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");

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
//
//        List<Category> categories = new ArrayList<Category>();
//        Category c1 = new Category();
//        Category c2 = new Category();
//        categories.add(c1);
//        categories.add(c2);
//        DAO dao = new DAO();
//        List<Category> categories = dao.getCategories(5);

//        for (Category c: categories) {
//            TransactionGroup group = new TransactionGroup(c.name);
//            for (Action a: c.actions) {
//                group.children.add(a.name);
//
//            }
//        }

        for (int j = 0; j < 5; j++) {
            TransactionGroup group = new TransactionGroup("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.add(group);
        }
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
