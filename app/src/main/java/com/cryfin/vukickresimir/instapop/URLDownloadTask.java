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
 * Class for downloading bitmap image from passed URL and putting it inside the View.
 */
//Todo: application may restart on screen reorientation, http://developer.android.com/guide/topics/resources/runtime-changes.html
class URLDownloadTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView = null;
    private InputStream urlInputStream;
    private static GlobalData global;
    private static ImageData imageData;
    private String urlString;
    private Integer viewPosition;

    public URLDownloadTask(ImageView imageView, Integer viewPosition){
        this.imageView = imageView;
        this.viewPosition = viewPosition;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    @Override
    protected Bitmap doInBackground(String... urlStrings) {
        Bitmap bmp;
        try {
            this.urlString = urlStrings[0];
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            urlInputStream = con.getInputStream();
            bmp = BitmapFactory.decodeStream(urlInputStream);
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
    @Override
    protected void onPostExecute(final Bitmap bmp) {
        if (null != bmp) {
            imageView.setImageBitmap(bmp);
            imageData.addImageBitmapToCache(urlString, bmp);
            imageData.addImagePositionToCache(urlString, viewPosition);
        }
        else
            Log.d("MANANA Error:","The Bitmap is NULL");
    }

    //when cancelled, no synchronization is necessary
    /*@Override
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
    }*/
}
