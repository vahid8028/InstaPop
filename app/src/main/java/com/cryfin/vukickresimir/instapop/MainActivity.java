package com.cryfin.vukickresimir.instapop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    // URL to get JSON data from instagram
    private static final String instaUrl = "https://api.instagram.com/v1/media/popular?client_id=72b1d107078b47a8a2c40ebc77b97a66";
    private String pagingUrl = null;

    private ProgressBar progressBar;
    private GridView gridView;
    private ImageData imageData;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.instaGrid);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ArrayList<HashMap<String, String>> imageList = new ArrayList<>();
        HashMap<String, Integer> imageCache = new HashMap<>();
        imageData = new ImageData(imageList, imageCache);

        // Calling async task to get json
        imageAdapter = new ImageAdapter(this, imageData);
        new LoadImageData( instaUrl, imageData, gridView, imageAdapter).execute();

        gridView.setOnScrollListener(new EndlessScroll(this, instaUrl, imageData, gridView));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                View toastView = getLayoutInflater().inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toastLayout));
                ImageView toastImageView = (ImageView)toastView.findViewById(R.id.toastImage);

                toastImageView.setImageBitmap(imageData.getImageBitmap(position));
                //toastImageView.setBackground(getDrawable(R.drawable.toast_background));

                TextView toastTextType = (TextView)toastView.findViewById(R.id.toastTextType);
                TextView toastTextUsername = (TextView)toastView.findViewById(R.id.toastTextUsername);
                TextView toastTextLink = (TextView)toastView.findViewById(R.id.toastTextLink);
                toastTextType.setText("Type: " + imageData.getImageData(position).get("type"));
                toastTextUsername.setText("Username: " + imageData.getImageData(position).get("username"));
                //toastTextLink.setText("Link: " + imageData.getImageData(position).get("link"));

                Toast toast = new Toast(MainActivity.this);

                toast.setGravity(Gravity.CENTER, 10, 10);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);

                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
