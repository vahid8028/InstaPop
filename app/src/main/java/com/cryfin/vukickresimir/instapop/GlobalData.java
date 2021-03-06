package com.cryfin.vukickresimir.instapop;

import android.widget.ListView;
import android.widget.ProgressBar;

/**
 *  Singleton class for storing global data
 */
public class GlobalData {

    private static GlobalData firstInstance = null;

    // URL to get JSON data from instagram
    public static final String instagramUrl = "https://api.instagram.com/v1/media/popular?client_id=72b1d107078b47a8a2c40ebc77b97a66";
    public static ListView listView;
    public static ImageData imageData = new ImageData();
    public static ImageAdapter imageAdapter;
    public static ProgressBar progressBar;

    private GlobalData() {}

    public static GlobalData getInstance(){
        if (firstInstance == null){
            // synchronisation is inside the if statement so that it can't slow down the program after first initialisation
            synchronized (GlobalData.class){
                //in case that multiple threads pass the first inspection
                if (firstInstance == null) {
                    firstInstance = new GlobalData();
                }
            }
        }
        return firstInstance;
    }

    public void setListView( ListView listView ){ GlobalData.listView = listView; }
    public void setProgressBar( ProgressBar progressBar ){ GlobalData.progressBar = progressBar; }
    public void setImageAdapter( ImageAdapter imageAdapter ){ GlobalData.imageAdapter = imageAdapter; }

    public String getDownloadUrl(){ return instagramUrl; }
    public ListView getListView() { return listView; }
    public ImageAdapter getImageAdapter(){ return imageAdapter; }
    public ImageData getImageData(){ return imageData; }
    public ProgressBar getProgressBar() { return progressBar; }
}
