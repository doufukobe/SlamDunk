package com.fpd.slamdunk.bussiness.myact.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.fpd.model.userinfo.HostedEntity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.myactdetial.MyActDetailActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by t450s on 2016/6/10.
 */
public class MyActListAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<HostedEntity> myActList;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public MyActListAdapter(Context context,List<HostedEntity> myActList){
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
                .inflate(R.layout.my_act_list_item, null);
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        ViewHolder viewHolder;
        final HostedEntity entity = myActList.get(position);
        if (convertView.getTag() == null){
            viewHolder = new ViewHolder();

            viewHolder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipelayout_my_act);
            viewHolder.layoutForeground = (LinearLayout) convertView.findViewById(R.id.my_act_layout);
            viewHolder.actName = (TextView) convertView.findViewById(R.id.my_act_list_actname);
            viewHolder.actState = (TextView) convertView.findViewById(R.id.my_act_list_actstate);
            viewHolder.actTime = (TextView) convertView.findViewById(R.id.my_act_list_acttime);
            viewHolder.textViewCancel = (TextView) convertView.findViewById(R.id.textview_cancel);
            viewHolder.actMem = (TextView) convertView.findViewById(R.id.my_act_list_actmember);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.actName.setText(entity.getActName());
        final String data = getDateToString(entity.getActTime()*1000);
        viewHolder.actTime.setText(data);
        viewHolder.actMem.setText(entity.getCurPeopleNum()+"");
        if (entity.getActState().equals("1")) {
            viewHolder.actState.setText("正在进行");
            viewHolder.actState.setTextColor(context.getResources().getColor(R.color.colormain));
            viewHolder.swipeLayout.setSwipeEnabled(true);
        }else{
            viewHolder.actState.setText("已完成");
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
                Intent intent = new Intent(context, MyActDetailActivity.class);
                intent.putExtra("ACTID",entity.getActId()+"");
                context.startActivity(intent);
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
        private TextView actName;
        private TextView actTime;
        private TextView actState;
        private TextView actMem;
        private TextView textViewCancel;

    }
}
