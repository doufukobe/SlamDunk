package com.fpd.basecore.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyScrollView extends ScrollView {


    private float myX;
    private float myY;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:{
                float deltaX = x - myX;
                float deltaY = y - myY;
                myX = x;
                myY = y;
                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    return false;
                }else{
                    return true;
                }
            }
        }
        return super.onInterceptHoverEvent(event);
    }
}
