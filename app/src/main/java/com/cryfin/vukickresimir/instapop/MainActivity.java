package com.cryfin.vukickresimir.instapop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public final static String VIEW_POSITION = "view_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageAdapter imageAdapter = new ImageAdapter(this);

        ListView listView = (ListView) findViewById(R.id.instaGrid);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        GlobalData global = GlobalData.getInstance();
        global.setListView(listView);
        global.setImageAdapter(imageAdapter);
        global.setProgressBar(progressBar);

        // Calling async task to get json data
        new LoadImageData().execute();

        global.getListView().setOnScrollListener(new EndlessScroll());

        global.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int viewPosition, long id) {
                Intent startDetailView = new Intent(MainActivity.this, DetailViewActivity.class);
                startDetailView.putExtra(VIEW_POSITION, viewPosition);
                startActivity(startDetailView);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
