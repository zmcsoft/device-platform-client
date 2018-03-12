package com.zmcsoft.apsp.client.http.driver.ICCard;

import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.DeviceCallable;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDrivers;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.iccard.ICCradDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard.IdentityDriver;

import java.io.IOException;

/**
 * @author zhouhao
 * @since 1.0
 */
public class ICCardCallable implements DeviceCallable {
    public static void main(String[] args) throws Exception {
        DeviceDrivers deviceDrivers = DeviceDriverManager.drivers();
        Thread.sleep(3000);
        System.out.println(deviceDrivers.iCCradDriver().getICCardInfoAnd55("A000000333010101",""));
        DeviceDriverManager.drivers().identity().getIDCradInfo();
    }
    @Override
    public DeviceCallResponse call(String provider, String action, String data){
        ICCradDriver icCradDriver = DeviceDriverManager.drivers().iCCradDriver(provider);
        try {
            switch (action) {
                case "getICCardInfo":
                    return DeviceCallResponse.success(icCradDriver.getICCardInfo().toString());
                case "getICCardInfoAnd55":
                    return DeviceCallResponse.success(icCradDriver.getICCardInfoAnd55("A000000333010101","").toString());
                default:
                    return DeviceCallResponse.of(404, "未知操作", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return DeviceCallResponse.of(500, "系统错误", null);
        }
    }
}
