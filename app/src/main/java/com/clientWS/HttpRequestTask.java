package com.clientWS;


import android.os.AsyncTask;
import android.util.Log;

import com.mapfap.persistence.Patcher;
import com.mapfap.persistence.Transaction;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequestTask extends AsyncTask<Void, Void, List<Transaction>> {

    private final Patcher patcher;
    private final String url;

    public HttpRequestTask(Patcher patcher, long code) {
        super();
        this.patcher = patcher;
        this.url = "http://128.199.85.120/api/v1/transaction/" + code;
    }

    @Override
    protected List<Transaction> doInBackground(Void... params) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Transaction[] transactions = rest.getForObject(url, Transaction[].class);
            return new ArrayList<Transaction>(Arrays.asList(transactions));

        } catch (Exception e) {
            Log.e("HttpRequestTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Transaction> transactions) {
        super.onPostExecute(transactions);
        patcher.onRecieveData(transactions);
    }

}
