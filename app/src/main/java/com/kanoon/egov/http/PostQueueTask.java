package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.activity.ConfirmationActivity;
import com.kanoon.egov.activity.LocationActivity;
import com.kanoon.egov.activity.MainActivity;
import com.kanoon.egov.activity.TransactionActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostQueueTask extends AsyncTask<String, Void, String> {
    private final String url;
    private ConfirmationActivity activity;

    public PostQueueTask(ConfirmationActivity ac,String id_no, String phone, String time){
        activity = ac;
        this.url = "http://128.199.85.120/api/v1/queue?action_id="+ TransactionActivity.idMenu
                +"&place_id="+ LocationActivity.idPlace
                +"&reg_id="+ MainActivity.regid
                +"&phone_no="+ phone
                +"&id_no="+id_no
                +"&time="+time;

        Log.v("url Post Queue",url);
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
    protected void onPostExecute(String id) {
        Log.d("id",id);
        super.onPostExecute(id);
        if(id != null){
            activity.setqId(id);
            activity.receiveQueueCode();
        }
    }
}
