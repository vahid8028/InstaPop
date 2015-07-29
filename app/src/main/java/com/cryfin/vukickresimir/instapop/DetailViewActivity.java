package com.cryfin.vukickresimir.instapop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class DetailViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int viewPosition = intent.getIntExtra(MainActivity.VIEW_POSITION, -1);

        GlobalData global = GlobalData.getInstance();
        ImageData imageData = global.getImageData();

        setContentView(R.layout.activity_detail_view);
        ZoomView detailImageView = (ZoomView) findViewById(R.id.detailImageView);

        String urlString = imageData.getUrlString(viewPosition);
        imageData.setImageBitmapStandard(detailImageView, viewPosition, urlString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
