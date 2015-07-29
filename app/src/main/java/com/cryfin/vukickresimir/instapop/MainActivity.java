package com.cryfin.vukickresimir.instapop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageAdapter imageAdapter = new ImageAdapter(this);

        GridView gridView = (GridView) findViewById(R.id.instaGrid);
        gridView.setFastScrollEnabled(true);
/*

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
*/

        GlobalData global = GlobalData.getInstance();
        global.setGridView(gridView);
        global.setImageAdapter(imageAdapter);
        //global.setProgressBar(progressBar);

        // Calling async task to get json data
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
