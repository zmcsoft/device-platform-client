package com.zmcsoft.apsp.client.sdk.drivers.voice;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.softkeyboard.SoftKeyBoardDriver;
import com.zmcsoft.apsp.client.sdk.drivers.softkeyboard.WinConsoleDriver;

public class DefaultVoiceDriver {
    static {
        DeviceDriverManager.register(register -> {
            register.register(VoiceDriver.class, new WinVoiceDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
}
