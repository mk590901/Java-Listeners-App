package com.widget.listenersapp;

import static com.widget.listenersapp.GuiConstants.fragmentName;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;


public class PageFragment extends Fragment {

    final String TAG = PageFragment.this.getClass().getSimpleName();
    private String mName;
    private String mUuid = UUID.randomUUID().toString();
    private CustomTextView mTextValue;

    @Nullable
    private MainActivity mActivity;

    public PageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null
        &&  arguments.containsKey(fragmentName)) {
            mName = arguments.getString(fragmentName);
            Log.e(TAG, "mName->" + mName);
        }

        Log.d(TAG, "onCreate");

        //  Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        setControls(view);
        return view;
    }

    private void setControls(final View view) {
        mTextValue = view.findViewById(R.id.value1);
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.pinch_zoom_out);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        onAttached(context);
    }

    private void onAttached(final Context context) {
        Log.d(TAG, "PageFragment.onAttached [" + mName + "]");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "PageFragment.onDetach [" + mName + "]");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Perform any cleanup related to views here
        // For example, you can release resources or references to views
        // This is a good place to set views to null to free up memory
        // Remember, view references should not be held beyond the lifecycle of the view
        // For example:
        // viewReference = null;
        Log.d(TAG,"onDestroyView [" + mName + "]");
    }
}
