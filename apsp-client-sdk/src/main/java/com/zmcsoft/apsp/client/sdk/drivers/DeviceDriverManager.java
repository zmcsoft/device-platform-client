package com.zmcsoft.apsp.client.sdk.drivers;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DeviceDriverManager {
    private static DeviceDrivers DEFAULT = new DefaultDeviceDrivers();

    public static DeviceDrivers drivers() {
        return DEFAULT;
    }

    public static void register(DeviceDriverInitialization initialization) {
        initialization.init(((DefaultDeviceDrivers) drivers()));
    }

    static {
        String[] drivers = {
                "com.zmcsoft.apsp.client.sdk.drivers.printer.DefaultPrinterDriver",
                "com.zmcsoft.apsp.client.sdk.drivers.camera.DefaultCameraDriver"
        };
        for (String driver : drivers) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
