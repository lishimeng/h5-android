package com.thingple.library.h5.webclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.thingple.library.h5.WebViewManager;
import com.thingple.library.h5.bridge.AbstractJsBridge;
import com.thingple.library.h5.bridge.BridgeConfig;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by lism on 2017/7/10.
 */
public class JsBridgeWebViewClient extends WebViewClient {

    private static String TAG = "" + JsBridgeWebViewClient.class.getName();

    private Context context;
    private WebView webView;

    private List<AbstractJsBridge> moduleList = new ArrayList<>();
    private Set<Class<? extends AbstractJsBridge>> registerdModules = new HashSet<>();

    public JsBridgeWebViewClient(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        init();
    }

    private void init() {

        CookieSyncManager.createInstance(context);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(TAG + "#onPageStarted", "start load page:" + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.d(TAG + "#onPageFinished", "加载完页面:" + url);

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Log.e(TAG + "#onReceivedError", "js_bridge报错了" + error.toString());
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
        Log.d(TAG + "#HttpAuthRequest", "ReceivedHttpAuthRequest. host:" + host + "\trealm:" + realm);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.startsWith("https://")) {
            if (WebViewManager.shareInstance().comonPage != null) {
                Intent intent = new Intent();
                intent.putExtra("url", url);
                intent.setClass(context, WebViewManager.shareInstance().comonPage);
                context.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        } else if (url.startsWith("http://")) {
            if (WebViewManager.shareInstance().comonPage != null) {
                Intent intent = new Intent();
                intent.putExtra("url", url);
                intent.setClass(context, WebViewManager.shareInstance().comonPage);
                context.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        } else if (url.startsWith("file:///")) {// TODO
            if (WebViewManager.shareInstance().comonPage != null) {
                Intent intent = new Intent();
                intent.putExtra("url", url);
                intent.setClass(context, WebViewManager.shareInstance().comonPage);
                context.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        }
        return false;
    }

    @SuppressLint("JavascriptInterface")
    private JsBridgeWebViewClient addBridge(Class<? extends AbstractJsBridge> clazz) {
        BridgeConfig bridgeConfig = clazz.getAnnotation(BridgeConfig.class);
        if (bridgeConfig == null) {
            return this;
        }
        Log.d(TAG + "#addBridge", "注入JS Module [" + bridgeConfig.moduleName() + "]");
        try {
            Constructor constructor = clazz.getDeclaredConstructor(WebView.class, Context.class);
            AbstractJsBridge jsBridge = (AbstractJsBridge) constructor.newInstance(webView, context);
            webView.addJavascriptInterface(jsBridge, bridgeConfig.moduleName());
            moduleList.add(jsBridge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsBridgeWebViewClient registerBridge(Class<? extends AbstractJsBridge> clazz) {
        registerdModules.add(clazz);
        return this;
    }

    public JsBridgeWebViewClient enableRegisterModules() {

        for (Class<? extends AbstractJsBridge> clazz : registerdModules) {
            addBridge(clazz);
        }
        return this;
    }

    private void destroyModule(AbstractJsBridge jsBridge) {
        jsBridge.destroy();
    }

    /**
     * 清理,释放资源
     */
    public void destroy() {
        for (AbstractJsBridge module : moduleList) {
            destroyModule(module);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
        moduleList.clear();
        moduleList = null;
    }
}
