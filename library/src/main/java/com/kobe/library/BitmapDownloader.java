package com.kobe.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kobe-mac on 15/5/20.
 */
public class BitmapDownloader {

    public static Bitmap loadBitmap(String url) {


        try {
            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            InputStream in = conn.getInputStream();
            byte[] buff = new byte[1024];
            int length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while((length = in.read(buff))!=-1){
                out.write(buff, 0, length);
            }
            byte[] data = out.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
