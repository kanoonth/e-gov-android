package com.kanoon.egov.persistence;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mapfap on 2/28/15.
 */
public class Copy {


    public static void exec() {
        try {
            copy(new File("/data/data/com.kanoon.egov/databases/patcher.db"), new File("/sdcard/Download/patcher.db"));
        } catch (Exception ex) {
            Log.e("Copy", ex.getMessage());
        }
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
