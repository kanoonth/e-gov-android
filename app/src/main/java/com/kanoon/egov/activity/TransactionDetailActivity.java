package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Category;
import com.kanoon.egov.models.Document;
import com.kanoon.egov.persistence.DAO;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailActivity extends Activity {
    private List<String> datas;
    private static String actionName;
    private String selectedPlace;
    private DAO dao;
    private boolean isSelectPlace;
    private static String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_transaction_detail);
        selectedPlace = "";
        isSelectPlace = false;
        Intent intent = getIntent();
        dao = DAO.getInstance();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if ( extras.containsKey("submenuName") )
                actionName = extras.getString("submenuName");
            selectedPlace = extras.getString("selectedPlace");
            if ( actionName != null) {
                Category category = dao.getCategoryByActionName(actionName);
                title = category.name;
            }

            if ( !title.equals(actionName) && actionName != null) title += actionName;
        }

        setTitle(title);

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

                String documentName = lsView.getItemAtPosition(position).toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(TransactionDetailActivity.this, DocumentActivity.class);
                    intent.putExtra("documentName", documentName);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(TransactionDetailActivity.this).toBundle());
                } else {
                    Intent newActivity = new Intent(TransactionDetailActivity.this, DocumentActivity.class);
                    newActivity.putExtra("documentName",documentName);
                    startActivity(newActivity);
                }



            }
        });

        if(selectedPlace != null){
            isSelectPlace = true;
            placeBtn.setText(selectedPlace);
        }

        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectPlace = true;
                reserveBtn.setBackgroundResource(R.drawable.button_custom);
                reserveBtn.setEnabled(true);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(TransactionDetailActivity.this, TabLayoutActivity.class);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(TransactionDetailActivity.this).toBundle());
                } else {
                    Intent newActivity = new Intent(TransactionDetailActivity.this, TabLayoutActivity.class);
                    startActivity(newActivity);
                }

            }
        });


        if(!isSelectPlace) {
            reserveBtn.setEnabled(false);
            reserveBtn.setBackgroundResource(R.drawable.button_custom_grey);
        }
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(TransactionDetailActivity.this, CalendarActivity.class);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(TransactionDetailActivity.this).toBundle());
                } else {
                    Intent newActivity = new Intent(TransactionDetailActivity.this, CalendarActivity.class);
                    startActivity(newActivity);
                }

            }
        });


    }

    public void fillData() {
        List<Document> listDoc = dao.getDocument(actionName);

        if(listDoc.size() == 0){
//            alertUpdate();
            Log.d(this.getClass().toString(), "No document.");
        }
        else {
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
