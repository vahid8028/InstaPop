package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  Class for managing Views inside GridView
 */
public class ImageAdapter extends BaseAdapter {
    private static final String TAG_LINK= "link";
    private static GlobalData global;
    private static ImageData imageData;
    private static LayoutInflater mInflater;

    public ImageAdapter(Context context){
        global = GlobalData.getInstance();
        imageData = global.getImageData();
        mInflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder viewHolder;


        if (oldView == null) {
            oldView = mInflater.inflate(R.layout.grid_item, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewUser = (TextView) oldView.findViewById(R.id.gridTextUser);
            viewHolder.textViewName = (TextView) oldView.findViewById(R.id.gridTextName);
            viewHolder.textViewCaption = (TextView) oldView.findViewById(R.id.gridTextCaption);
            viewHolder.imageView = (ImageView) oldView.findViewById(R.id.gridImage);

            oldView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) oldView.getTag();
        }
        viewHolder.textViewUser.setText("User: " + imageData.getImageData(viewPosition).get("username"));
        viewHolder.textViewName.setText("Full Name: " + imageData.getImageData(viewPosition).get("full_name"));
        viewHolder.textViewCaption.setText("Caption: " + imageData.getImageData(viewPosition).get("caption"));

        try{
            String urlString = imageData.getImageData(viewPosition).get(TAG_LINK);
                if (imageData.isInCache(urlString)) {
                    viewHolder.imageView.setImageBitmap(imageData.getImageBitmapThumbnail(urlString));
                }
                else {
                    new URLDownloadTask(viewHolder.imageView, viewPosition).execute(urlString);
                }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return oldView;
    }

}
