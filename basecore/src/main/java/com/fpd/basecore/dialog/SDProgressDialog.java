package com.fpd.basecore.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fpd.basecore.R;

/**
 * Created by t450s on 2016/6/11.
 */
public class SDProgressDialog extends ProgressDialog {

    private ImageView progress_img;
    private ObjectAnimator progress_rotate;

    public SDProgressDialog(Context context) {
        this(context, R.style.progressDialog);
    }

    public SDProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_layout);
        WindowManager.LayoutParams params=this.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);


        progress_img= (ImageView) findViewById(R.id.progress_circile);

        this.setCancelable(false);
        this.setIndeterminate(true);
    }

    public void showProgressDialog(){

        this.show();
        progress_rotate = ObjectAnimator.ofFloat(progress_img,"rotation",0f,360f);
        progress_rotate.setRepeatMode(ValueAnimator.RESTART);
        progress_rotate.setRepeatCount(-1);
        progress_rotate.setDuration(3000);
        progress_rotate.start();
    }

    public void  hideProgressDialog(){

        if (progress_rotate !=null)
            progress_rotate.cancel();
        this.cancel();
    }

    @Override
    public void onBackPressed() {
        hideProgressDialog();
    }
}
