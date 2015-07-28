package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 *  Class for managing Views inside GridView
 */
public class ImageAdapter extends BaseAdapter {
    private static Context context;
    private static GlobalData global;
    private static ImageData imageData;
    private static final String TAG_LINK= "link";

    public ImageAdapter(Context context){
        this.context = context;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    public int getCount() {
        return imageData.getSize();
    }

    public String getItem(int position){
        return imageData.getImageList().get(position).get(TAG_LINK);
    }

    public long getItemId(int position){
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int viewPosition, View oldView, ViewGroup parent) {
        ImageView imageView;
        if (oldView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(7, 7, 7, 7);
        } else {
            imageView = (ImageView) oldView;
        }

        try{
            String urlString = imageData.getImageData(viewPosition).get(TAG_LINK);
                if (imageData.isInCache(urlString)) {
                    imageView.setImageBitmap(imageData.getImageBitmap(urlString));
                }
                else {
                    //Log.d("MANANA urlString", urlString);
                    new URLDownloadTask(imageView, viewPosition).execute(urlString);
                }
        }catch(Exception e) {
            Log.d("MANANA", "IA Exception: " + e);
        }
        return imageView;
    }

}
