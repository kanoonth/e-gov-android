package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailActivity extends Activity{
    private List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        Intent intent = getIntent();
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");
        datas = new ArrayList<String>();
        fillData();

        final ListView lsView = (ListView)findViewById(R.id.lsDetail);
        ListAdapter adapter = new ListAdapter(this, datas);
        lsView.setAdapter(adapter);
        lsView.setTextFilterEnabled(true);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, DocumentActivity.class);
//                newActivity.putExtra("MyClass", (Serializable) dao);
                startActivity(newActivity);
            }
        });

        final Button placeBtn = (Button)findViewById(R.id.cPlaceBtn);
        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, TabLayoutActivity.class);
//                newActivity.putExtra("MyClass", (Serializable) dao);
                startActivity(newActivity);
            }
        });

        final Button reserveBtn = (Button)findViewById(R.id.reserveBtn);
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, CalendarActivity.class);
//                newActivity.putExtra("MyClass", (Serializable) dao);
                startActivity(newActivity);
            }
        });
    }

    public  void fillData() {
        for (int i = 1; i <= 7; i++) {
            datas.add("Activity " + i);
        }
    }
}
