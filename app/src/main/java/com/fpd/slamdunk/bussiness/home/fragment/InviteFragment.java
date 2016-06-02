package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.widget.DividerItemDecoration;
import com.fpd.slamdunk.bussiness.home.widget.MyLoadMoreView;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;

/**
 * Created by t450s on 2016/6/2.
 */
public class InviteFragment extends Fragment {

    private Context mContext;
    private PullToRefreshRecyclerView listView;


    private PtrrvAdapter mAdapter;
    private static final int DEFAULT_ITEM_SIZE = 8;
    private static final int ITEM_SIZE_OFFSET = 8;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;

    private static final int TIME = 1000;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.setCount(DEFAULT_ITEM_SIZE);
                mAdapter.notifyDataSetChanged();
                listView.setOnRefreshComplete();
                listView.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET){
                    //over
                    Toast.makeText(mContext, "没有新数据", Toast.LENGTH_SHORT).show();
                    listView.onFinishLoading(false, false);
                } else {
                    mAdapter.setCount(DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET);
                    mAdapter.notifyDataSetChanged();
                    listView.onFinishLoading(true, false);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.invite_fragment,null);
        listView = (PullToRefreshRecyclerView) view.findViewById(R.id.invite_list);
        listView.setSwipeEnable(true);

        MyLoadMoreView loadMoreView = new MyLoadMoreView(mContext,listView.getRecyclerView());
        loadMoreView.setLoadmoreString("教练我想打篮球");
        loadMoreView.setLoadMorePadding(100);
        listView.setLayoutManager(new LinearLayoutManager(mContext));

        listView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });
//        listView.getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext,
//                DividerItemDecoration.VERTICAL_LIST));

//        mPtrrv.removeHeader();
        mAdapter = new PtrrvAdapter(mContext);
//        mAdapter.setCount(0);
        mAdapter.setCount(DEFAULT_ITEM_SIZE);
        listView.setAdapter(mAdapter);
        listView.onFinishLoading(false, false);
        return view;
    }


    private class PtrrvAdapter extends RecyclerView.Adapter<PtrrvAdapter.ViewHolder> {

        protected int mCount = 0;
        protected Context context = null;

        public static final int TYPE_HEADER = 0;
        public static final int TYPE_HISVIDEO = 1;
        public static final int TYPE_MESSAGE = 2;


        public PtrrvAdapter(Context context) {
           this.context = context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.invite_list_item, null);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        public void setCount(int count){
            mCount = count;
        }

        @Override
        public int getItemCount() {
            return mCount;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
