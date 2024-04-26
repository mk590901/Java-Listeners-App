package com.widget.listenersapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

public class CustomImageView extends AppCompatImageView implements IUpdate {

    final String TAG = CustomImageView.this.getClass().getSimpleName();

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        UpdateAdapter.getInstance().registerListener(this);
        Log.d(TAG,"onAttachedToWindow ->[" + UpdateAdapter.getInstance().size() + "]");
    }

    @Override
    protected void onDetachedFromWindow() {
        UpdateAdapter.getInstance().unregisterListener(this);
        Log.d(TAG,"onDetachedFromWindow ->[" + UpdateAdapter.getInstance().size() + "]");
        super.onDetachedFromWindow();
    }

    @Override
    public void updateValue(Object value) {
        setEnabled(isEnabled() ? false : true);
        setImageResource(isEnabled() ? R.drawable.pinch_zoom_out : R.drawable.pinch_zoom_in);
    }

    @Override
    public boolean isSwitch() {
        return true;
    }

}

