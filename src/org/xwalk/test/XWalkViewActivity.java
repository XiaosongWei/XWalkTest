package org.xwalk.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import java.util.LinkedList;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class XWalkViewActivity extends Activity {
    private static final String TAG = "XWalkActivity";

    LinkedList<XWalkView> mViewHistory = new LinkedList<XWalkView>();
    ViewGroup mRootView;
    XWalkView mXWalkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);

        setContentView(R.layout.activity_xwalkview);
        mRootView = (ViewGroup) findViewById(R.id.root);
        mXWalkView = (XWalkView) findViewById(R.id.xwalkview);
        mViewHistory.add(mXWalkView);

        mXWalkView.setUIClient(new TestXWalkUIClient(mXWalkView, this, mRootView, mViewHistory));
        mXWalkView.setResourceClient(new TestXWalkResourceClient(mXWalkView));
        mXWalkView.load("file:///android_asset/create_window_1.html", null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewHistory.getLast().getNavigationHistory().canGoBack()) {
                Log.d(TAG, "Web Back");
                XWalkNavigationHistory history = mViewHistory.getLast().getNavigationHistory();
                history.navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
                return true;
            } else if (mViewHistory.size() > 1) {
                Log.d(TAG, "Window Back");
                mRootView.removeView(mViewHistory.removeLast());
                mRootView.addView(mViewHistory.getLast());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
