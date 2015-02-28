package com.u.juthamas.egoverment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TransactionDetailActivity extends Activity{
    private String[] dataList = {"data1","data2","data3","data4","data5","data6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        final ListView lsView = (ListView)findViewById(R.id.lsDetail);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dataList);
        lsView.setAdapter(adapter);
        lsView.setTextFilterEnabled(true);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, DocumentActivity.class);
                startActivity(newActivity);
            }
        });

        final Button placeBtn = (Button)findViewById(R.id.cPlaceBtn);
        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, TabLayoutActivity.class);
                startActivity(newActivity);
            }
        });

        final Button reserveBtn = (Button)findViewById(R.id.reserveBtn);
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, CalendarActivity.class);
                startActivity(newActivity);
            }
        });
    }
}
