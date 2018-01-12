package com.zmcsoft.apsp.client.sdk.drivers;

import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrinterDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DefaultDeviceDrivers implements DeviceDrivers, DeviceDriverRegister {

    private final Map<Class<? extends DeviceDriver>, Map<String, DeviceDriver>> drivers = new HashMap<>();

    @SuppressWarnings("all")
    private <T extends DeviceDriver> Map<String, T> getProviders(Class<T> type) {
        return (Map<String, T>) drivers.computeIfAbsent(type, (t) -> new HashMap<>());
    }

    private <T extends DeviceDriver> T getDriver(Class<T> type, String provider) {
        T driver = getProviders(type).get(provider);
        if (driver == null) {
            throw new UnsupportedOperationException("找不到设备驱动:" + provider);
        }
        return driver;
    }

    @Override
    public CameraDriver camera() {
        return getDriver(CameraDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public PrinterDriver printer() {
        return getDriver(PrinterDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    protected String checkProvider(String provider) {
        if (null == provider || provider.isEmpty()) {
            return DEFAULT_DRIVER_PROVIDER;
        }
        return provider;
    }

    @Override
    public CameraDriver camera(String provider) {
        return getDriver(CameraDriver.class, checkProvider(provider));
    }

    @Override
    public PrinterDriver printer(String provider) {
        return getDriver(PrinterDriver.class, checkProvider(provider));
    }

    @Override
    public <T extends DeviceDriver> void register(Class<T> type, T driver, String provider) {
        getProviders(type).put(provider, driver);
    }

    @Override
    public <T extends DeviceDriver> T unregister(Class<T> type, String provider) {
        return getProviders(type).remove(provider);
    }
}
