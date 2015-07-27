package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Singleton class
 */
public class GlobalData {

    private static GlobalData firstInstance = null;
    private static boolean firstThread = true;

    // URL to get JSON data from instagram
    public static final String instagramUrl = "https://api.instagram.com/v1/media/popular?client_id=72b1d107078b47a8a2c40ebc77b97a66";

    public static Context mContext;
    public static GridView gridView;
    public static ProgressBar progressBar;
    public static ImageData imageData = new ImageData();;
    public static ImageAdapter imageAdapter;

    private GlobalData() {
    }
    public static GlobalData getInstance(){
        if (firstInstance == null){
            // synchonisation is inside the if statement so that it can't slow down the program after first initialisation
            synchronized (GlobalData.class){
                //in case that multiple threads pass the first inspection
                if (firstInstance == null) {
                    firstInstance = new GlobalData();
                }
            }
        }
        return firstInstance;
    }

    public void setGridView( GridView gridView ){ this.gridView = gridView; }
    public void setProgressBar( ProgressBar progressBar ){ this.progressBar = progressBar; }
    public void setImageAdapter( ImageAdapter imageAdapter ){ this.imageAdapter = imageAdapter; }
    public void setMainContext( Context mContext ){ this.mContext = mContext; }

    public String getDownloadUrl(){ return instagramUrl; }
    public GridView getGridView() {return gridView; }
    public ProgressBar getProgressBar() {return progressBar; }
    public ImageData getImageData(){ return imageData; }
    public ImageAdapter getImageAdapter(){ return imageAdapter; }
    public Context getMainContext(){ return mContext; }
}
