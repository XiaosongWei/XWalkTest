package org.xwalk.test;

import android.util.Log;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class TestXWalkResourceClient extends XWalkResourceClient {
    private static final String TAG = "XWalkActivity";

    public TestXWalkResourceClient(XWalkView xwalkView) {
        super(xwalkView);
    }

    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading: " + url);
        return false;
    }
}
