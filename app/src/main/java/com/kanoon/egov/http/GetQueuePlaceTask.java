package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.kanoon.egov.activity.ConfirmationActivity;
import com.kanoon.egov.activity.LocationActivity;
import com.kanoon.egov.activity.MainActivity;
import com.kanoon.egov.activity.RouteMapActivity;
import com.kanoon.egov.activity.TransactionActivity;
import com.kanoon.egov.models.Place;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by suwijakchaipipat on 3/10/2015 AD.
 */
public class GetQueuePlaceTask extends AsyncTask<String, Void, String> {

    private String url;
    private RouteMapActivity activity;
    private Place place;

    public GetQueuePlaceTask(RouteMapActivity activity, String queueId){
        this.url = "http://128.199.85.120/api/v1/queue/" + queueId + "/place";
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.v("url",url);

            Place[] places = rest.getForObject(url, Place[].class);

            place = places[0];

            } catch (Exception e) {
            Log.e("GetPlaceTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String id) {
        this.activity.addMarker(new LatLng(place.latitude,place.longitude),place.name,"");
        super.onPostExecute(id);
    }
}
