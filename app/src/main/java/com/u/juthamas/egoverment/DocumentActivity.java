package com.u.juthamas.egoverment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DocumentActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        Button btn = (Button) findViewById(R.id.Docbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(DocumentActivity.this, ExtraInformationActivity.class);
                startActivity(newActivity);
            }
        });
    }
}
