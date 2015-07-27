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

    private ImageData imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.instaGrid);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ArrayList<HashMap<String, String>> imageList = new ArrayList<>();
        HashMap<String, Integer> imageCache = new HashMap<>();
        imageData = new ImageData(imageList, imageCache);

        // Calling async task to get json
        ImageAdapter imageAdapter = new ImageAdapter(this, imageData);
        new LoadImageData(imageData, imageAdapter, gridView, instaUrl).execute();

        //gridView.setOnScrollListener(new EndlessScroll(imageData, imageAdapter, gridView, instaUrl));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ShowInfo showInfo = new ShowInfo(MainActivity.this, imageData, position);
                showInfo.show();
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
