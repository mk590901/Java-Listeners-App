package com.widget.listenersapp;

import java.util.UUID;

public class MockObject {
    private String mName;
    private int mIndex;
    private boolean mUsed = false;
    private String mUuid = UUID.randomUUID().toString();
    private int mNumberCapabilities;
    private boolean mConnected = false;

    public MockObject(final String name) {
        this.mName = name;
    }

    public MockObject setNumberCapabilities(final int nCapabilities) {
        mNumberCapabilities = nCapabilities;
        return this;
    }

    public MockObject setConnected(final boolean enable) {
        mConnected = enable;
        return this;
    }

    public String getName() {
        return mName;
    }

    public String getMACAddress() {
        return "00:05:6E:7E:8F:F9";
    }

    public String getUuid() {
        return mUuid;
    }

    public boolean isConnected() {
        return mConnected;
    }

    public int getNumberCapabilities() {
        return mNumberCapabilities;
    }

    public void setIndex(final int index) {
        mIndex = index;
    }

    public void setUsed(final boolean used) {
        mUsed = used;
    }

    public boolean isUsed() {
        return mUsed;
    }

//    public String[] getCapabilities() {
//        String[] capabilitiesName = new String[mNumberCapabilities];
//        for (int i = 0; i < mNumberCapabilities; i++) {
//            capabilitiesName[i] = "Capability" + (i+1);
//        }
//        return capabilitiesName;
//    }

}
