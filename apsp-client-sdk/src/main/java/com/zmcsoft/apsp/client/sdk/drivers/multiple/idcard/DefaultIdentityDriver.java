package com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard;

import com.sun.jna.NativeLong;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DriverCallException;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.IDCardEntity;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zmcsoft.apsp.client.sdk.drivers.multiple.Mtx_32Library.INSTANCE;
import static com.zmcsoft.apsp.client.sdk.drivers.multiple.device.DefaultMultipleDeviceDriver.icdev;

public class DefaultIdentityDriver implements IdentityDriver {

    //路径深度大于3，读取照片的函数读（基本）不出来
    public static String savePhotoPath = "d:\\photo\\";


    public static String savePhotoName = "temp";

    static {
        DeviceDriverManager.register(register -> {
            register.register(IdentityDriver.class, new DefaultIdentityDriver(), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        });
    }
    @Override
    public IDCardEntity getIDCradInfo() throws Exception{
        //句柄为null 先打开设备获取句柄
        if(icdev == null) icdev = INSTANCE.device_open((short)0, new NativeLong(9600));

        INSTANCE.IDCard_SetPhotoName(ByteBuffer.wrap(savePhotoName.getBytes()));
        INSTANCE.IDCard_SetPhotoPath(ByteBuffer.wrap(savePhotoPath.getBytes()));
        byte Info_Name[]=new byte[40];
        byte Info_Gender[]=new byte[3];
        byte Info_Nation[]=new byte[18];
        byte Info_Birth[]=new byte[16];
        byte Info_Address[]=new byte[70];
        byte Info_IDNumber[]=new byte[36];
        byte Info_Issued[]=new byte[30];
        byte Info_DateBegin[]=new byte[16];
        byte Info_DateEnd[]=new byte[16];
        byte Info_Reserved[]=new byte[36];
        byte Info_PhotoName[]=new byte[50];
        byte cPhotoBase64[]=new byte[65530];
        int iPhotoBase64Len = 0;
        byte resp[]=new byte[36];
        ByteBuffer msg = ByteBuffer.allocate(128);
        INSTANCE.IDCard_ReadCard_Ex(icdev,
                ByteBuffer.wrap(Info_Name),ByteBuffer.wrap(Info_Gender),
                ByteBuffer.wrap(Info_Nation),ByteBuffer.wrap(Info_Birth),
                ByteBuffer.wrap(Info_Address),ByteBuffer.wrap(Info_IDNumber),
                ByteBuffer.wrap(Info_Issued),ByteBuffer.wrap(Info_DateBegin),
                ByteBuffer.wrap(Info_DateEnd),ByteBuffer.wrap(Info_Reserved),1,
                ByteBuffer.wrap(cPhotoBase64),
                IntBuffer.allocate(4),msg
        );
        deviceClose();
        return IDCardEntity.builder()
                .name(new String(Info_Name, "gbk").trim())
                .gender(new String(Info_Gender, "gbk").trim())
                .notion(new String(Info_Nation, "gbk").trim())
                .birthday(new String(Info_Birth, "gbk").trim())
                .address(new String(Info_Address, "gbk").trim())
                .idNumber(new String(Info_IDNumber, "gbk").trim())
                .issueDepartment(new String(Info_Issued, "gbk").trim())
                .validFormDate(new String(Info_DateBegin, "gbk").trim())
                .validExpiryDate(new String(Info_DateEnd, "gbk").trim())
                .portraitBase64(new String(cPhotoBase64, "gbk").trim())
                .build();
    }


    private boolean deviceClose() throws DriverCallException {
        boolean result = INSTANCE.device_close(icdev) == 0;
        if(result) icdev = null;
        else throw new DriverCallException("关闭设备失败");
        return  result;
    }

  /*  @Override
    public IDCardEntity getIdCardByIndex(int index) throws IOException{
        ByteBuffer read = ByteBuffer.allocate(16);
        INSTANCE.IDCard_ReadCard(icdev,read);
        ByteBuffer byteBuffer =  ByteBuffer.allocate(32);
        INSTANCE.IDCard_GetCardInfo(icdev,index,byteBuffer);
        return new String(byteBuffer.array(),"gbk").trim();
    }*/

 /*   @Override
    public boolean idCradIfOverdue() throws IOException{
        ByteBuffer read = ByteBuffer.allocate(16);
        INSTANCE.IDCard_ReadCard(icdev,read);
        ByteBuffer byteBuffer =  ByteBuffer.allocate(32);
        INSTANCE.IDCard_GetCardInfo(icdev,8,byteBuffer);

        String lastDate = new String(byteBuffer.array(),"gbk").trim();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return df.parse(lastDate).getTime() <= (calendar.getTime().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
*/
   /* @Override
    public boolean deleteAllPhotofile() {
        return INSTANCE.delete_all_photofile() == 0;
    }*/
}
