package com.cryfin.vukickresimir.instapop;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to parse Json data passed to it as JSONArray.
 * All data for certain image is firstly stored inside HashMap object and afterwards saved in ImageData object.
 */
public class ParseJson {

    // JSON Node names
    private static final String TAG_FULL_NAME = "full_name";
    private static final String TAG_IMAGES = "images";
    private static final String TAG_LINK_THUMB= "link";
    private static final String TAG_LINK_STD_RES= "link";
    private static final String TAG_STD_RESOLUTION= "standard_resolution";
    private static final String TAG_THUMBNAIL= "thumbnail";
    private static final String TAG_URL = "url";
    private static final String TAG_USER = "user";
    private static final String TAG_USERNAME = "username";

    private JSONArray images;
    private static ImageData imageData;

    public ParseJson ( JSONArray images ){
        this.images = images;
        GlobalData global = GlobalData.getInstance();
        imageData = global.getImageData();
    }
    //todo: implement if/else statements for checking if certain objects exist before trying to retrieve them.
    public void parse () {
        try {
            // parsing all images and populating imageData object
            for (int i = 0; i < images.length(); i++) {
                JSONObject jsonObject = images.getJSONObject(i);

                String imageUrlThumbnail = jsonObject.getJSONObject(TAG_IMAGES).getJSONObject(TAG_THUMBNAIL).getString(TAG_URL);
                if (imageData.isInCache(imageUrlThumbnail)) continue;

                String imageUrlStdRes = jsonObject.getJSONObject(TAG_IMAGES).getJSONObject(TAG_STD_RESOLUTION).getString(TAG_URL);

                JSONObject user = jsonObject.getJSONObject(TAG_USER);
                String username = user.getString(TAG_USERNAME);
                String full_name = user.getString(TAG_FULL_NAME);

                // tmp hashmap for single image
                ConcurrentHashMap<String, String> image = new ConcurrentHashMap<>();

                // adding each child node to HashMap
                image.put(TAG_USERNAME, username);
                image.put(TAG_FULL_NAME, full_name);
                image.put(TAG_LINK_THUMB, imageUrlThumbnail);
                image.put(TAG_LINK_STD_RES, imageUrlStdRes);

                imageData.addImageData(image);
            }
        }catch(JSONException e){
            Log.d("MANANA ERROR: ", "" + e);
        }
    }
}