package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Document;
import com.kanoon.egov.persistence.DAO;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailActivity extends Activity{
    private List<String> datas;
    private String actionName;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);


        Intent intent = getIntent();
        dao = DAO.getInstance();
        Bundle extras = intent.getExtras();
        if(extras != null){
            actionName = extras.getString("submenuName");
        }

        Log.v("sub menu name",actionName);
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

    public  void fillData() {
        List<Document> listDoc = dao.getDocument(actionName);
        for (int i = 0; i <listDoc.size()  ; i++) {
            datas.add(listDoc.get(i).name);
        }
    }
}
