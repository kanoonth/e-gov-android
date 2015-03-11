package com.kanoon.egov.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.kanoon.egov.R;
import com.kanoon.egov.models.Document;
import com.kanoon.egov.persistence.DAO;

public class DocumentActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_document);
        Intent intent = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.docImage);
        DAO dao = DAO.getInstance();
        String documentName = getIntent().getStringExtra("documentName");
        Document document = dao.getDocumentByName(documentName);
        setTitle(document.name);

        imageView.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.kanoon.egov/files/" + document.getPhotoFileName()));

        Button btn = (Button) findViewById(R.id.Docbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(DocumentActivity.this, ExtraInformationActivity.class);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(DocumentActivity.this).toBundle());
                } else {
                    Intent newActivity = new Intent(DocumentActivity.this, ExtraInformationActivity.class);
                    startActivity(newActivity);
                }



            }
        });
    }
}
