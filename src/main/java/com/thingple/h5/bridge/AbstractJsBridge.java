package com.thingple.android.bridge;

import android.content.Context;
import android.webkit.WebView;

/**
 *
 * Created by lism on 2017/8/14.
 */

public abstract class AbstractJsBridge implements Bridge {

    private WebView webView;

    private Context context;

    public AbstractJsBridge(WebView webView, Context context) {
        this.webView = webView;
        this.context = context;
    }

    public WebView getWebView() {
        return this.webView;
    }

    public Context getContext() {
        return this.context;
    }

    protected String moduleName() {
        BridgeConfig bridgeConfig = getClass().getAnnotation(BridgeConfig.class);
        if (bridgeConfig == null) {
            return null;
        }
        return bridgeConfig.moduleName();
    }
}
