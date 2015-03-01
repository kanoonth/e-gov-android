package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import com.kanoon.egov.R;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends Activity implements SearchView.OnQueryTextListener {
    private SearchView search;
    private ListView lsView;
    private com.kanoon.egov.activity.ListAdapter adapter;
    private List<String> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        places = new ArrayList<String>();
        fillData();

        Intent intent = getIntent();
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");
        search = (SearchView) findViewById(R.id.searchLocation);
        lsView = (ListView) findViewById(R.id.lsLocation);
        adapter = new com.kanoon.egov.activity.ListAdapter(this,places);
        lsView.setAdapter(adapter);
        lsView.setTextFilterEnabled(true);
        setupSearchView();

    }

    public void setupSearchView(){
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setSubmitButtonEnabled(false);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filterData(newText);
        return false;
    }

    public  void fillData() {
        for (int i = 1; i <= 4; i++) {
            places.add("places " + i);
        }
    }
}
