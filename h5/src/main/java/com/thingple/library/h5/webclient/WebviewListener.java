package com.thingple.library.h5.webclient;

import android.webkit.WebView;

/**
 *
 * Created by lism on 2017/8/29.
 */
public interface WebviewListener {

    /**
     * webview加载任务完成后调用
     * @param webView
     */
    void onLoadFinish(WebView webView);
}
