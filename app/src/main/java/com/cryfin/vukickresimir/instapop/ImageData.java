package com.cryfin.vukickresimir.instapop;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CryFin on 7/26/2015.
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
        if (imageBitmaps.get(position) == bmp) return true;
        else return false;
    }
    public boolean addImageBitmap ( Integer position, Bitmap bmp){
        imageBitmaps.put(position-1, bmp);
        if (imageBitmaps.get(position-1) == bmp) return true;
        else return false;
    }
    public boolean addImageData (HashMap<String, String> image){
        return imageList.add(image);
    }
    public boolean addImageDataToCache (String url, Integer position){
        if (isInCache(url)) return true;
        else imageCache.put(url,position);
        if (isInCache(url)) return true;
        else return false;
    }
    public boolean isInCache (String url){
        return imageCache.containsKey(url);
    }
    //todo: why do I have to add +2?
    public Bitmap getImageBitmap ( Integer position ){
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
