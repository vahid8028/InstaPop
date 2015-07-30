package com.cryfin.vukickresimir.instapop;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Async class for loading initial set of images.
 */
public class LoadImageData extends AsyncTask<Void, Void, Void> {

    private static GetJson getJson;
    private static GlobalData global;
    private static ImageData imageData;
    private static ImageAdapter imageAdapter;
    private static ListView listView;
    private static String downloadUrl;
    private static ProgressBar progressBar;

    public LoadImageData() {
        getJson = new GetJson();
        global = GlobalData.getInstance();
        imageData = global.getImageData();
        imageAdapter = global.getImageAdapter();
        listView = global.getListView();
        downloadUrl = global.getDownloadUrl();
        progressBar = global.getProgressBar();
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        imageData.setLoadingImages(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... vars) {
        String jsonStr = getJson.get(downloadUrl);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray images = jsonObj.getJSONArray("data");

                ParseJson parseJson = new ParseJson(images);
                parseJson.parse();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error: ", "Couldn't get any data from the url");
        }
        return null;
    }

    @Override
    protected synchronized void onPostExecute(Void result) {
        //super.onPostExecute(result);
        progressBar.setVisibility(View.GONE);

        // Get current scroll position
        int currentPosition = listView.getFirstVisiblePosition();

        //Populate ListView with images
        listView.setAdapter(imageAdapter);

        // Set old scroll position - othervise you'd be thrown to the beginning
        listView.setSelection(currentPosition);

        imageData.setLoadingImages(false);
    }
}
