package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.activity.ConfirmationActivity;
import com.kanoon.egov.activity.LocationActivity;
import com.kanoon.egov.activity.MainActivity;
import com.kanoon.egov.activity.RouteMapActivity;
import com.kanoon.egov.activity.TransactionActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by suwijakchaipipat on 3/10/2015 AD.
 */
public class GetQueuePlaceTask extends AsyncTask<String, Void, String> {

    private String url;
    private RouteMapActivity activity;

    public GetQueuePlaceTask(RouteMapActivity activity, String actionName, String reg_id){
        this.url = "http://128.199.85.120/api/v1/queue/action?name=" + actionName + "&reg_id=" + reg_id;
        this.activity = activity;
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

        //TODO update RouteMapActivity to addMarker on place

        return text;
    }

    @Override
    protected void onPostExecute(String id) {
        super.onPostExecute(id);
    }
}
