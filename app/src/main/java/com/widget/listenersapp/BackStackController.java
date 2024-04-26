package com.widget.listenersapp;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class BackStackController {

    final String TAG =  BackStackController.this.getClass().getSimpleName();

    private Fragment mFragment;

    public void openFragment(MainActivity activity, Fragment fragment){

        activity.getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void register(Fragment fragment) {
        this.mFragment = fragment;
        Log.d(TAG,"BackStackController.register");
    }

    public void unregister() {
        this.mFragment = null;
        Log.d(TAG,"BackStackController.unregister");
    }

    public boolean isAttached() {
        return (mFragment == null) ? false : true;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
