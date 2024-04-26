package com.widget.listenersapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView implements IUpdate {

    final String TAG = CustomTextView.this.getClass().getSimpleName();

    public CustomTextView(@NonNull Context context) {
        super(context);
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setText(String.valueOf(value));
    }

    @Override
    public boolean isSwitch() {
        return false;
    }
}