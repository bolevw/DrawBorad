package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/3/15.
 */
public class PictureView extends View {


    private Paint mPaint;

    private Path mPath;

    private float startX;
    private float startY;

    private float currentX;
    private float currentY;

    Bitmap bitmap;
    Canvas mCanvas;

    public PictureView(Context context) {
        super(context);
    }

    public PictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        mCanvas.drawPath(mPath, mPaint);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(dip2px(5));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.WHITE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        currentX = event.getX();
        currentY = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mPath.reset();
                mPath.moveTo(currentX, currentY);

                startX = currentX;
                startY = currentY;
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mPath.quadTo(startX, startY, currentX, currentY);
                startX = currentX;
                startY = currentY;
                invalidate();
                break;
            }
        }
        return true; // true 很关键
    }

    private int dip2px(int value) {
        return (int) (getResources().getDisplayMetrics().density * value + 0.5f);
    }
}
