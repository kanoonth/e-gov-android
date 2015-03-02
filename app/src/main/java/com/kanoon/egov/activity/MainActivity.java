package com.kanoon.egov.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.persistence.Copy;
import com.kanoon.egov.persistence.DAO;
import com.kanoon.egov.persistence.Patcher;
import com.kanoon.egov.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity {
    private List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Patcher patcher = new Patcher(getBaseContext());
        patcher.patch();
        Copy.exec();

        data = new ArrayList<String>();
        fillData();
        final DAO dao = DAO.getInstance();
        dao.setContent(getBaseContext());
        final ListView lsHis = (ListView)findViewById(R.id.listHistory);
        ListAdapter adapter = new ListAdapter(this, data);
        lsHis.setAdapter(adapter);
        lsHis.setTextFilterEnabled(true);

        lsHis.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, CheckTransactionActivity.class);
                newActivity.putExtra("MyClass", (Serializable) dao);
//                newActivity.putExtra("MyClass", (Serializable) parse);
                startActivity(newActivity);
            }
        });

        final Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(MainActivity.this, TransactionActivity.class);
//                newActivity.putExtra("MyClass", (Serializable) parse);
                startActivity(newActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void fillData() {
        data.add("ทำบัตรประชาชน");
        data.add("แจ้งเกิด");
        for (int i = 1; i <= 4; i++) {
            data.add("history " + i);
        }
    }
}
