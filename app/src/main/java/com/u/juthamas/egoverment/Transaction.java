package com.u.juthamas.egoverment;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.SearchView;

public class Transaction extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private SparseArray<Transaction_group> groups = new SparseArray<Transaction_group>();
    private SearchView search;
    private ExpandableListView lsView;
    private Transaction_expandableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

//        createData();
//        lsView = (ExpandableListView)findViewById(R.id.lsTransaction);
//        adapter = new Transaction_expandableListAdapter(this, groups);
//        lsView.setAdapter(adapter);

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
        adapter = new Transaction_expandableListAdapter(this, groups);
        lsView.setAdapter(adapter);

    }

    public void createData(){
        for (int j = 0; j < 5; j++) {
            Transaction_group group = new Transaction_group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
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
}
