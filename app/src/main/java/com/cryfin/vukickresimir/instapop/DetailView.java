package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 *  Class for showing detail view of selected image with zooming capabilities.
 */
public class DetailView {

    private Context mContext;
    private static GlobalData global;
    private static ImageData imageData;
    private String urlString;
    private int viewPosition;

    public DetailView( Context mContext, int viewPosition ){
        this.mContext = mContext;
        this.viewPosition = viewPosition;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    public void show (){
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mainView = mInflater.inflate(R.layout.activity_main, null);
        View detailView = mInflater.inflate(R.layout.toast_detail_view, (ViewGroup) mainView.findViewById(R.id.detailView));
        ImageView detailImageView = (ImageView) detailView.findViewById(R.id.detailImageView);

        urlString = imageData.getUrlString(viewPosition);
        imageData.setImageBitmapStandard( detailImageView, viewPosition, urlString);

        Toast toast = new Toast(mContext);

        toast.setGravity(Gravity.CENTER, 10, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(detailView);

        toast.show();

    }

}
