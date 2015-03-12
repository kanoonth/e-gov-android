package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            date = extras.getString("DateTime");
            Log.d(this.getClass().toString(), "date: "+ date);
        }

        final EditText personal_id = (EditText) findViewById(R.id.personal_id_edit);
        personal_id.setInputType(InputType.TYPE_CLASS_NUMBER);

        final EditText phone_EditText = (EditText) findViewById(R.id.telephone_edit);
        phone_EditText.setInputType(InputType.TYPE_CLASS_PHONE);

        final Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alertIdData = "เลขบัตรประจำตัวประชาชนไม่ถูกต้อง";
                String alertPhoneData = "หมายเลขโทรศัพท์ไม่ถูกต้อง";
                String alertEmptyData = "โปรดใส่ข้อมูล";
                personalID = personal_id.getText().toString();
                phone = phone_EditText.getText().toString();
                if ( MainActivity.regid == null || MainActivity.regid == "") {
                    final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                    String registrationId = prefs.getString("registration_id", "");
                    MainActivity.regid = registrationId;
                }
                Log.w("regid",MainActivity.regid);
                if ( personal_id.equals("") && phone.equals("") )
                    Toast.makeText(getApplicationContext(),alertEmptyData,Toast.LENGTH_SHORT).show();
                else if ( !validateIdentificationNumber(personalID))
                    Toast.makeText(getApplicationContext(),alertIdData,Toast.LENGTH_SHORT).show();
                else if ( !validatePhoneNumber(phone) )
                    Toast.makeText(getApplicationContext(),alertPhoneData,Toast.LENGTH_SHORT).show();
                else
                    new PostQueueTask(ConfirmationActivity.this,personalID,phone,date).execute();

            }
        });
    }

    public boolean validatePhoneNumber(String phone) {
        return isDigit(phone) && phone.trim().length() == PHONE_NO_LENGTH;
    }

    public boolean validateIdentificationNumber(String idNumber) {
        if ( !isDigit(idNumber) ) return false;
        if ( !(idNumber.trim().length() == ID_NUMBER_LENGTH ) )
            return false;
        Long id = Long.parseLong(idNumber.substring(0,12));
        Log.w("idNum",id+"");
        long base = 100000000000l;
        int basenow;
        int sum = 0;
        for ( int i = 13 ; i > 1 ; i-- ) {
            basenow = (int)Math.floor(id/base);
            id = id - basenow*base;
            sum += basenow*i;
            base = base/10;
        }
        String checkbit = ( 11 - ( sum % 11 ) ) % 10 + "";
        Log.w("lastDigit",idNumber.charAt(12)+ "");
        Log.w("checkbit",checkbit);
        return checkbit.equals(idNumber.charAt(12) + "");
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

        alert.setTitle("โปรดใส่รหัสยืนยัน");
        alert.setMessage("ท่านจะได้รับรหัสยืนยันทาง SMS");


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
            Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(ConfirmationActivity.this).toBundle());
        } else {
            Intent newActivity = new Intent(ConfirmationActivity.this, MainActivity.class);
            startActivity(newActivity);
        }

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
