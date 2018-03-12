package com.zmcsoft.apsp.client.sdk.drivers.multiple.iccard;

import com.sun.jna.NativeLong;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DriverCallException;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.ICCardEntity;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.zmcsoft.apsp.client.sdk.drivers.multiple.Mtx_32Library.INSTANCE;
import static com.zmcsoft.apsp.client.sdk.drivers.multiple.device.DefaultMultipleDeviceDriver.icdev;

public class DefaultICCradDriver implements ICCradDriver {
    static {
        DeviceDriverManager.register(register -> {
            register.register(ICCradDriver.class, new DefaultICCradDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
    @Override
    public ICCardEntity getICCardInfo() throws Exception{
        if(icdev == null) icdev = INSTANCE.device_open((short)0, new NativeLong(9600));
        ByteBuffer cardNo = ByteBuffer.allocate(20);
        ByteBuffer cardName = ByteBuffer.allocate(16);
        ByteBuffer lpErrMsg = ByteBuffer.allocate(128);
        INSTANCE.iReadICCardNoAndName(icdev, 0, cardNo, cardName, lpErrMsg);
        deviceClose();
        return ICCardEntity.builder()
                .cardNo(new String(cardNo.array(),"gbk").trim())
                .cardName(new String(cardName.array(),"gbk").trim())
                .build();
    }
    @Override
    public ICCardEntity getICCardInfoAnd55(String AIDList, String aryInput) throws Exception{
        //单独获取55域数据不行，得先读取ic卡基本信息
        if(icdev == null) icdev = INSTANCE.device_open((short)0, new NativeLong(9600));
        ByteBuffer cardNo = ByteBuffer.allocate(20);
        ByteBuffer cardName = ByteBuffer.allocate(16);
        ByteBuffer lpErrMsg = ByteBuffer.allocate(128);
        INSTANCE.iReadICCardNoAndName(icdev, 0, cardNo, cardName, lpErrMsg);
        ByteBuffer serNo = ByteBuffer.allocate(100);
        ByteBuffer sValue = ByteBuffer.allocate(512);
        IntBuffer len = IntBuffer.allocate(100);
        int re = INSTANCE.GenF55(icdev,ByteBuffer.wrap(aryInput.getBytes()),ByteBuffer.wrap(AIDList.getBytes()),serNo,sValue,len);
        //System.out.println("55域数据长度"+len.array()[0]);
        System.out.println(len.arrayOffset());
        deviceClose();
        return ICCardEntity.builder()
                .cardNo(new String(cardNo.array(),"gbk").trim())
                .cardName(new String(cardName.array(),"gbk").trim())
                .serNo(new String(serNo.array(),"gbk").trim())
                .gef55(new String(sValue.array(),"gbk").trim())
                .build();
    }









    private boolean deviceClose() throws DriverCallException {
        boolean result = INSTANCE.device_close(icdev) == 0;
        if(result) icdev = null;
        else throw new DriverCallException("关闭设备失败");
        return  result;
    }
}
