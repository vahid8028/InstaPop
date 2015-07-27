package com.cryfin.vukickresimir.instapop;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Created by CryFin on 7/21/2015.
 *
 *  Async class for loading initial set of images.
 * */
public class LoadImageData extends AsyncTask<Integer, Void, Void> {

    //private final WeakReference<ImageAdapter> imageAdapterWeakReference;
    private static GetJson getJson = new GetJson();
    private ImageData imageData;
    private ImageAdapter imageAdapter;
    private GridView gridView;
    private String downloadUrl;

    public LoadImageData( ImageData imageData, ImageAdapter imageAdapter,  GridView gridView, String downloadUrl ) {
        this.imageData = imageData;
        this.imageAdapter = imageAdapter;
        this.gridView = gridView;
        this.downloadUrl = downloadUrl;
        //imageAdapterWeakReference = new WeakReference<>(imageAdapter);
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Integer... currentPage) {
        //TODO: rename method
        String jsonStr = getJson.get(downloadUrl);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray images = jsonObj.getJSONArray("data");

                ParseJson parseJson = new ParseJson(images, imageData);
                parseJson.parse();

            } catch (JSONException e) {
                Log.d("MANANA ERROR: ", "" + e);
            }
        } else {
            Log.d("MANANA", "Couldn't get any data from the url");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //super.onPostExecute(result);
        //progressBar.setVisibility(View.GONE);
        //Populate GridView with images
        gridView.setAdapter(imageAdapter);

        //todo: is this needed?
        // get listview current position - used to maintain scroll position
        int currentPosition = gridView.getFirstVisiblePosition();
        // Setting new scroll position
        gridView.setSelection(currentPosition + 1);
    }
}
