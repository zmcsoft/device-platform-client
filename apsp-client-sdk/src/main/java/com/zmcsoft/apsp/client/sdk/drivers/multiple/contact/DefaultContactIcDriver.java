package com.zmcsoft.apsp.client.sdk.drivers.multiple.contact;

import java.nio.ByteBuffer;
import static com.zmcsoft.apsp.client.sdk.drivers.multiple.Mtx_32Library.INSTANCE;
import static com.zmcsoft.apsp.client.sdk.drivers.multiple.device.DefaultMultipleDeviceDriver.icdev;

public class DefaultContactIcDriver implements ContactIcDriver {
    @Override
    public String getCardNo() {
        ByteBuffer cardNo = ByteBuffer.wrap(new byte[512]);
        INSTANCE.iReadICCardNoAndName(icdev, 0, cardNo, ByteBuffer.wrap(new byte[512]), ByteBuffer.wrap(new byte[1024]));
        return new String(new byte[cardNo.remaining()]);
    }
    @Override
    public String getCardName() {
        ByteBuffer cardName = ByteBuffer.wrap(new byte[512]);
        INSTANCE.iReadICCardNoAndName(icdev, 0, ByteBuffer.wrap(new byte[512]), cardName, ByteBuffer.wrap(new byte[1024]));
        return new String(new byte[cardName.remaining()]);
    }
    @Override
    public String getCardNoAndName() {
        ByteBuffer cardNo = ByteBuffer.wrap(new byte[512]);
        ByteBuffer cardName = ByteBuffer.wrap(new byte[512]);
        ByteBuffer lpErrMsg = ByteBuffer.wrap(new byte[1024]);
        INSTANCE.iReadICCardNoAndName(icdev, 0, cardNo, cardName, lpErrMsg);
        StringBuffer result = new StringBuffer();
        result.append(new String(new byte[cardNo.remaining()]));
        result.append(" | ");
        result.append(new String(new byte[cardName.remaining()]));
        return result.toString();
    }
}
