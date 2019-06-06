package com.thingple.library.h5;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.thingple.library.h5.webclient.JsBridgeWebChromeClient;
import com.thingple.library.h5.webclient.JsBridgeWebViewClient;
import com.thingple.library.view.BaseActivity;


/**
 *
 * Created by lism on 2017/7/10.
 */
public abstract class WebViewActivity extends BaseActivity {

    private WebView webView;

    JsBridgeWebViewClient webViewClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(viewId());
        this.webView = getWebView();
        if (webView != null) {
            initWebView();
            Uri url = url();
            if (url != null) {
                webView.loadUrl(url.toString());
            }
        }
    }

    protected abstract void initWebViewClient(JsBridgeWebViewClient webViewClient);

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webViewClient = new JsBridgeWebViewClient(this, webView);
        initWebViewClient(webViewClient);
        webViewClient.enableRegisterModules();// 加载js模块
        webView.setWebViewClient(webViewClient);

        JsBridgeWebChromeClient webChromeClient = new JsBridgeWebChromeClient();
        webView.setWebChromeClient(webChromeClient);

        webView.clearCache(true);
        webView.getSettings().setBuiltInZoomControls(false);// 不显示缩放工具
        webView.getSettings().setSupportZoom(false);// 禁止缩放
        webView.getSettings().setJavaScriptEnabled(true);// js生效
        webView.setVerticalScrollBarEnabled(false);// 横向滚动条消失
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        // Cookie

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webViewClient != null) {
            JsBridgeWebViewClient client = webViewClient;
            webViewClient = null;
            client.destroy();
        }
    }

    /**
     * web_view
     */
    protected abstract WebView getWebView();

    /**
     * 需要加载的资源
     */
    protected abstract Uri url();

    /**
     * R页面ID
     */
    protected abstract int viewId();

}
