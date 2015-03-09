package com.kanoon.egov.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.kanoon.egov.R;
import com.kanoon.egov.http.PostQueueRateTask;

public class ReviewPageActivity extends Activity {
    private RatingBar ratingBar;
    private Button button;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        id = getIntent().getStringExtra("id");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(3.0f);

        button = (Button) findViewById(R.id.btnSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = Math.round(ratingBar.getRating()) + "";

                new PostQueueRateTask(id,rating);

                finish();

                launchHomeScreen();
            }
        });
    }

    public void launchHomeScreen() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
