package com.fpd.slamdunk.bussiness.myactdetial.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpd.basecore.util.CircleImage;
import com.fpd.model.acthead.MyActHeadEntity;
import com.fpd.slamdunk.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by t450s on 2016/6/15.
 */
public class HeadAdapter extends RecyclerView.Adapter<HeadAdapter.ViewHolder> {

    private Context context;
    private List<MyActHeadEntity> heads;
    private DisplayImageOptions options;
    public HeadAdapter(Context context,List<MyActHeadEntity> heads){
        this.context = context;
        this.heads = heads;
        initOption();
    }

    private void initOption() {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.user_default_icon)
                .showImageForEmptyUri(R.mipmap.user_default_icon)
                .showImageOnFail(R.mipmap.user_default_icon)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_act_head_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(heads.get(position).getUserName());
        if (heads.get(position).getHeadUrl() ==null || heads.get(position).getHeadUrl().isEmpty()){
            holder.head.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.user_default_icon));
        }else{
            ImageLoader.getInstance().loadImage(heads.get(position).getHeadUrl(), options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    holder.head.setBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return heads.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImage head;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            head = (CircleImage) itemView.findViewById(R.id.member_head);
            name = (TextView) itemView.findViewById(R.id.member_name);
        }
    }
}
