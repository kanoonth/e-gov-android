package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kanoon.egov.R;

public class ConfirmationActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        final EditText personal_id = (EditText) findViewById(R.id.personal_id_edit);
        final EditText email = (EditText) findViewById(R.id.email_address);

        final Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(ConfirmationActivity.this, MainActivity.class);
                startActivity(newActivity);
            }
        });
    }
}
