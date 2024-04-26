package com.widget.listenersapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton implements IUpdate {

    final String TAG = CustomButton.this.getClass().getSimpleName();

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void updateValue(final Object value) {
        setEnabled(isEnabled() ? false : true);
    }

    @Override
    public boolean isSwitch() {
        return true;
    }

}
