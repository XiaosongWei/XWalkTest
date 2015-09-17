package org.xwalk.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.LinkedList;

public class WebViewActivity extends Activity {
    LinkedList<WebView> mViewHistory = new LinkedList<WebView>();
    ViewGroup mRootView;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        mRootView = (ViewGroup) findViewById(R.id.root);
        mWebView = (WebView) findViewById(R.id.webview);
        mViewHistory.add(mWebView);

        WebSettings settings = mWebView.getSettings();
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new TestWebChromeClient(this, mRootView, mViewHistory));
        mWebView.setWebViewClient(new TestWebViewClient());

        mWebView.loadUrl("file:///android_asset/create_window_1.html");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewHistory.getLast().canGoBack()) {
                Log.d("WebView", "Web Back");
                mViewHistory.getLast().goBack();
                return true;
            } else if (mViewHistory.size() > 1) {
                Log.d("WebView", "Window Back");
                mRootView.removeView(mViewHistory.removeLast());
                mRootView.addView(mViewHistory.getLast());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
