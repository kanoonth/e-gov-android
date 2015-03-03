package com.kanoon.egov.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.kanoon.egov.R;
import com.kanoon.egov.http.GetPlaceTask;
import com.kanoon.egov.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aof on 2/24/2015 AD.
 */
public class TabLayoutActivity extends TabActivity {
    private long id;
    private TabHost tabhost;
    private GetPlaceTask getPlaceTask;
    private List<Place> places;
    private List<String> namePlace;
    private List<Float> lat;
    private List<Float> log;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tab_controll);

        id = 0;
        Intent intentTemp = getIntent();
        Bundle extras = intentTemp.getExtras();
        if (extras != null) {
            id = extras.getLong("id");
        }

        places = new ArrayList<Place>();
        namePlace = new ArrayList<String>();
        lat = new ArrayList<Float>();
        log = new ArrayList<Float>();

        getPlaceTask = new GetPlaceTask(this, id);
        getPlaceTask.execute();

//        DAO dao = (DAO) intentTemp.getSerializableExtra("MyClass");
    }

    public void fillData(List<Place> places) {
        this.places.addAll(places);
        for(Place p : places){
            namePlace.add(p.name);
            lat.add(p.latitude);
            log.add(p.longitude);
        }

        doTabhost();
    }

    public void doTabhost(){
        tabhost = getTabHost();
        TabHost.TabSpec tab1 = tabhost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabhost.newTabSpec("Sec Tab");
        TabHost.TabSpec spec;

        Intent intent;
        intent = new Intent(this, LocationActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("namePlace", namePlace.toArray(new String[0]));
        spec = tabhost.newTabSpec("location").setIndicator("สถานที่").setContent(intent);
        tab1.setContent(new Intent(this,LocationActivity.class));

        tabhost.addTab(spec);

        intent = new Intent(this, NearMapActivity.class);
        spec = tabhost.newTabSpec("nearMap").setIndicator("Google Map").setContent(intent);
//        newActivity.putExtra("MyClass", (Serializable) dao);
        tab2.setContent(new Intent(this,NearMapActivity.class));
        tabhost.addTab(spec);
    }
}
