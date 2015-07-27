package com.cryfin.vukickresimir.instapop;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Async class for loading initial set of images.
 */
public class LoadImageData extends AsyncTask<Integer, Void, Void> {

    //private final WeakReference<ImageAdapter> imageAdapterWeakReference;
    private static GetJson getJson;
    private static GlobalData global = GlobalData.getInstance();
    private static ImageData imageData;
    private static ImageAdapter imageAdapter;
    private static GridView gridView;
    private static String downloadUrl;
    private static ProgressBar progressBar;

    public LoadImageData() {
        this.getJson = new GetJson();
        global = GlobalData.getInstance();
        imageData = global.getImageData();
        imageAdapter = global.getImageAdapter();
        gridView = global.getGridView();
        downloadUrl = global.getDownloadUrl();
        progressBar = global.getProgressBar();
        //imageAdapterWeakReference = new WeakReference<>(imageAdapter);
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        imageData.setLoadingImages(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Integer... currentPage) {
        String jsonStr = getJson.get(downloadUrl);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray images = jsonObj.getJSONArray("data");

                ParseJson parseJson = new ParseJson(images);
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
        progressBar.setVisibility(View.GONE);
        // get listview current position - used to maintain scroll position
        int currentPosition = gridView.getFirstVisiblePosition();

        //Populate GridView with images
        gridView.setAdapter(imageAdapter);

        //todo: is this needed?
        // Setting new scroll position
        gridView.setSelection(currentPosition);
        imageData.setLoadingImages(false);
    }
}
