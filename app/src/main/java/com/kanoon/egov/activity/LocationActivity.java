package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.kanoon.egov.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationActivity extends Activity implements SearchView.OnQueryTextListener {
    private SearchView search;
    private ListView lsView;
    private com.kanoon.egov.activity.ListAdapter adapter;
    private List<String> namePlace;
    private List<String> idPlaces;
    public static String idPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        namePlace = new ArrayList<String>();
        idPlaces = new ArrayList<String>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            namePlace = Arrays.asList(extras.getStringArray("namePlace"));
            idPlaces = Arrays.asList(extras.getStringArray("idPlace"));
        }

        search = (SearchView) findViewById(R.id.searchLocation);
        lsView = (ListView) findViewById(R.id.lsLocation);
        adapter = new com.kanoon.egov.activity.ListAdapter(this,namePlace);
        lsView.setAdapter(adapter);
        lsView.setTextFilterEnabled(true);
        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view.findViewById(R.id.lsText)).getText().toString();
                for(int i = 0; i < namePlace.size(); i++){
                    if(item.equals(namePlace.get(i))){
                        idPlace = idPlaces.get(i);
                        break;
                    }
                }
                Intent newActivity = new Intent(LocationActivity.this, TransactionDetailActivity.class);
                newActivity.putExtra("selectedPlace",item);
                startActivity(newActivity);
            }
        });
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

}
