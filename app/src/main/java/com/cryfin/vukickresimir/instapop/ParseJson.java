package com.cryfin.vukickresimir.instapop;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by CryFin on 7/23/2015.
 *
 * This class is used to parse Json data passed to it as JSONArray.
 * All data for certain image is firstly stored inside HashMap object and afterwards saved in ImageData object.
 */
public class ParseJson {

    // JSON Node names
    private static final String TAG_CREATED = "created_time";
    private static final String TAG_FULL_NAME = "full_name";
    private static final String TAG_ID = "id";
    private static final String TAG_IMAGES = "images";
    private static final String TAG_LINK= "link";
    private static final String TAG_LOW_RESOLUTION= "low_resolution";
    private static final String TAG_PROFILE_PICTURE = "profile_picture";
    private static final String TAG_TYPE = "type";
    private static final String TAG_URL = "url";
    private static final String TAG_USER = "user";
    private static final String TAG_USERNAME = "username";

    private JSONArray images;
    private static GlobalData global;
    private static ImageData imageData;

    public ParseJson ( JSONArray images ){
        this.images = images;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }
    //todo: implement if/else statements for checking if certain objects exist before trying to retrieve them.
    public void parse () {
        try {
            // parsing all images and populating imageData object
            for (int i = 0; i < images.length(); i++) {
                JSONObject jsonObject = images.getJSONObject(i);

                String imageUrl = jsonObject.getJSONObject(TAG_IMAGES).getJSONObject(TAG_LOW_RESOLUTION).getString(TAG_URL);
                if (imageData.isInCache(imageUrl)) continue;

                String type = jsonObject.getString(TAG_TYPE);
                String created_time = jsonObject.getString(TAG_CREATED);
                String id = jsonObject.getString(TAG_ID);

                JSONObject user = jsonObject.getJSONObject(TAG_USER);
                String username = user.getString(TAG_USERNAME);
                String full_name = user.getString(TAG_FULL_NAME);
                String profile_picture = user.getString(TAG_PROFILE_PICTURE);


                // tmp hashmap for single image
                HashMap<String, String> image = new HashMap<>();

                // adding each child node to HashMap
                image.put(TAG_ID, id);
                image.put(TAG_USERNAME, username);
                image.put(TAG_CREATED, created_time);
                image.put(TAG_FULL_NAME, full_name);
                image.put(TAG_PROFILE_PICTURE, profile_picture);
                image.put(TAG_TYPE, type);
                image.put(TAG_LINK, imageUrl);

                imageData.addImageData(image);

            }
        }catch(JSONException e){
            Log.d("MANANA ERROR: ", "" + e);
        }
    }
}