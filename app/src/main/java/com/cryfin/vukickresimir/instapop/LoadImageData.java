package com.cryfin.vukickresimir.instapop;

/**
 * Created by CryFin on 7/26/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Async task class for fetching and parsing JSON
 * */
public class LoadImageData extends AsyncTask<Integer, Void, Void> {

    // JSON Node names
    private static final String TAG_CREATED = "created_time";
    private static final String TAG_DATA = "data";
    private static final String TAG_FULL_NAME = "full_name";
    private static final String TAG_NEXT_URL = "next_url";
    private static final String TAG_ID = "id";
    private static final String TAG_IMAGES = "images";
    private static final String TAG_LINK= "link";
    private static final String TAG_LOW_RESOLUTION= "low_resolution";
    private static final String TAG_PAGINATION = "pagination";
    private static final String TAG_PROFILE_PICTURE = "profile_picture";
    private static final String TAG_TYPE = "type";
    private static final String TAG_URL = "url";
    private static final String TAG_USER = "user";
    private static final String TAG_USERNAME = "username";

    //private final WeakReference<ImageAdapter> imageAdapterWeakReference;
    private GetJsonTask getJsonTask;
    private String downloadUrl;
    private ImageData imageData;
    private Context mContext;
    private GridView gridView;

    public LoadImageData( Context mContext, String downloadUrl, ImageData imageData, GridView gridView ) {
        this.getJsonTask= new GetJsonTask();

        this.mContext = mContext;
        this.downloadUrl = downloadUrl;
        this.imageData = imageData;
        this.gridView = gridView;
        //imageAdapterWeakReference = new WeakReference<>(imageAdapter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Integer... currentPage) {
        //TODO: rename method
        String jsonStr = getJsonTask.makeServiceCall(downloadUrl);

        //Log.d("MANANA Response: ", jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray images = jsonObj.getJSONArray(TAG_DATA);

                // looping through All Contacts
                for (int i = 0; i < images.length(); i++) {
                    JSONObject c = images.getJSONObject(i);

                    String type = c.getString(TAG_TYPE);
                    String created_time = c.getString(TAG_CREATED);
                    String id = c.getString(TAG_ID);

                    // Phone node is JSON Object
                    JSONObject user = c.getJSONObject(TAG_USER);
                    String username = user.getString(TAG_USERNAME);
                    String full_name = user.getString(TAG_FULL_NAME);
                    String profile_picture = user.getString(TAG_PROFILE_PICTURE);

                    String imageUrl = c.getJSONObject(TAG_IMAGES).getJSONObject(TAG_LOW_RESOLUTION).getString(TAG_URL);

                    // tmp hashmap for single image
                    HashMap<String, String> image = new HashMap<>();

                    // adding each child node to HashMap key => value
                    image.put(TAG_ID, id);
                    image.put(TAG_USERNAME, username);
                    image.put(TAG_CREATED, created_time);
                    image.put(TAG_FULL_NAME, full_name);
                    image.put(TAG_PROFILE_PICTURE, profile_picture);
                    image.put(TAG_TYPE, type);
                    image.put(TAG_LINK, imageUrl);

                    imageData.addImageData(image);

                   /* try {
                        pagingUrl = jsonObj.getJSONObject(TAG_PAGINATION).getString(TAG_NEXT_URL);
                    }catch (JSONException e) {
                        Log.d("MANANA pagination: ", "" + e);
                    }*/

                }
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
        super.onPostExecute(result);
        //progressBar.setVisibility(View.GONE);
        //Populate GridView with images
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mainView = mInflater.inflate(R.layout.activity_main, null);
        ImageAdapter imageAdapter = new ImageAdapter(mContext, imageData);
        gridView.setAdapter(imageAdapter);


        // get listview current position - used to maintain scroll position
        int currentPosition = gridView.getFirstVisiblePosition();
        // Setting new scroll position
        gridView.setSelection(currentPosition + 1);
    }
}
