package com.fpd.slamdunk.bussiness.login.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.util.DensityUtil;

/**
 * Created by solo on 2016/6/2.
 */
public class MyEditTextView extends EditText implements
        View.OnFocusChangeListener,TextWatcher
{

    private Drawable drawableRight;
    private boolean hasFocus;
    private int rightDrawablePadding;
    public MyEditTextView(Context context) {
        this(context,null);
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rightDrawablePadding= DensityUtil.dip2px(context, 10);
        init();
    }

    private void init(){
        drawableRight=getCompoundDrawables()[2];
        if(drawableRight==null){
            drawableRight=getResources().getDrawable(R.mipmap.search_clear_normal);
            drawableRight.setBounds(-rightDrawablePadding,0,drawableRight.getIntrinsicWidth()-rightDrawablePadding,drawableRight.getIntrinsicHeight());
        }
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setRightIconVisible(false);
    }

    private void setRightIconVisible(boolean visible){
        Drawable right= visible ? drawableRight : null;
        Drawable[] drawables=getCompoundDrawables();
        setCompoundDrawables(drawables[0],drawables[1],right,drawables[3]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(drawableRight!=null){
                    boolean clear =event.getX() > (getWidth() - getTotalPaddingRight()-rightDrawablePadding)
                                    && (event.getX() < ((getWidth() - getPaddingRight()-rightDrawablePadding)));
                    if(clear){
                        setText("");
                        if(listener!=null){
                            listener.onClearClick();
                        }
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(hasFocus){
            setRightIconVisible(getText().length()>0);
            if(mOnTextChangeListener!=null)
            {
                mOnTextChangeListener.onTextChange();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus=hasFocus;
        if(hasFocus){
            setRightIconVisible(getText().length()>0);
        }else {
            setRightIconVisible(false);
        }
    }

    public interface OnClearClickListener
    {
        void onClearClick();
    }
    private OnClearClickListener listener;
    public void setOnClearClickListener(OnClearClickListener listener)
    {
        this.listener=listener;
    }

    public interface OnTextChangeListener
    {
        void onTextChange();
    }
    private OnTextChangeListener mOnTextChangeListener;
    public void setOnTextChangeListener(OnTextChangeListener listener)
    {
        this.mOnTextChangeListener=listener;
    }
}
