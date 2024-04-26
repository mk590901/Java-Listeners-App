package com.widget.listenersapp;

import static com.widget.listenersapp.GuiConstants.objProperties;
import static com.widget.listenersapp.GuiConstants.objName;
import static com.widget.listenersapp.GuiConstants.objUuid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.this.getClass().getSimpleName();

    private OnBackPressedCallback onBackPressedCallback;

    final private ObjectWidgetHelper objectWidgetHelper = new ObjectWidgetHelper();

    private BackStackController mBackStackController = new BackStackController();;

    public BackStackController getDetailedViewController() {
        return mBackStackController;
    }

    final private PeriodicAction mPeriodicAction = new PeriodicAction();

    public PeriodicAction getPeriodicalAction() {
        return mPeriodicAction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        createBackDispatcher();
        setLayout();
        UpdateAdapter.newInstance();
    }

    private void createBackDispatcher() {
        onBackPressedCallback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle back button press here
                // For example, you can navigate back or perform any desired action
                // You can also call isEnabled() to check if callback is enabled or not
                Log.d(TAG, "MainActivity.handleOnBackPressed");

                showDialogAndExit();
            }
        };

        // Adding the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }

    private void setLayout() {
        Button button = findViewById(R.id.createFragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"OnClick");
                MockObject
                    object = new MockObject("Custom Views").setNumberCapabilities(6);
                showMeasurementPager(object);
            }
        });
    }

    private void showMeasurementPager(final MockObject appliance) {
        Log.d(TAG, "******* showMeasurementPager *******");
        Bundle bundle = new Bundle();
        bundle.putString(objUuid, appliance.getUuid());
        bundle.putString(objName, appliance.getName());
        bundle.putStringArrayList(objProperties,
            new ArrayList<>(Arrays.asList(objectWidgetHelper.getProperties(appliance)))
        );
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        openDetailedViewFragment(fragment);
    }

    public void openDetailedViewFragment(final Fragment fragment) {
        BackStackController
            controller = mBackStackController;
        if (controller != null) {
            controller.openFragment(this,fragment);
        }
    }


    private void showDialogAndExit() {
        new ProgressDialogBox(MainActivity.this,
                getString(R.string.exit_app),
                getString(R.string.are_you_sure),
                getString(R.string.cancel),
                getString(R.string.ok),
                new IActionResult() {
                    @Override
                    public void onSuccess() {
                        if (mPeriodicAction.isActive()) {
                            mPeriodicAction.stop();
                        }

                        onBackPressedCallback.remove();
                        finish();
                    }

                    @Override
                    public void onFailed() {
                    }
                });
    }

}