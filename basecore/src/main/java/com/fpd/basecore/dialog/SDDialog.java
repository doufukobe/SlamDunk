package com.fpd.basecore.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpd.basecore.R;

/**
 * Created by t450s on 2016/6/11.
 */
public class SDDialog extends Dialog {

    private View dialogView;
    private TextView dialogMessage;
    private Button dialogSure;
    private Button dialogCancel;

    private Context context;

    private RelativeLayout mRelativelayout;
    private Callback callback;
    private String message;
    public SDDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    public SDDialog(Context context,String message,Callback callback) {
        super(context, R.style.dialog_untran);
        this.context = context;
        this.callback = callback;
        this.message = message;
        init();
    }

    private void init(){
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        mRelativelayout = (RelativeLayout) dialogView.findViewById(R.id.my_dialog_layout);
        dialogMessage = (TextView) dialogView.findViewById(R.id.dialog_message);
        dialogSure = (Button) dialogView.findViewById(R.id.dialog_btn_sure);
        dialogCancel = (Button) dialogView.findViewById(R.id.dialog_btn_cancel);
        setContentView(dialogView);
        dialogMessage.setText(message);
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.sureCallBack();
                }
                dismiss();
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback !=null){
                    callback.cancelCallBack();
                }
                dismiss();
            }
        });
    }

    public interface Callback{
        public void sureCallBack();

        public void cancelCallBack();
    }

    public void dialogSureText(String text)
    {
        dialogSure.setText(text);
    }

    public void dialogCancelText(String text)
    {
        dialogCancel.setText(text);
    }
}
