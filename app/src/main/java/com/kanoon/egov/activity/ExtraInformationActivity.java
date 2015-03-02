package com.kanoon.egov.activity;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.kanoon.egov.R;

public class ExtraInformationActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_information);

        ImageView imageView = (ImageView) findViewById(R.id.extraImage);
        imageView.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.kanoon.egov/files/1_fRxsTiexGbnT.jpg"));
    }
}
