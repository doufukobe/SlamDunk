package com.fpd.slamdunk.bussiness.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpd.basecore.application.BaseApplication;
import com.fpd.basecore.config.Config;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.application.CommenApplication;
import com.fpd.slamdunk.join.JoinActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by t450s on 2016/6/3.
 */
public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    protected Context context = null;

    private static final int WITH_PHOTO = 0;
    private static final int WITHOUT_PHOTO =1;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DisplayImageOptions options;


    private List<InviteListEntity> inviteList;

    public InviteAdapter(Context contenxt, List<InviteListEntity> inviteList){
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        if (viewType == 1)
            view = LayoutInflater.from(context).inflate(R.layout.invite_list_item, null);
        else
            view = LayoutInflater.from(context).inflate(R.layout.invite_list_ph,null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (null == inviteList.get(position).getActImg() ||  inviteList.get(position).getActImg().isEmpty())
            return WITHOUT_PHOTO;
        else
            return WITH_PHOTO;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.distance.setText(inviteList.get(position).getAddressDist()+"m");
        holder.theme_name.setText(inviteList.get(position).getActName());
        int cur = inviteList.get(position).getCurPeopleNum();
        int max = inviteList.get(position).getMaxPeopleNum();
        holder.member.setText(cur + "/" + max);
        holder.people_name.setText(inviteList.get(position).getActOriginatorName());
        String date = sdf.format(new Date(inviteList.get(position).getActTime()*1000));
        holder.time.setText(date.substring(date.indexOf("-")+1,date.lastIndexOf(":")));

        if (!inviteList.get(position).getActImg().isEmpty()){
            ImageLoader.getInstance().displayImage(Config.headUrl+inviteList.get(position).getActImg(), holder.ac_img, options);
        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.default_ball);
            holder.ac_img.setImageBitmap(bitmap);
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView people_name;
        TextView member;
        TextView time;
        TextView theme_name;
        TextView distance;
        ImageView ac_img;
        RelativeLayout rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (RelativeLayout) itemView.findViewById(R.id.invite_layout);
            people_name = (TextView) itemView.findViewById(R.id.invite_people_name);
            member = (TextView) itemView.findViewById(R.id.invite_member);
            time = (TextView) itemView.findViewById(R.id.invite_time);
            theme_name = (TextView) itemView.findViewById(R.id.invite_sport_name);
            distance = (TextView) itemView.findViewById(R.id.invite_distance);
            ac_img = (ImageView) itemView.findViewById(R.id.invite_img);
            int height = BaseApplication.getScreenWidth() * 9 / 16;
        }
    }
}

