package com.kanoon.egov.http;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by suwijakchaipipat on 3/9/2015 AD.
 */
public class PostQueueRateTask extends AsyncTask<String, Void, String> {

    private final String url;

    public PostQueueRateTask(String id, String rate) {
        this.url = "http://128.199.85.120/api/v1/queue/" + id + "/rate?=" + rate;
    }

    @Override
    protected String doInBackground(String... params) {
        String text = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        HttpResponse resp = null;
        try {
            resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();
            text = EntityUtils.toString(ent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
