package com.widget.listenersapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class PeriodicAction {

    private final String TAG = PeriodicAction.this.getClass().getSimpleName();

    private Handler mHandler;
    private boolean mIsPeriodicalActionActive;
    private static Random mRandom = new Random(); //  for simulation
    private static final int PERIODICAL_INTERVAL = 250; // 1 second

    // Constructor
    public PeriodicAction() {
        mHandler = new Handler(Looper.getMainLooper());
        mIsPeriodicalActionActive = false;
    }

    public boolean isActive() {
        return mIsPeriodicalActionActive;
    }

    // Method to start the periodical action
    public void start() {
        mIsPeriodicalActionActive = true;
        mHandler.post(periodicalRunnable);
        Log.e(TAG, "Start");
    }

    // Method to stop the periodical action
    public void stop() {
        mIsPeriodicalActionActive = false;
        mHandler.removeCallbacks(periodicalRunnable);
        Log.e(TAG, "Stop");
    }

    // Runnable for the periodical action
    private Runnable periodicalRunnable = new Runnable() {
        @Override
        public void run() {
            // Perform the periodical action here
            if (mIsPeriodicalActionActive) {
                updateWidgets();
                mHandler.postDelayed(this, PERIODICAL_INTERVAL);
            }
        }
    };

    private void updateWidgets() {
    //  Walk
        //Log.d(TAG, "updateAppliances");

    //  Create shallow copy of array of listeners:
        ArrayList<IUpdate>
            listeners = UpdateAdapter.getInstance().shallowCopy();
        for (IUpdate element : listeners) {
            if (!element.isSwitch()) {
                 element.updateValue(generateRandomNumber(10, 99));
            }
            else {
                element.updateValue(null);
            }
        }
    }

    static public int generateRandomNumber(int from, int to) {
        return mRandom.nextInt(to - from + 1) + from;
    }

}
