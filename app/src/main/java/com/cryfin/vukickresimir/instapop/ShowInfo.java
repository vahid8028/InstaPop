package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  Class for showing additional info about selected image.
 */
public class ShowInfo {

    private Context mContext;
    private static GlobalData global;
    private static ImageData imageData;
    private static String urlString;
    private int viewPosition;

    public ShowInfo( Context mContext, int viewPosition ){
        this.mContext = mContext;
        this.viewPosition = viewPosition;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    public void show (){
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mainView = mInflater.inflate(R.layout.test_activity_main, null);
        View toastView = mInflater.inflate(R.layout.test_toast, (ViewGroup) mainView.findViewById(R.id.toastLayout));
        ImageView toastImageView = (ImageView) toastView.findViewById(R.id.toastImage);

        urlString = imageData.getUrlString(viewPosition);
        toastImageView.setImageBitmap(imageData.getImageBitmapThumbnail(urlString));

        TextView toastTextPosition = (TextView) toastView.findViewById(R.id.toastTextPosition);
        TextView toastTextType = (TextView) toastView.findViewById(R.id.toastTextType);
        TextView toastTextUsername = (TextView) toastView.findViewById(R.id.toastTextUsername);

        toastTextPosition.setText("ViewID: " + viewPosition);
        toastTextType.setText("Type: " + imageData.getImageData(viewPosition).get("type"));
        toastTextUsername.setText("Username: " + imageData.getImageData(viewPosition).get("username"));

        Toast toast = new Toast(mContext);

        toast.setGravity(Gravity.CENTER, 10, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);

        toast.show();
    }
}
