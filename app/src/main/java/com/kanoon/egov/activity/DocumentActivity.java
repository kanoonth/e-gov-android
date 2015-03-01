package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kanoon.egov.R;

public class DocumentActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        Intent intent = getIntent();
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");
        Button btn = (Button) findViewById(R.id.Docbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(DocumentActivity.this, ExtraInformationActivity.class);
//                newActivity.putExtra("MyClass", (Serializable) dao);
                startActivity(newActivity);
            }
        });
    }
}
