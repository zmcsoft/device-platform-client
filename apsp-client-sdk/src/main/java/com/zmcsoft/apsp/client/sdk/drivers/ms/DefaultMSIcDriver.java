package com.zmcsoft.apsp.client.sdk.drivers.ms;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;

public class DefaultMSIcDriver implements MSIcDriver{

    static {
        DeviceDriverManager.register(register -> {
            register.register(MSIcDriver.class, new DefaultMSIcDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
    @Override
    public String getCardInfo() {
        return "磁条卡信息";
    }
}
