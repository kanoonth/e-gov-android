package com.kanoon.egov.http;

import android.os.AsyncTask;
import android.util.Log;

import com.kanoon.egov.persistence.Patcher;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mapfap on 3/1/15.
 */
public class DownloadFileTask extends AsyncTask<Void, Void, Void> {

    private final Patcher patcher;
    private final String url;
    private final String filename;

    public DownloadFileTask(Patcher patcher, String url, String filename) {
        super();
        this.patcher = patcher;
        this.url = url;
        this.filename = filename;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            download();
        } catch (Exception e) {
            Log.e("DownloadFileTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        patcher.onReceiveFile(true);
    }

    private void download() throws Exception {
        String aUrl = url;
        URL url = new URL(aUrl);
        File file = new File("/data/data/com.kanoon.egov/files/" + filename);
        long startTime = System.currentTimeMillis();

        URLConnection ucon = url.openConnection();
        InputStream is = ucon.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(4096);
        int current = 0;
        while ((current = bis.read()) != -1) {
            baf.append((byte) current);
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baf.toByteArray());
        fos.flush();
        fos.close();
    }
}
