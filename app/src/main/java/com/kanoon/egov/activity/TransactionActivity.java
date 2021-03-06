package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Action;
import com.kanoon.egov.models.Category;
import com.kanoon.egov.persistence.DAO;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ArrayList<TransactionGroup> groups;
    private List<List<Action>> actions;
    private SearchView search;
    private ExpandableListView lsView;
    private TransactionExpandableListAdapter adapter;
    private DAO dao;

    public static long idMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_transaction);

        dao = DAO.getInstance();
        actions = new ArrayList<List<Action>>();
        groups = new ArrayList<TransactionGroup>();
        createData();

        setTitle("เลือกบริการ");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        search.setFocusable(false);

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
        lsView = (ExpandableListView)findViewById(R.id.lsTransaction);
        adapter = new TransactionExpandableListAdapter(groups,this);
        lsView.setAdapter(adapter);

    }

    public void createData(){
        List<Category> list = dao.getCategories(5);
        for (int i = 0; i < list.size(); i++) {
            TransactionGroup group = new TransactionGroup(list.get(i).name);
            for (int j = 0; j < list.get(i).actions.size(); j++) {
                actions.add(list.get(i).actions);
                group.children.add(list.get(i).actions.get(j).name);
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

    public void nextPage(String t){
        String txt = t;

        for(List<Action> a : actions){
            for(int i = 0; i < a.size(); i++){
                if(a.get(i).name == t){
                    idMenu = a.get(i).id;
                    break;
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
            Intent intent = new Intent(TransactionActivity.this, TransactionDetailActivity.class);
            intent.putExtra("submenuName",txt);
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(TransactionActivity.this).toBundle());
        } else {
            Intent newActivity = new Intent(TransactionActivity.this, TransactionDetailActivity.class);
            newActivity.putExtra("submenuName",txt);
            startActivity(newActivity);
        }


    }
}
