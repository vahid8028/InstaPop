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
//todo: implement dynamic cache emptying
public class ImageData {

    private static ArrayList<HashMap<String, String>> imageList;
    private static HashMap<String, Integer> imageCache;
    private static HashMap<Integer, Bitmap> imageBitmaps;
    public static boolean loadingImages = true;

    public ImageData(){
        imageList = new ArrayList<>();
        imageCache = new HashMap<>();
        this.imageBitmaps = new HashMap<>();
    }

    public boolean isLoadingImages(){
        return loadingImages;
    }
    public void setLoadingImages(boolean loading){
        synchronized (ImageData.class){
            loadingImages = loading;
        }
    }
    //todo: check if addImageBitmap position counting works with this method
    public int addImageBitmap ( Bitmap bmp){
        int position = imageBitmaps.size();
        imageBitmaps.put(position, bmp);
        return position;
    }
    public boolean addImageBitmap ( Integer position, Bitmap bmp){
        imageBitmaps.put(position-1, bmp);
        return imageBitmaps.get(position - 1) == bmp;
    }
    public boolean addImageData (HashMap<String, String> image){
        return imageList.add(image);
    }
    public boolean addImageDataToCache (String urlString, Integer bmpPosition){
        if (isInCache(urlString)) return true;
        else imageCache.put(urlString,bmpPosition);
        return isInCache(urlString);
    }
    public boolean isInCache (String urlString){
        return imageCache.containsKey(urlString);
    }
    //todo: why do I have to add +2? -> no longer works
    public Bitmap getImageBitmap ( Integer position ){
        if (imageBitmaps.containsKey(position)) return imageBitmaps.get(position);
        else return null;
    }
    public Bitmap getImageBitmap ( String urlString ){
        Integer position = imageCache.get(urlString);
        if (imageBitmaps.containsKey(position)) return imageBitmaps.get(position);
        else return null;
    }
    public HashMap<String, String> getImageData (Integer position){
        return imageList.get(position);
    }
    public HashMap<String, String> getImage (int position){
        return imageList.get(position);
    }
    public HashMap<String, String> getImageDataFromCache (String urlString){
        return imageList.get(imageCache.get(urlString));
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
