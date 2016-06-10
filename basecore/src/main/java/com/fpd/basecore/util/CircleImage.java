package com.fpd.basecore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by solo on 2016/6/2.
 */
public class CircleImage extends View
{
    private Bitmap sourceBitmap;
    private Bitmap scaledBitmap;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;

    public CircleImage(Context context)
    {
        this(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.i("TAG","onDraw");
        super.onDraw(canvas);
        if(sourceBitmap!=null)
        {
            doDraw(canvas);
        }
    }

    /**
     * @param bitmap：
     */
    public void setBitmap(Bitmap bitmap)
    {
        Log.i("TAG","setBitmap");
        this.sourceBitmap=bitmap;
        if(sourceBitmap!=null)
        {
            invalidate();
        }
    }

    private void doDraw(Canvas canvas)
    {
        Log.i("TAG","doDraw");
        mBitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        if(sourceBitmap!=null && getWidth()!=0 && getHeight()!=0)
        {
            initPaint();
            float radius=Math.min(getWidth()/2,getHeight()/2);
            float sx=Math.max(2.0f * radius / sourceBitmap.
                    getWidth(), 2.0f * radius / sourceBitmap.getHeight());
            float sy=sx;
            float px=sourceBitmap.getWidth();
            float py=sourceBitmap.getHeight();
            scaledBitmap=scaleBitmap(sourceBitmap,sx,sy,px,py);
            mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            mCanvas.drawBitmap(scaledBitmap, getWidth() / 2 - scaledBitmap.getWidth() / 2,
                    getHeight() / 2 - scaledBitmap.getHeight() / 2, mPaint);
            canvas.drawBitmap(mBitmap,0,0,null);
        }
    }

    private Bitmap scaleBitmap(Bitmap bitmap,float sx,float sy,float px,float py)
    {
        Log.i("TAG","scaleBitmap");
        Matrix matrix=new Matrix();
        matrix.postScale(sx,sy,px,py);
        bitmap=Bitmap.createBitmap(bitmap,0,0,
                bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return bitmap;
    }

    private void initPaint()
    {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
    }

}
