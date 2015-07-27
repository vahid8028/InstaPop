package com.cryfin.vukickresimir.instapop;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Created by CryFin on 7/22/2015.
 *
 *  This class is used to store and manage all images used inside the app.
 *
 */
public class ImageData {

    private ArrayList<HashMap<String, String>> imageList;
    private HashMap<String, Integer> imageCache;
    private HashMap<Integer, Bitmap> imageBitmaps;

    public ImageData( ArrayList<HashMap<String, String>> imageList, HashMap<String, Integer> imageCache){
        this.imageList = imageList;
        this.imageCache = imageCache;
        this.imageBitmaps = new HashMap<>();
    }

    //todo: check if addImageBitmap position counting works with this method
    public boolean addImageBitmap ( Bitmap bmp){
        int position = imageBitmaps.size();
        imageBitmaps.put(position, bmp);
        return imageBitmaps.get(position) == bmp;
    }
    public boolean addImageBitmap ( Integer position, Bitmap bmp){
        imageBitmaps.put(position-1, bmp);
        return imageBitmaps.get(position - 1) == bmp;
    }
    public boolean addImageData (HashMap<String, String> image){
        return imageList.add(image);
    }
    public boolean addImageDataToCache (String url, Integer position){
        if (isInCache(url)) return true;
        else imageCache.put(url,position);
        return isInCache(url);
    }
    public boolean isInCache (String url){
        return imageCache.containsKey(url);
    }
    //todo: why do I have to add +2?
    public Bitmap getImageBitmap ( Integer position ){
        if (imageBitmaps.containsKey(position+2)) return imageBitmaps.get(position+2);
        else return null;
    }
    public Bitmap getImageBitmap ( String urlString ){
        Integer position = imageCache.get(urlString);
        if (imageBitmaps.containsKey(position+2)) return imageBitmaps.get(position+2);
        else return null;
    }
    public HashMap<String, String> getImageData (Integer position){
        return imageList.get(position);
    }
    public HashMap<String, String> getImage (int position){
        return imageList.get(position);
    }
    public HashMap<String, String> getImageDataFromCache (String url){
        return imageList.get(imageCache.get(url));
    }
    public ArrayList<HashMap<String, String>> getImageList (){
        return imageList;
    }
    public int getSize (){
        return imageList.size();
    }
    public void clearImageData (){
        imageList.clear();
        imageCache.clear();
        imageBitmaps.clear();
    }
    public void printImageData(int position){
        Log.d("MANANA imgDat link", imageList.get(position).get("link"));
    }

}
