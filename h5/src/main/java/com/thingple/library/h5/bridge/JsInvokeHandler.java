package com.thingple.library.h5.bridge;

import android.util.Log;
import android.webkit.WebView;

import java.util.Map;

/**
 * js调用器
 * Created by lism on 2017/8/18.
 */

public class JsInvokeHandler {

    private String module;

    private String function;

    private Map<String, String> paramMap;

    private WebView webView;

    public JsInvokeHandler(WebView webView) {
        this.webView = webView;
    }

    /**
     * 执行 js
     */
    public void invoke() {

        if (module == null) {
            module = "window";
        }
        String functionName = joinFunction();
        String param = joinParam();
        String content = "if (" + module + " && " + functionName + ") {" + functionName + "(" + param + ");}";
        final String js = "javascript:(function() {" + content + "})();";

        Log.d(getClass().getName() + "#invoke", "JS回调:" + js);
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(js);
            }
        });
    }

    public JsInvokeHandler module(String module) {
        this.module = module;
        return this;
    }

    public JsInvokeHandler function(String function) {
        this.function = function;
        return this;
    }

    public JsInvokeHandler params(Map<String, String> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    private String joinFunction() {

        return module + "." + function;
    }

    private String joinParam() {

        boolean first = true;
        String text = "{";
        for (String name : this.paramMap.keySet()) {
            if (first) {
                first = false;
            } else {
                text += ", ";
            }
            text += (name + ":'" + paramMap.get(name) + "'");
        }

        text += "}";
        return text;
    }
}
