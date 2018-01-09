package com.zmcsoft.apsp.client.http.driver;

import com.zmcsoft.apsp.client.core.DeviceCallResponse;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface DeviceCallable {
    DeviceCallResponse call(String provider, String action, String data);
}
