package com.clientWS;


import android.os.AsyncTask;
import android.util.Log;

import com.mapfap.persistence.Transaction;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestTask extends AsyncTask<Void, Void, Transaction> {
    private List<Transaction> list;
    private String url;

    public HttpRequestTask() {
        super();
        url = "http://rest-service.guides.spring.io/greeting";
        list = new ArrayList<Transaction>();
    }

    @Override
    protected Transaction doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Transaction transaction = restTemplate.getForObject(url, Transaction.class);
            return transaction;
        }catch (Exception e){
            Log.e("HttpRequestTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Transaction transaction) {
        super.onPostExecute(transaction);
        list.add(transaction);
    }


    public List<Transaction> getTransaction(int id) {
        list.clear();
        url += id;
        new HttpRequestTask().execute();
        return list;
    }
}
