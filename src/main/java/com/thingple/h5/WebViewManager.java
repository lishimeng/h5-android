package com.thingple.h5;

import android.content.Context;

/**
 *
 * Created by lism on 2017/8/24.
 */

public class WebViewManager {

    private static WebViewManager ins;

    private Context context;

    public Class<? extends CommonWebPageActivity> comonPage = null;

    public static void init(Context context) {
        ins = new WebViewManager(context);
    }

    public static WebViewManager shareInstance() {
        return ins;
    }

    private WebViewManager(Context context) {
        this.context = context.getApplicationContext();
    }
}
