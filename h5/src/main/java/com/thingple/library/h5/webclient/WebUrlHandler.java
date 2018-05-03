package com.thingple.library.h5.webclient;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 *
 * Created by lism on 2017/8/30.
 */
public class WebUrlHandler {

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }
}
