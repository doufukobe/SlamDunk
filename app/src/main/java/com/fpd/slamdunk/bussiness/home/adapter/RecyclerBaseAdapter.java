package com.fpd.slamdunk.bussiness.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpd.slamdunk.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by solo on 2015/12/25.
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter
{

    private Context context;
    private List<T> datas;
    private int layoutId;
    private DisplayImageOptions options;

    public RecyclerBaseAdapter(Context context, List<T> datas, int itemlayoutId)
    {
        this.context=context;
        this.datas=datas;
        this.layoutId=itemlayoutId;
        initOption();
    }

    private void initOption() {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.invite_photo)
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View itemView= LayoutInflater.from(context).inflate(layoutId,viewGroup,false);
        return new RecyclerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
    {
        final RecyclerHolder holder=(RecyclerHolder)viewHolder;
        View itemView=null;
        if(holder!=null)
        {
            itemView = holder.itemView;
        }
        if(itemView!=null)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener!=null)
                    {
                        listener.onItemClick(position);
                    }
                }
            });
        }
        convert(holder,datas.get(position),position,false);
    }

    public abstract void convert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    public class RecyclerHolder  extends RecyclerView.ViewHolder
    {

        private final SparseArray<View> mViews;

        public RecyclerHolder(View itemView)
        {
            super(itemView);
            this.mViews = new SparseArray<View>(8);
        }

        public <T extends View> T getView(int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public RecyclerHolder setText(int viewId,String text)
        {
            TextView textView=getView(viewId);
            textView.setText(text);
            return this;
        }

        public RecyclerHolder setImgByUrl(int viewId,String url)
        {
            final ImageView imageView=getView(viewId);
            ImageLoader.getInstance().loadImage(url,options,new SimpleImageLoadingListener()
            {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                {
                    if(imageView!=null && loadedImage!=null)
                    {
                        imageView.setBackground(new BitmapDrawable(loadedImage));
                    }
                }
            });
            return this;
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(int positon);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
