package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.fpd.basecore.config.URLContans;
import com.fpd.model.sportnews.ArticleEntity;
import com.fpd.model.sportnews.SportNewsEntity;
import com.fpd.model.sportnews.SportNewsEntityData;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.adapter.RecyclerBaseAdapter;
import com.fpd.slamdunk.bussiness.home.widget.MyLoadMoreView;
import com.fpd.slamdunk.bussiness.sportnews.activity.SportActivity;
import com.google.gson.Gson;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solo on 2016/6/5.
 */
public class ShareFragment extends Fragment implements RecyclerBaseAdapter.OnItemClickListener
{
    private Context mContext;
    private View mContentView;
    //List相关
    private PullToRefreshRecyclerView mSharList;
    private RecyclerBaseAdapter mAdapter;
    private List<ArticleEntity> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("TAG","onCreateView");
        mContentView = inflater.inflate(R.layout.fragment_share, container, false);
        mContext=getActivity();
        initViews();
        return mContentView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void initViews()
    {
        mSharList=(PullToRefreshRecyclerView)mContentView.findViewById(R.id.id_share_list);
        initList();
    }

    private void initEvents()
    {
        mAdapter.setOnItemClickListener(this);
    }

    private void initList()
    {
        mSharList.setSwipeEnable(true);
        MyLoadMoreView loadMoreView = new MyLoadMoreView(mContext,mSharList.getRecyclerView());
        loadMoreView.setLoadMorePadding(100);
        mSharList.setLayoutManager(new LinearLayoutManager(mContext));
        mSharList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Log.i("TAG", "onRefresh");
            }
        });
        mSharList.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener()
        {
            @Override
            public void onLoadMoreItems()
            {
                Log.i("TAG", "onLoadMoreItems");
            }
        });
        mSharList.setAdapter(mAdapter);
        mSharList.onFinishLoading(true, false);
        mDatas=new ArrayList<>();
        mAdapter=new RecyclerBaseAdapter<ArticleEntity>(mContext,mDatas,R.layout.sportnews_list_item)
        {
            @Override
            public void convert(RecyclerBaseAdapter.RecyclerHolder holder, ArticleEntity item, int position, boolean isScrolling)
            {
                holder.setText(R.id.id_sportnews_title,item.title);
                holder.setImgByUrl(R.id.id_sportnews_img,item.img);
            }
        };
        mSharList.setAdapter(mAdapter);
        getShareList("1");
        initEvents();
    }

    private void getShareList(String page) {
        Parameters para = new Parameters();
        para.put("id", "nba");
        para.put("page", page);
        ApiStoreSDK.execute("http://apis.baidu.com/3023/news/channel",
                ApiStoreSDK.GET, para,
                new ApiCallBack()
                {
                    @Override
                    public void onSuccess(int status, String responseString)
                    {
                        SportNewsEntity sne = new Gson().fromJson(responseString, SportNewsEntity.class);
                        if (sne != null)
                        {
                            SportNewsEntityData data = sne.data;
                            if (data != null)
                            {
                                List<ArticleEntity> article_list = data.article;
                                for (ArticleEntity article : article_list)
                                {
                                    mDatas.add(article);
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(int status, String responseString, Exception e)
                    {
                        Log.i("TAG", "onError, status: " + status);
                        Log.i("TAG", "errMsg: " + (e == null ? "" : e.getMessage()));
                    }
                });

    }

    @Override
    public void onItemClick(int positon)
    {
        Intent intent=new Intent(mContext, SportActivity.class);
        intent.putExtra(URLContans.SPORT_NEWS_URL,mDatas.get(positon).url);
        startActivity(intent);
    }
}
