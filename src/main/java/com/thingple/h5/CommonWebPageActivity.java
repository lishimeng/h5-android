package com.thingple.h5;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public abstract class CommonWebPageActivity extends WebViewActivity {

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected abstract WebView getWebView();

    @Override
    protected Uri url() {
        Uri uri = null;
        try {
            uri = Uri.parse(url);
        } catch (Exception e) {
            Log.e(getClass().getName() + "#url", "uri转换异常", e);
        }
        return uri;
    }

    @Override
    protected abstract int viewId();
}
