package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.activity.TabLayoutActivity;
import com.kanoon.egov.models.Place;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetPlaceTask extends AsyncTask<Void, Void, List<Place>> {

    private final String url;

    private TabLayoutActivity activity;

    public GetPlaceTask(TabLayoutActivity ac, long id) {
        super();
        this.activity = ac;
        this.url = "http://128.199.85.120/api/v1/actions/" + id + "/places";
    }

    @Override
    protected List<Place> doInBackground(Void... params) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.v("url",url);

            Place[] ps = rest.getForObject(url, Place[].class);
            return new ArrayList<Place>(Arrays.asList(ps));

        } catch (Exception e) {
            Log.e("GetPlaceTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Place> places) {
        super.onPostExecute(places);
        activity.fillData(places);
    }

}
