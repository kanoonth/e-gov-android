package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.activity.ConfirmationActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostQueueCodeTask extends AsyncTask<String, Void, String> {
    private ConfirmationActivity activity;
    private String status;
    private final String url;

    public PostQueueCodeTask(ConfirmationActivity ac, String qid, String qCode){
        super();
        activity = ac;
        this.url = "http://128.199.85.120/api/v1/queue/"+qid+"?queue_code="+qCode;
        Log.v("Post Queue Code url",url);
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

        return text;
    }

    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if(status.equals("OK")){
            activity.nextPage();
        }
        else{
            activity.alertInvalid();
        }
    }
}


