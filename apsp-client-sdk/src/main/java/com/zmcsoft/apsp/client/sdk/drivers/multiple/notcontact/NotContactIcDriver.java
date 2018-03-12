package com.zmcsoft.apsp.client.sdk.drivers.multiple.notcontact;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

public interface NotContactIcDriver extends DeviceDriver {
    @Override
    default String getName() {
        return "contact-ic";
    }

    /**
     * 获取卡号
     * @return
     */
    String getCardNo();

    /**
     * 获取姓名
     * @return
     */
    String getCardName();

    /**
     * 获取卡号和姓名
     * @return
     */
    String getCardNoAndName();
}
