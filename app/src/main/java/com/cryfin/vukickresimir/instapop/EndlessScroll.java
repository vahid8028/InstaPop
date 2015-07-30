package com.cryfin.vukickresimir.instapop;

import android.widget.AbsListView;


/**
 *  This class manages scrolling inside the app and dynamically loads more images when needed.
 */
public class EndlessScroll implements AbsListView.OnScrollListener {

    private static GlobalData global;
    private static ImageData imageData;

    private int visibleThreshold = 8;
    private int previousTotal = 0;

    public EndlessScroll() {
        global = GlobalData.getInstance();
        imageData = global.getImageData();
    }

    // Will be called many times per second
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (imageData.isLoadingImages()) {
            if (totalItemCount > previousTotal) {
                imageData.setLoadingImages(false);
                previousTotal = totalItemCount;
            }
        }
        else if ((totalItemCount - firstVisibleItem) <= (visibleItemCount + visibleThreshold)) {
            imageData.setLoadingImages(true);
            new LoadImageData().execute();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}