package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kanoon.egov.R;
import com.kanoon.egov.http.PostQueueCodeTask;
import com.kanoon.egov.http.PostQueueTask;

public class ConfirmationActivity extends Activity{

    private String qId;
    private String personalID;
    private String phone;
    private String date;
    private static final int PHONE_NO_LENGTH = 10;
    private static final int ID_NUMBER_LENGTH = 13;
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
        personal_id.setInputType(InputType.TYPE_CLASS_NUMBER);

        final EditText phone_EditText = (EditText) findViewById(R.id.telephone_edit);
        phone_EditText.setInputType(InputType.TYPE_CLASS_PHONE);

        final Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alertData = "ข้อมูลไม่ถูกต้อง";
                personalID = personal_id.getText().toString();
                phone = phone_EditText.getText().toString();
                if ( MainActivity.regid == null || MainActivity.regid == "") {
                    final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                    String registrationId = prefs.getString("registration_id", "");
                    MainActivity.regid = registrationId;
                }
                Log.w("regid",MainActivity.regid);
                if ( validateIdentificationNumber(personalID) && validatePhoneNumber(phone) )
                    new PostQueueTask(ConfirmationActivity.this,personalID,phone,date).execute();
                else
                    Toast.makeText(getApplicationContext(),alertData,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validatePhoneNumber(String phone) {
        return isDigit(phone) && phone.trim().length() == PHONE_NO_LENGTH;
    }

    public boolean validateIdentificationNumber(String idNumber) {
        return isDigit(idNumber) && idNumber.trim().length() == ID_NUMBER_LENGTH;
    }

    public static boolean isDigit(String str) {
        if (str != null) { // <--- Add This
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i)))
                    return false;
            }
            return true; // we only got here if there were characters, and they're all digit(s).
        }
        return false;
    }

    public void receiveQueueCode() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Queue Code");
        alert.setMessage("Please fill queue code");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
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
