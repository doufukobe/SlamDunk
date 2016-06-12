package com.fpd.slamdunk.myjoinact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.basecore.swipelayout.SwipeLayout;
import com.fpd.basecore.swipelayout.adapters.BaseSwipeAdapter;
import com.fpd.core.deleteact.DeleteAction;
import com.fpd.model.myactlist.MyActListEntity;
import com.fpd.model.success.SuccessEntity;
import com.fpd.model.userinfo.JoinedEntity;
import com.fpd.slamdunk.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by t450s on 2016/6/11.
 */
public class MyJoinActListAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<JoinedEntity> myActList;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MyJoinActListAdapter(Context context,List<JoinedEntity> myActList){
        this.context = context;
        this.myActList = myActList;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout_my_act;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context)
                .inflate(R.layout.my_join_act_layout, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        ViewHolder viewHolder;
        final JoinedEntity entity = myActList.get(position);
        if (convertView.getTag() == null){
            viewHolder = new ViewHolder();

            viewHolder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipelayout_my_act);
            viewHolder.layoutForeground = (LinearLayout) convertView.findViewById(R.id.my_act_layout);
            viewHolder.hostName = (TextView) convertView.findViewById(R.id.my_act_list_actname);
            viewHolder.actState = (TextView) convertView.findViewById(R.id.my_act_list_actstate);
            viewHolder.actTime = (TextView) convertView.findViewById(R.id.my_act_list_acttime);
            viewHolder.textViewCancel = (TextView) convertView.findViewById(R.id.textview_cancel);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.hostName.setText(entity.getActOriginator());
        String data = getDateToString(entity.getActTime());
        viewHolder.actTime.setText(data.substring(data.indexOf(" "), data.length()));
        viewHolder.actState.setText(entity.getActState());
        if (entity.getActState().equals("正在进行")) {
            viewHolder.actState.setTextColor(context.getResources().getColor(R.color.colormain));
            viewHolder.swipeLayout.setSwipeEnabled(true);
        }else{
            viewHolder.swipeLayout.setSwipeEnabled(false);
        }

        viewHolder.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDDialog dialog = new SDDialog(context, "确认取消吗?", new SDDialog.Callback() {
                    @Override
                    public void sureCallBack() {
                        DeleteAction deleteAction = new DeleteAction(context);
                        deleteAction.deleteAct(Config.userId, entity.getActId()+"", new CallBackListener<SuccessEntity>() {
                            @Override
                            public void onSuccess(SuccessEntity result) {
                                myActList.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(String Message) {

                            }
                        });
                    }

                    @Override
                    public void cancelCallBack() {

                    }
                });
                dialog.show();
            }
        });
        viewHolder.layoutForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getCount() {
        return myActList.size();
    }

    @Override
    public Object getItem(int position) {
        return myActList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    class ViewHolder {
        private SwipeLayout swipeLayout;
        private LinearLayout layoutForeground;
        private TextView hostName;
        private TextView actTime;
        private TextView actState;

        private TextView textViewCancel;

    }
}
