package com.cryfin.vukickresimir.instapop;

import android.content.Context;
import android.media.Image;
import android.widget.AbsListView; // possibly wrong import
import android.widget.GridView;

/**
 *  Created by CryFin on 7/24/2015.
 *
 *  This class manages scrolling inside the app and dynamically loads more images when needed.
 */
public class EndlessScroll implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;

    private ImageData imageData;
    private ImageAdapter imageAdapter;
    private GridView gridView;
    private String downloadUrl;

    public EndlessScroll( ImageData imageData, ImageAdapter imageAdapter,  GridView gridView, String downloadUrl ) {
        this.imageData = imageData;
        this.imageAdapter = imageAdapter;
        this.gridView = gridView;
        this.downloadUrl = downloadUrl;
    }

    // Will be called many times per second
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            new LoadMoreImages(imageData, imageAdapter, gridView, downloadUrl).execute();
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}