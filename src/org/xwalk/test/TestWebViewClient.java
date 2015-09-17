package org.xwalk.test;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestWebViewClient extends WebViewClient {
    private static final String TAG = "XWalkActivity";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading: " + url);
        return false;
    }
}
