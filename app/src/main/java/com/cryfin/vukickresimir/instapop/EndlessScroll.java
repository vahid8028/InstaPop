package com.cryfin.vukickresimir.instapop;

import android.widget.AbsListView; // possibly wrong import


/**
 *  Created by CryFin on 7/24/2015.
 *
 *  This class manages scrolling inside the app and dynamically loads more images when needed.
 */
//todo: disable rescrolling to the top ater loading new images
public class EndlessScroll implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;

    private static GlobalData global;
    private static ImageData imageData;

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
                currentPage++;
                //Log.d("MANANA: ", "previous Total: "+ previousTotal);
            }
        }
        if (!imageData.isLoadingImages() && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            new LoadImageData().execute();
            imageData.setLoadingImages(true);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}