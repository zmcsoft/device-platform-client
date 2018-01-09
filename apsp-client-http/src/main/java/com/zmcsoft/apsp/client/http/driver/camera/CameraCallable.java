package com.zmcsoft.apsp.client.http.driver.camera;

import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.DeviceCallable;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;

/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraCallable implements DeviceCallable {

    @Override
    public DeviceCallResponse call(String provider, String action, String data) {
        CameraDriver cameraDriver = DeviceDriverManager.drivers().camera(provider);
        switch (action) {
            case "open":
                return DeviceCallResponse.success(cameraDriver.open());
            case "close":
                cameraDriver.close();
                return DeviceCallResponse.success(true);
            case "isOpen":
                return DeviceCallResponse.success(cameraDriver.isOpen());
            case "getImageBase64":
                if (!cameraDriver.isOpen()) {
                    cameraDriver.open();
                }
                return DeviceCallResponse.success(cameraDriver.photographBase64());
            default:
                return DeviceCallResponse.of(404, "未知操作", null);
        }
    }
}
