package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Document;
import com.kanoon.egov.persistence.DAO;

import java.util.List;

public class CheckTransactionActivity extends Activity {
    private String actionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_transaction);

        actionName = getIntent().getStringExtra("actionName");
        List<Document> documents = DAO.getInstance().getDocument(actionName);
        final ListView ls = (ListView) findViewById(R.id.listCheckTran);
        String[] data = new String[documents.size()];

        for ( int i = 0 ; i < documents.size() ; i++ ) {
            data[i] = documents.get(i).name;
        }

        CheckTransactionAdapter adapter = new CheckTransactionAdapter(this,this.getResources().hashCode(),data);
        ls.setAdapter(adapter);

        final Button startBtn = (Button) findViewById(R.id.placeBtn_checkTran);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(CheckTransactionActivity.this, RouteMapActivity.class);
                newActivity.putExtra("actionName",actionName);
                startActivity(newActivity);
            }
        });

    }
}
