package com.fpd.slamdunk.bussiness.sportnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fpd.basecore.config.URLContans;
import com.fpd.basecore.dialog.SDProgressDialog;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

/**
 * Created by solo on 2016/6/5.
 */
public class SportActivity extends CommenActivity
{

    private WebView webView;
    private String url;
    private SDProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        Intent intent=getIntent();
        url=intent.getStringExtra(URLContans.SPORT_NEWS_URL);
        initViews();
    }

    private void initViews()
    {
        progressDialog = new SDProgressDialog(this);
        progressDialog.showProgressDialog();
        init();
    }

    private void init(){
        webView=(WebView)findViewById(R.id.id_sportnews_webview);
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                progressDialog.hideProgressDialog();
            }
        });
    }
    
}
