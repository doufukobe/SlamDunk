package com.fpd.slamdunk.bussiness.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpd.basecore.application.BaseApplication;
import com.fpd.basecore.config.Config;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.myactdetial.adapter.HeadAdapter;
import com.fpd.slamdunk.join.JoinActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by t450s on 2016/6/23.
 */
public class NewInviteAdapter extends BaseAdapter {

    protected Context context = null;

    private static final int WITH_PHOTO = 0;
    private static final int WITHOUT_PHOTO =1;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DisplayImageOptions options;


    private List<InviteListEntity> inviteList;

    public NewInviteAdapter(Context contenxt, List<InviteListEntity> inviteList){
        this.context = contenxt;
        this.inviteList = inviteList;
        initOption();
    }

    private void initOption() {

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(false)
                .build();
    }

    public void fillView(List<InviteListEntity> inviteList){
        this.inviteList = inviteList;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return inviteList.size();
    }

    @Override
    public Object getItem(int position) {
        return inviteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            if (getItemViewType(position) == WITHOUT_PHOTO){
                convertView = LayoutInflater.from(context).inflate(R.layout.invite_list_item, null);
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.invite_list_ph,null);
            }
            viewHolder = new ViewHolder();
            viewHolder.rootView = (RelativeLayout) convertView.findViewById(R.id.invite_layout);
            viewHolder.people_name = (TextView) convertView.findViewById(R.id.invite_people_name);
            viewHolder.member = (TextView) convertView.findViewById(R.id.invite_member);
            viewHolder.time = (TextView) convertView.findViewById(R.id.invite_time);
            viewHolder.theme_name = (TextView) convertView.findViewById(R.id.invite_sport_name);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.invite_distance);
            viewHolder.ac_img = (ImageView) convertView.findViewById(R.id.invite_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.distance.setText(inviteList.get(position).getAddressDist()+"m");
        viewHolder.theme_name.setText(inviteList.get(position).getActName());
        int cur = inviteList.get(position).getCurPeopleNum();
        int max = inviteList.get(position).getMaxPeopleNum();
        viewHolder.member.setText(cur + "/" + max);
        viewHolder.people_name.setText(inviteList.get(position).getActOriginatorName());
        String date = sdf.format(new Date(inviteList.get(position).getActTime()*1000));
        viewHolder.time.setText(date.substring(date.indexOf("-")+1,date.lastIndexOf(":")));

        if (!inviteList.get(position).getActImg().isEmpty()){
            ImageLoader.getInstance().displayImage(Config.headUrl + inviteList.get(position).getActImg(), viewHolder.ac_img, options);
        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_ball);
            viewHolder.ac_img.setImageBitmap(bitmap);
        }
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JoinActivity.class);
                intent.putExtra("ACTID",inviteList.get(position).getActId()+"");
                if (!inviteList.get(position).getActImg().isEmpty()){
                    intent.putExtra("ACTPHOTOURL", Config.headUrl + inviteList.get(position).getActImg());
                }
                intent.putExtra("DISTANCE",inviteList.get(position).getAddressDist());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == inviteList.get(position).getActImg() ||  inviteList.get(position).getActImg().isEmpty())
            return WITHOUT_PHOTO;
        else
            return WITH_PHOTO;
    }

    class ViewHolder {
        TextView people_name;
        TextView member;
        TextView time;
        TextView theme_name;
        TextView distance;
        ImageView ac_img;
        RelativeLayout rootView;

    }
}
