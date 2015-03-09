package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.activity.MainActivity;
import com.kanoon.egov.models.Queue;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetQueneTask extends AsyncTask<Void, Void, List<Queue>> {

    private MainActivity activity;
    private final String url;

    public GetQueneTask(MainActivity ac,String reg_id){
        super();
        activity = ac;
        this.url = "http://128.199.85.120/api/v1/queue/" + reg_id;
        Log.v("url Get Queue",url);
    }
    @Override
    protected List<Queue> doInBackground(Void... params) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Queue[] quenes = rest.getForObject(url, Queue[].class);
            return new ArrayList<Queue>(Arrays.asList(quenes));

        } catch (Exception e) {
            Log.e("GetTransactionTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Queue> queues) {
        super.onPostExecute(queues);
        if(queues != null){
            activity.fillData(queues);
        }
    }
}
