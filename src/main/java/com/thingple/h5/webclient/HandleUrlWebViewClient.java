package com.thingple.h5.webclient;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * shouldOverrideUrlLoading 可以以外挂的方式执行<br/>
 *
 * @see WebUrlHandler
 * Created by lism on 2017/8/30.
 */
public class HandleUrlWebViewClient extends WebViewClient {

    private WebUrlHandler webUrlHandler;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (webUrlHandler != null) {
            return webUrlHandler.shouldOverrideUrlLoading(view, request);
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (webUrlHandler != null) {
            return webUrlHandler.shouldOverrideUrlLoading(view, url);
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public void setWebUrlHandler(WebUrlHandler webUrlHandler) {
        this.webUrlHandler = webUrlHandler;
    }
}
