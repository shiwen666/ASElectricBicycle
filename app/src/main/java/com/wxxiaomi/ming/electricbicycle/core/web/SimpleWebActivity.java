package com.wxxiaomi.ming.electricbicycle.core.web;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.web.base.BaseWebActivity;

/**
 * Created by 12262 on 2016/11/10.
 * 对于只有一个webview的页面的activity
 */
public class SimpleWebActivity extends BaseWebActivity {
    private Toolbar toolbar;

    @Override
    protected int initViewAndReutrnWebViewId(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        return R.id.web_view;
    }

    @Override
    protected void initData() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView.loadUrl("file:///android_asset/edit.html");
    }
}
