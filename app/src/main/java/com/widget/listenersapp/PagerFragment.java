package com.widget.listenersapp;

import static com.widget.listenersapp.GuiConstants.objProperties;
import static com.widget.listenersapp.GuiConstants.objName;
import static com.widget.listenersapp.GuiConstants.objUuid;
import static com.widget.listenersapp.GuiConstants.fragmentName;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    final String TAG = PagerFragment.this.getClass().getSimpleName();
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private OnBackPressedCallback onBackPressedCallback;
    private ImageView mBackButton;
    private ImageView mPlayImageView;

    @Nullable
    private MainActivity mActivity;
    private String mObjUuid;
    private String mObjName;
    private ArrayList<String> mProperties;

    public PagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null
        &&  arguments.containsKey(objUuid)
        &&  arguments.containsKey(objName)
        &&  arguments.containsKey(objProperties)) {
            mObjUuid = arguments.getString(objUuid);
            mObjName = arguments.getString(objName);
            mProperties = arguments.getStringArrayList(objProperties);
        }

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        boolean isConnected = mProperties.size() == 0 ? false : true;

        mPlayImageView = view.findViewById(R.id.play_image_header);

        TextView textViewDeviceName = view.findViewById(R.id.textViewObjectName);
        textViewDeviceName.setText(TextUtils.isEmpty(mObjName)
                ? "???"
                : mObjName);

    //  Define action for back (<-) button
        mBackButton = view.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(v -> {

            Log.e(TAG, "PagerFragment.handleOnBackPressed X");

            requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(PagerFragment.this).commit();

            onBackPressedCallback.remove();

        });

        mPlayImageView = view.findViewById(R.id.play_image_header);
        mPlayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick.mCloudImageView");
                if (mActivity != null) {
                    PeriodicAction periodicAction = mActivity.getPeriodicalAction();
                    if (periodicAction.isActive()) {
                        periodicAction.stop();
                        mPlayImageView.setImageResource(R.drawable.play_circle);
                    }
                    else {
                        periodicAction.start();
                        mPlayImageView.setImageResource(R.drawable.icon_pause_circle_idle);
                    }
                }
            }
        });

    //  Capabilities
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(mActivity != null ? mActivity : getActivity());

        addCapabilitiesFragments();

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> tab.setText(mProperties.get(position))
        ).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                // Called when the scroll state changes
                //Log.e(TAG, "onPageScrollStateChanged->[" + state + "]");
            }
        });

        showCapabilitiesLayout(isConnected);

        return view;
    }

    public void showCapabilitiesLayout(final boolean isConnected) {
       tabLayout.setVisibility(View.VISIBLE);
       viewPager.setVisibility(View.VISIBLE);
    }

    public void back() {
        if (mBackButton != null) {
            mBackButton.performClick();
        }
    }

    private void addCapabilitiesFragments() {
        for (int i = 0; i < mProperties.size(); i++) {
            addCapabilityFragment(i);
        }
    }

    private void addCapabilityFragment(int i) {
        Bundle bundle = new Bundle();
        String name = mProperties.get(i);
        bundle.putString(fragmentName, name);
        Fragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, name);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        onAttached(context);
    }

    private void onAttached(final Context context) {
        Log.d(TAG, "PagerFragment.onAttached");
        if (context instanceof MainActivity) {
            mActivity = (MainActivity)context;

            BackStackController backStackController = mActivity.getDetailedViewController();
            if (backStackController != null) {
                backStackController.register(this);
            }
            else {
                Log.e(TAG,"onAttached=>BackStackController is NULL");
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Your code to handle app going to the background
        Log.d(TAG, "going to the background");
        back();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Your code to handle Fragment resume
        Log.d(TAG, "going to the foreground");
    }

    @Override
    public void onDetach() {

        Log.d(TAG, "PagerFragment.onDetach");

        if (mActivity != null) {
            BackStackController backStackController = mActivity.getDetailedViewController();
            if (backStackController != null) {
                backStackController.unregister();
            }
            mActivity = null;
         }
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "PagerFragment.onStart");

        // Creating an instance of OnBackPressedCallback
        onBackPressedCallback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "PagerFragment.handleOnBackPressed");

                requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(PagerFragment.this)
                    .commit();

                onBackPressedCallback.remove();

            }
        };

        // Adding the callback to the OnBackPressedDispatcher
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

}
