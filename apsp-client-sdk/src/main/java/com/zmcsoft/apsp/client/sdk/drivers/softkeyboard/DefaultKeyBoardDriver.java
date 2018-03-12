package com.zmcsoft.apsp.client.sdk.drivers.softkeyboard;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import org.hswebframework.expands.shell.Result;
import org.hswebframework.expands.shell.Shell;

public class DefaultKeyBoardDriver{
    static {
        DeviceDriverManager.register(register -> {
            register.register(SoftKeyBoardDriver.class, new WinConsoleDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
}
