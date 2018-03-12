package com.zmcsoft.apsp.client.sdk.drivers.multiple.device;

import com.sun.jna.NativeLong;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DriverCallException;

import java.nio.ByteBuffer;

import static com.zmcsoft.apsp.client.sdk.drivers.multiple.Mtx_32Library.INSTANCE;

public class DefaultMultipleDeviceDriver implements MultipleDeviceDriver {

    public static Integer icdev = null;

    static {
        DeviceDriverManager.register(register -> {
            register.register(MultipleDeviceDriver.class, new DefaultMultipleDeviceDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
    @Override
    public boolean deviceOpen(int port, long baud) {
        return (icdev = INSTANCE.device_open((short)port, new NativeLong(baud))) > 0;
    }

    @Override
    public boolean deviceClose() throws DriverCallException {
        boolean result = INSTANCE.device_close(icdev) == 0;
        if(result) icdev = null;
        else throw new DriverCallException("关闭设备失败");
        return result;
    }

    @Override
    public boolean deviceLedctrl(int ledctrl) {
        return INSTANCE.device_ledctrl(icdev, (byte) ledctrl) == 0;
    }

    @Override
    public boolean getDeviceStatus(int ndev_status) {
        return INSTANCE.get_device_status(icdev, ByteBuffer.allocate(ndev_status)) == 0;
    }

    @Override
    public boolean deviceReset() {
        return INSTANCE.device_reset(icdev, (byte)1) == 0;
    }

    @Override
    public String getDeviceType() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[2]);
        INSTANCE.device_gettype(byteBuffer);
        return new String(new byte[byteBuffer.remaining()]);
    }

    @Override
    public boolean setDeviceBaud(int module, long baud) {
        return INSTANCE.device_setbaud(icdev,(byte)module, new NativeLong(baud)) == 0;
    }
}
