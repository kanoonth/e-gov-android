package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kanoon.egov.R;
import com.kanoon.egov.http.PostQueueCodeTask;
import com.kanoon.egov.http.PostQueueTask;

public class ConfirmationActivity extends Activity{

    private String qId;
    private String personalID;
    private String phone;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            date = extras.getString("DateTime");
        }

        final EditText personal_id = (EditText) findViewById(R.id.personal_id_edit);
        final EditText phone_EditText = (EditText) findViewById(R.id.telephone_edit);

        final Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalID = personal_id.getText().toString();
                phone = phone_EditText.getText().toString();
                new PostQueueTask(ConfirmationActivity.this,personalID,phone,date).execute();
            }
        });
    }

    public void receiveQueueCode() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Queue Code");
        alert.setMessage("Please fill queue code");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                new PostQueueCodeTask(ConfirmationActivity.this,qId,value).execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void nextPage(){
        Intent newActivity = new Intent(ConfirmationActivity.this, MainActivity.class);
        startActivity(newActivity);
    }

    public void alertInvalid() {

        new AlertDialog.Builder(ConfirmationActivity.this)
                .setTitle("Invalid")
                .setMessage("Invalid Queue Code")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        receiveQueueCode();
                    }
                }).show();

    }

    public void setqId(String q) {
        this.qId = q;
    }
}
