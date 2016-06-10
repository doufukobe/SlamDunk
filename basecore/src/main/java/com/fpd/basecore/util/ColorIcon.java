package com.fpd.basecore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by solo on 2016/6/3.
 */
public class ColorIcon extends View
{
    private Bitmap sourceBitmap;
    private Bitmap mBitmap;
    private Bitmap scaledBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private int mColor;

    public ColorIcon(Context context)
    {
        this(context, null);
    }

    public ColorIcon(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(sourceBitmap!=null)
        {
            doDraw(canvas);
        }
    }

    private void doDraw(Canvas canvas)
    {
        mBitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        if(sourceBitmap!=null && getWidth()!=0 && getHeight()!=0)
        {
            initPaint();
            float sx=Math.min(1.0f*getWidth()/sourceBitmap.getHeight(),
                    1.0f*getHeight()/sourceBitmap.getHeight());
            float sy=sx;
            float px=sourceBitmap.getWidth();
            float py=sourceBitmap.getHeight();
            scaledBitmap=scaleBitmap(sourceBitmap,sx,sy,px,py);
            mCanvas.drawBitmap(scaledBitmap, getWidth() / 2 - scaledBitmap.getWidth() / 2,
                    getHeight() / 2 - scaledBitmap.getHeight() / 2, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            mCanvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
            canvas.drawBitmap(mBitmap,0,0,null);
        }
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.sourceBitmap=bitmap;
        if(sourceBitmap!=null)
        {
            invalidate();
        }
    }

    public void setColor(int color)
    {
        this.mColor=color;
    }

    private void initPaint()
    {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
    }

    private Bitmap scaleBitmap(Bitmap bitmap,float sx,float sy,float px,float py)
    {
        Matrix matrix=new Matrix();
        matrix.postScale(sx,sy,px,py);
        bitmap=Bitmap.createBitmap(bitmap,0,0,
                bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return bitmap;
    }
}
