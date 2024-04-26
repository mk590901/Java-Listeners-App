package com.widget.listenersapp;

import java.util.UUID;

public class MockObject {
    private String mName;
    private boolean mUsed = false;
    private String mUuid = UUID.randomUUID().toString();
    private int mNumberProperties;

    public MockObject(final String name) {
        this.mName = name;
    }

    public MockObject setNumberProperties(final int numberProperties) {
        mNumberProperties = numberProperties;
        return this;
    }

    public String getName() {
        return mName;
    }

    public String getUuid() {
        return mUuid;
    }

    public int getNumberProperties() {
        return mNumberProperties;
    }

}
