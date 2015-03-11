package com.kanoon.egov.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
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

    private TabHost tabhost;
    private GetPlaceTask getPlaceTask;
    private List<Place> places;
    private List<String> namePlace;
    private List<String> lat;
    private List<String> log;
    private List<String> idPlace;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_tab_controll);

        places = new ArrayList<Place>();
        namePlace = new ArrayList<String>();
        lat = new ArrayList<String>();
        log = new ArrayList<String>();
        idPlace = new ArrayList<String>();

        getPlaceTask = new GetPlaceTask(this, TransactionActivity.idMenu);
        getPlaceTask.execute();

//        DAO dao = (DAO) intentTemp.getSerializableExtra("MyClass");
    }

    public void fillData(List<Place> places) {
        this.places.addAll(places);
        for(Place p : places){
            namePlace.add(p.name);
            idPlace.add(p.id +"");
            lat.add(p.latitude +"");
            log.add(p.longitude+"");
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
        intent.putExtra("namePlace", namePlace.toArray(new String[0]));
        intent.putExtra("idPlace", idPlace.toArray(new String[0]));
        spec = tabhost.newTabSpec("location").setIndicator("สถานที่").setContent(intent);
        tab1.setContent(new Intent(this,LocationActivity.class));

        tabhost.addTab(spec);

        intent = new Intent(this, NearMapActivity.class);
        intent.putExtra("namePlace", namePlace.toArray(new String[0]));
        intent.putExtra("idPlace", idPlace.toArray(new String[0]));
        intent.putExtra("latitude", lat.toArray(new String[0]));
        intent.putExtra("longitude", log.toArray(new String[0]));
        spec = tabhost.newTabSpec("nearMap").setIndicator("Google Map").setContent(intent);
//        newActivity.putExtra("MyClass", (Serializable) dao);
        tab2.setContent(new Intent(this,NearMapActivity.class));
        tabhost.addTab(spec);
    }
}
