package com.widget.listenersapp;

public class ObjectWidgetHelper {

    public String[] getProperties(MockObject object) {
        if (object == null) {
            return null;
        }
        int numberCapabilities = object.getNumberCapabilities();
        String[] capabilitiesName = new String[numberCapabilities];
        for (int i = 0; i < numberCapabilities; i++) {
            capabilitiesName[i] = "PROPERTY " + (i+1);
        }
        return capabilitiesName;
    }

}
