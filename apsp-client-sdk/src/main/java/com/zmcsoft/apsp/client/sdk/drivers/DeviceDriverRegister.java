package com.zmcsoft.apsp.client.sdk.drivers;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface DeviceDriverRegister {
    String DEFAULT_DRIVER_PROVIDER = "DEFAULT";

    <T extends DeviceDriver> void register(Class<T> type, T driver, String provider);
}
