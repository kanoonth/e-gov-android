package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.R;

public class CheckTransactionActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_transaction);

        final ListView ls = (ListView) findViewById(R.id.listCheckTran);
        String[] datas = {"ทะเบียนบ้าน","สำเนาทะเบียนบ้าน","เงิน"};

        CheckTransactionAdapter adapter = new CheckTransactionAdapter(this,this.getResources().hashCode(),datas);
        ls.setAdapter(adapter);

        final Button startBtn = (Button) findViewById(R.id.placeBtn_checkTran);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(CheckTransactionActivity.this, RouteMapActivity.class);
                startActivity(newActivity);
            }
        });

    }
}
