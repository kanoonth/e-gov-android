package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class TransactionDetailActivity extends Activity {
    private List<String> datas;
    private String actionName;
    private long id;
    private DAO dao;
    private boolean isSelectPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        isSelectPlace = false;
        Intent intent = getIntent();
        dao = DAO.getInstance();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            actionName = extras.getString("submenuName");
            id = extras.getLong("id");
        }

        Log.v("sub menu name", actionName);
        datas = new ArrayList<String>();
        fillData();

        final ListView lsView = (ListView) findViewById(R.id.lsDetail);
        final Button placeBtn = (Button) findViewById(R.id.cPlaceBtn);
        final Button reserveBtn = (Button) findViewById(R.id.reserveBtn);
        ListAdapter adapter = new ListAdapter(this, datas);
        lsView.setAdapter(adapter);
        lsView.setTextFilterEnabled(true);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, DocumentActivity.class);
                startActivity(newActivity);
            }
        });

        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectPlace = true;
                reserveBtn.setBackgroundResource(R.drawable.button_custom);
                Intent newActivity = new Intent(TransactionDetailActivity.this, TabLayoutActivity.class);
                newActivity.putExtra("id",id);
                startActivity(newActivity);
            }
        });


        if(!isSelectPlace) {
            reserveBtn.setEnabled(false);
            reserveBtn.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B6B6B4")));
        }
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(TransactionDetailActivity.this, CalendarActivity.class);
                startActivity(newActivity);
            }
        });
    }

    public void fillData() {
        List<Document> listDoc = dao.getDocument(actionName);
        if(listDoc.size() == 0){
            alertUpdate();
        }
        else{
            for (int i = 0; i < listDoc.size(); i++) {
                datas.add(listDoc.get(i).name);
            }
        }
    }

    /*
     * AlertDialog for udpating patch
     */
    public void alertUpdate() {

        new AlertDialog.Builder(TransactionDetailActivity.this)
                .setTitle("UPDATE")
                .setMessage("Update patcher")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();

    }
}
