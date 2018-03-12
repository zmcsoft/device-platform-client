package com.zmcsoft.apsp.client.http.driver.IDCard;

import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.DeviceCallable;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDrivers;
import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard.IdentityDriver;

import java.io.IOException;

/**
 * @author zhouhao
 * @since 1.0
 */
public class IDCardCallable implements DeviceCallable {

    @Override
    public DeviceCallResponse call(String provider, String action, String data){
        IdentityDriver identityDriver = DeviceDriverManager.drivers().identity(provider);
        try {
            switch (action) {
                case "getIDCradInfo":
                    return DeviceCallResponse.success(identityDriver.getIDCradInfo().toString());
                default:
                    return DeviceCallResponse.of(404, "未知操作", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return DeviceCallResponse.of(500, "系统错误", null);
        }
    }
}
