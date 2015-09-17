package org.xwalk.test;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.ValueCallback;

import java.util.LinkedList;

import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkJavascriptResult;

public class TestXWalkUIClient extends XWalkUIClient {
    private static final String TAG = "XWalkActivity";

    Activity mActivity;
    ViewGroup mRootView;
    LinkedList<XWalkView> mViewHistory;

    public TestXWalkUIClient(XWalkView xwalkView, Activity activity, ViewGroup rootView,
            LinkedList<XWalkView> viewHistory) {
        super(xwalkView);

        mActivity = activity;
        mRootView = rootView;
        mViewHistory = viewHistory;
    }

    @Override
    public void onJavascriptCloseWindow(XWalkView view) {
        Log.d(TAG, "onJavascriptCloseWindow");
        super.onJavascriptCloseWindow(view);
    }

    @Override
    public void onRequestFocus(XWalkView view) {
        Log.d(TAG, "onRequestFocus");
    }

    @Override
    public boolean onJavascriptModalDialog(XWalkView view, JavascriptMessageType type,
            String url, String message, String defaultValue, XWalkJavascriptResult result) {
        Log.d(TAG, "onJavascriptModalDialog: " + type + "," + url + "," + message
                + "," + defaultValue + "," + result);
        return super.onJavascriptModalDialog(view, type, url, message, defaultValue, result);
    }

    @Override
    public void onReceivedTitle(XWalkView view, String title) {
        Log.d(TAG, "onReceivedTitle: " + title);
    }

    @Override
    public boolean onCreateWindowRequested(XWalkView view, InitiateBy initiator,
            ValueCallback<XWalkView> callback) {
        Log.d(TAG, "onCreateWindowRequested: " + initiator);
        TestXWalkView newView = new TestXWalkView(mActivity);

        callback.onReceiveValue(newView);

        mRootView.removeAllViews();
        mRootView.addView(newView);
        mViewHistory.add(newView);

        newView.setUIClient(new TestXWalkUIClient(newView, mActivity, mRootView, mViewHistory));
        newView.setResourceClient(new TestXWalkResourceClient(newView));
        return true;
    }
}
