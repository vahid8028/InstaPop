package com.cryfin.vukickresimir.instapop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by CryFin on 7/22/2015.
 */
//Todo: application may restart on screen reorientation, http://developer.android.com/guide/topics/resources/runtime-changes.html
class URLDownloadTask extends AsyncTask<URL, Void, Bitmap> {
    private ImageView updateView = null;
    private boolean isCancelled = false;
    private InputStream urlInputStream;
    private ImageData imageData;

    URLDownloadTask(ImageView updateView, ImageData imageData){
        this.updateView = updateView;
        this.imageData = imageData;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bmp;
        try {
            HttpURLConnection con = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = con.getInputStream();
            bmp = BitmapFactory.decodeStream(inputStream);
            return bmp;
        } catch (Exception e) {
            Log.d("MANANA", "URLDT Exception: " + e);
            return null;
        }finally {
            if (this.urlInputStream != null) {
                try {
                    this.urlInputStream.close();
                } catch (IOException e) {
                    Log.d("MANANA", "URLDT Exception: " + e);
                } finally {
                    this.urlInputStream = null;
                }
            }
        }
    }
    //runs in UI thread
    //todo: rename updateView
    @Override
    protected void onPostExecute(final Bitmap bmp) {
        if (!this.isCancelled) {
            if (null != bmp) {
                updateView.setImageBitmap(bmp);
                imageData.addImageBitmap(bmp);
            }
            else
                System.out.println("The Bitmap is NULL");
        }
    }

    //when cancelled, no synchronization is necessary
    //todo: inspect code
    @Override
    protected void onCancelled() {
        this.isCancelled = true;
        try {
            if (this.urlInputStream != null) {
                try {
                    this.urlInputStream.close();
                } catch (IOException e) {
                    Log.d("MANANA", "URLDT Exception: " + e);
                } finally {
                    this.urlInputStream = null;
                }
            }
        } finally {
            super.onCancelled();
        }
    }
}
