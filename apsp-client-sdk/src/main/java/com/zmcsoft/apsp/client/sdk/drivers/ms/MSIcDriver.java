package com.zmcsoft.apsp.client.sdk.drivers.ms;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

public interface MSIcDriver extends DeviceDriver{

    @Override
    default String getName(){
        return "magnetic-stripe";
    }

    /**
     * 获取磁条卡信息
     * @return
     */
    String getCardInfo();
}
