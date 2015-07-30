package com.cryfin.vukickresimir.instapop;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DetailViewActivity extends AppCompatActivity implements View.OnTouchListener {

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    PointF lastPoint = new PointF();
    PointF middlePoint = new PointF();
    float oldDistance = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int viewPosition = intent.getIntExtra(MainActivity.VIEW_POSITION, -1);

        GlobalData global = GlobalData.getInstance();
        ImageData imageData = global.getImageData();

        setContentView(R.layout.activity_detail_view);
        ImageView detailImageView = (ImageView) findViewById(R.id.detailImageView);
        detailImageView.setOnTouchListener(this);

        String urlString = imageData.getUrlString(viewPosition);
        imageData.setImageBitmapStandard(detailImageView, viewPosition, urlString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // only one finger is down
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                lastPoint.set(event.getX(), event.getY());
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // two fingers are down
                oldDistance = fingerDistance(event);
                if (oldDistance > 5f) {
                    savedMatrix.set(matrix);
                    setMiddlePoint(middlePoint, event);
                    mode = ZOOM;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - lastPoint.x, event.getY() - lastPoint.y); // transform matrix
                }
                else if (mode == ZOOM) {
                    // pinch zooming
                    float newDistance = fingerDistance(event);
                    if (newDistance > 5f){
                        matrix.set(savedMatrix);
                        scale = newDistance / oldDistance;
                        // scale the matrix. (scale > 1) => zoom in; (scale < 1) => zoom out
                        matrix.postScale(scale, scale, middlePoint.x, middlePoint.y);
                    }
                }
                break;

            case MotionEvent.ACTION_UP: // first finger lifted
            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                mode = NONE;
                break;
        }
        view.setImageMatrix(matrix); // update View on the screen
        return true;
    }

    private float fingerDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void setMiddlePoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


}
