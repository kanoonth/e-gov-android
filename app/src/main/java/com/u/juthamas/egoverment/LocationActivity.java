package com.u.juthamas.egoverment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class LocationActivity extends Activity implements SearchView.OnQueryTextListener {
    private SearchView search;
    private ListView lsView;
    private ArrayAdapter<String> adapter;
    private String[] placeList = {"place1","place2","place3","flace4","place5","place6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Intent intent = getIntent();
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");
        search = (SearchView) findViewById(R.id.searchLocation);
        lsView = (ListView) findViewById(R.id.lsLocation);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, placeList);
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
        adapter.getFilter().filter(newText);
        return true;
    }
}
