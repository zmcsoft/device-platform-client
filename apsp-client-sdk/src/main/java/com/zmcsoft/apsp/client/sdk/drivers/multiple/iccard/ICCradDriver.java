package com.zmcsoft.apsp.client.sdk.drivers.multiple.iccard;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.ICCardEntity;

public interface ICCradDriver extends DeviceDriver {
    @Override
    default String getName() {
        return "contact-ic";
    }

    /**
     * 获取IC卡基本新信息
     * @return
     */
    ICCardEntity getICCardInfo() throws Exception;

    /**
     *  获取IC卡55域信息
     * @param AIDList 应用AID  ps:A000000333010101
     * @param aryInput 产生ARQC的数据标签 ps:S0080140923V020 演示商户
     * @return
     * @throws Exception
     */
    ICCardEntity getICCardInfoAnd55(String AIDList, String aryInput) throws Exception;
}
