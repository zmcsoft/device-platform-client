package com.zmcsoft.apsp.client.sdk.drivers.softkeyboard;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

public interface SoftKeyBoardDriver extends DeviceDriver {

    @Override
    default String getName(){
        return "keyBoard";
    }

    void open();
}
