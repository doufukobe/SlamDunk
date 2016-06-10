package com.fpd.basecore.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by solo on 2016/6/8.
 */
public class PersonalView extends ScrollView
{
    private int maxScrollLength;
    private Context mContext;
    public PersonalView(Context context)
    {
        super(context);
    }

    public PersonalView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup layout = (ViewGroup) getChildAt(0);
        if (layout != null)
        {
            View ch0 = layout.getChildAt(0);
            View ch1 = layout.getChildAt(1);
            if (ch0 != null)
            {
                maxScrollLength=ch0.getMeasuredHeight();
            }
            if (ch1 != null)
            {
                if(ch1.getMeasuredHeight()<getMeasuredHeight()+DensityUtil.dip2px(mContext,20))
                {
                    ch1.getLayoutParams().height = getMeasuredHeight()+DensityUtil.dip2px(mContext,20);
                }
            }
        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        if(listener!=null && t>=0)
        {
            listener.onScroll(t,maxScrollLength);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    float currentY=0;
    int action=0;//1:下滑 2：上滑
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        int scrollY=getScrollY();
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                currentY=ev.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                if(currentY<ev.getY())
                {
                    action=1;
                    currentY=ev.getY();
                }
                else
                {
                    action=2;
                    currentY=ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(action!=1 && scrollY<maxScrollLength)
                {
                    smoothScrollTo(0, maxScrollLength);
                }
                if(action==1 && scrollY<maxScrollLength)
                {
                    smoothScrollTo(0,0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnScrollListener
    {
        void onScroll(int t,int maxScrollLength);
    }

    private OnScrollListener listener;
    public void setOnScrollListener(OnScrollListener listener)
    {
        this.listener=listener;
    }
}
