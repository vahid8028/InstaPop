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
    private int position;

    public ShowInfo( Context mContext, int position){
        this.mContext = mContext;
        this.position = position;
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    public void show (){
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mainView = mInflater.inflate(R.layout.activity_main, null);
        View toastView = mInflater.inflate(R.layout.toast, (ViewGroup) mainView.findViewById(R.id.toastLayout));
        ImageView toastImageView = (ImageView) toastView.findViewById(R.id.toastImage);

        toastImageView.setImageBitmap(imageData.getImageBitmap(position));
        //toastImageView.setBackground(getDrawable(R.drawable.toast_background));

        TextView toastTextType = (TextView) toastView.findViewById(R.id.toastTextType);
        TextView toastTextUsername = (TextView) toastView.findViewById(R.id.toastTextUsername);
        TextView toastTextLink = (TextView) toastView.findViewById(R.id.toastTextLink);
        toastTextType.setText("Type: " + imageData.getImageData(position).get("type"));
        toastTextUsername.setText("Username: " + imageData.getImageData(position).get("username"));
        //toastTextLink.setText("Link: " + imageData.getImageData(position).get("link"));

        Toast toast = new Toast(mContext);

        toast.setGravity(Gravity.CENTER, 10, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);

        toast.show();
    }
}
