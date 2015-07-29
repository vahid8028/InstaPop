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
 * Class for downloading bitmap image from passed URL and setting them inside the View.
 */
//Todo: application may restart on screen reorientation, http://developer.android.com/guide/topics/resources/runtime-changes.html
class URLDownloadTask extends AsyncTask<String, Void, Bitmap> {
    private static GlobalData global;
    private static ImageData imageData;
    private boolean addToCache = true;
    private Integer viewPosition;
    private InputStream urlInputStream;
    private ImageView imageView = null;
    private String urlString;

    public URLDownloadTask(ImageView imageView, Integer viewPosition, boolean addToCache){
        this.imageView = imageView;
        this.viewPosition = viewPosition;
        this.addToCache = addToCache;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }
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
            e.printStackTrace();
            return null;
        }finally {
            if (this.urlInputStream != null) {
                try {
                    this.urlInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    this.urlInputStream = null;
                }
            }
        }
    }
    //runs on the UI thread
    @Override
    protected void onPostExecute(final Bitmap bmp) {
        if (null != bmp) {
            imageView.setImageBitmap(bmp);
            if (addToCache) {
                imageData.addImageBitmapToCache(urlString, bmp);
                imageData.addImagePositionToCache(urlString, viewPosition);
            }
        }
        else
            Log.e("Error: ", "The Bitmap is NULL");
    }
}
