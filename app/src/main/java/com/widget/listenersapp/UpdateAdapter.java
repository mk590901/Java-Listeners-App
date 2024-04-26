package com.widget.listenersapp;

import android.util.Log;

import java.util.ArrayList;

public class UpdateAdapter {
    private final String TAG = UpdateAdapter.this.getClass().getSimpleName();;
    private final ArrayList<IUpdate>
            mListeners = new ArrayList<>();
    private final Object mListenerLock = new Object();

    private static UpdateAdapter instance = null;

    public synchronized static UpdateAdapter getInstance() {
        return instance;
    }

    public static synchronized void newInstance() {
        if (instance == null) {
            instance = new UpdateAdapter();
        }
    }

    public int size() {
        int size = 0;
        synchronized (mListenerLock) {
            size = mListeners.size();
        }
        return size;
    }

    public void registerListener(IUpdate callback) {
        synchronized (mListenerLock) {
            if (mListeners.contains(callback)) {
                return;
            }
            mListeners.add(callback);
        }
        //Log.d(TAG, "+ [" + mListeners.size() + "]");
    }

    public void unregisterListener(IUpdate callback) {
        synchronized (mListenerLock) {
            mListeners.remove(callback);
        }
        //Log.d(TAG, "- [" + mListeners.size() + "]");
    }

    public ArrayList<IUpdate> shallowCopy() {
        ArrayList<IUpdate> copiedList = null;
        synchronized (mListenerLock) {
            copiedList = new ArrayList<>(mListeners);
        }
        return copiedList;
    }

}
