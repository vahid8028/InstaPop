package com.cryfin.vukickresimir.instapop;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  This class is used to store and manage all images used inside the app.
 */
//todo: implement dynamic cache emptying
public class ImageData {

    private static ArrayList<ConcurrentHashMap<String, String>> imageList;
    private static LruCache<String, Integer> imagePositionMap;    //urlString, viewPosition
    private static LruCache<Integer, String> imageUrlMap;         //viewPosition, urlString
    private static LruCache<String, Bitmap> imageBitmapCache;     //urlString, bmp
    public static boolean loadingImages = true;
    private static final int cacheSize = 100;    // max number of objects stored in cache
    /*
    private static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int cacheSize = maxMemory / 4;     // max number of bytes stored in cache
    //todo: override sizeof() in LruCache
    */

    public ImageData(){
        imageList = new ArrayList<>();
        imageBitmapCache = new LruCache<>(cacheSize);
        imagePositionMap = new LruCache<>(cacheSize);
        imageUrlMap = new LruCache<>(cacheSize);
    }

    public boolean isLoadingImages(){
        return loadingImages;
    }
    public void setLoadingImages(boolean loading){
        synchronized (ImageData.class){
            loadingImages = loading;
        }
    }

    //returns previous value stored under urlString or null
    public Bitmap addImageBitmapToCache(String urlString, Bitmap bmp){
        return imageBitmapCache.put(urlString, bmp);
    }
    public Bitmap getImageBitmap ( String urlString ){
        if ( null != urlString )
            return imageBitmapCache.get(urlString);
        else return null;
    }

    public Integer addImagePositionToCache(String urlString, Integer viewPosition){
        imageUrlMap.put(viewPosition, urlString);
        return imagePositionMap.put(urlString, viewPosition);
    }
    public Integer getImagePosition(String urlString){
        if ( null != urlString )
            return imagePositionMap.get(urlString);
        else return null;
    }

    public boolean isInCache (String urlString){
        if (null != imageBitmapCache.get(urlString))
            return true;
        else return false;
    }

    public boolean addImageData (ConcurrentHashMap<String, String> image){
        return imageList.add(image);
    }
    public ConcurrentHashMap<String, String> getImageData (Integer position){
        return imageList.get(position);
    }

    public int getSize (){
        return imageList.size();
    }
    public String getUrlString(Integer viewPosition){
        if ( null != viewPosition )
            return imageUrlMap.get(viewPosition);
        else return null;
    }
    public ArrayList<ConcurrentHashMap<String, String>> getImageList (){
        return imageList;
    }

/*    public void clearImageData (){
        imageList.clear();
        imageBitmapCache.evictAll();
        imagePositionMap.evictAll();
    }
    */

}
