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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageAdapter imageAdapter = new ImageAdapter(this);

        GridView gridView = (GridView) findViewById(R.id.instaGrid);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        final GlobalData global = GlobalData.getInstance();
        global.setGridView(gridView);
        global.setImageAdapter(imageAdapter);
        global.setProgressBar(progressBar);

        // Calling async task to get json
        new LoadImageData().execute();

        global.getGridView().setOnScrollListener(new EndlessScroll());
        global.getGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ShowInfo showInfo = new ShowInfo(MainActivity.this, position);
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
