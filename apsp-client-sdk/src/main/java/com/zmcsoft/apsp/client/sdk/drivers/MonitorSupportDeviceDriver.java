package com.zmcsoft.apsp.client.sdk.drivers;

import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface MonitorSupportDeviceDriver {
    Map<Integer, String> getState();

    int getState(int project);
}
