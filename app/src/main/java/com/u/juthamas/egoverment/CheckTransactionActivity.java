package com.u.juthamas.egoverment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CheckTransactionActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_transaction);
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();

        final ListView ls = (ListView) findViewById(R.id.listCheckTran);
        String[] datas = {"ทะเบียนบ้าน","สำเนาทะเบียนบ้าน","เงิน"};

        CheckTransactionAdapter adapter = new CheckTransactionAdapter(this,this.getResources().hashCode(),datas);
        ls.setAdapter(adapter);
        ls.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height*70/100));

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
