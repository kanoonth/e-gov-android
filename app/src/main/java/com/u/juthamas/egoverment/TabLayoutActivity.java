package com.u.juthamas.egoverment;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Aof on 2/24/2015 AD.
 */
public class TabLayoutActivity extends TabActivity {
    private TabHost tabhost;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tab_controll);

        tabhost = getTabHost();
        TabHost.TabSpec tab1 = tabhost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabhost.newTabSpec("Sec Tab");
        TabHost.TabSpec spec;

        Intent intent;
        intent = new Intent(this, LocationActivity.class);
        spec = tabhost.newTabSpec("location").setIndicator("สถานที่").setContent(intent);
        tab1.setContent(new Intent(this,LocationActivity.class));

        tabhost.addTab(spec);

        intent = new Intent(this, NearMapActivity.class);
        spec = tabhost.newTabSpec("nearMap").setIndicator("Google Map").setContent(intent);
        tab2.setContent(new Intent(this,NearMapActivity.class));
        tabhost.addTab(spec);
    }
}
