package com.zmcsoft.apsp.client.sdk.drivers;

import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.iccard.ICCradDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard.IdentityDriver;
import com.zmcsoft.apsp.client.sdk.drivers.ms.MSIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.contact.ContactIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.device.MultipleDeviceDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.notcontact.NotContactIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrinterDriver;
import com.zmcsoft.apsp.client.sdk.drivers.softkeyboard.SoftKeyBoardDriver;
import com.zmcsoft.apsp.client.sdk.drivers.voice.VoiceDriver;

/**
 * 设备驱动
 *
 * @author zhouhao
 * @since 1.0
 */
public interface DeviceDrivers {
    /**
     * @return 默认的摄像头驱动
     */
    CameraDriver camera();

    /**
     * @return 默认的打印机驱动
     */
    PrinterDriver printer();

    /**
     * @return 默认的语音播报驱动
     */
    VoiceDriver voice();

    /**
     * @return 默认的软键盘驱动
     */
    SoftKeyBoardDriver softKeyBoard();

    /**
     * 获取指定的摄像头驱动
     *
     * @param provider 摄像头标识
     * @return 摄像头驱动
     */
    CameraDriver camera(String provider);

    /**
     * 获取指定的打印机驱动
     *
     * @param provider 打印机标识
     * @return 打印机驱动
     */
    PrinterDriver printer(String provider);

    /**
     * 获取指定的软件盘机驱动
     *
     * @param provider 软件盘标识
     * @return 软件盘驱动
     */
    SoftKeyBoardDriver softKeyBoard(String provider);

    /**
     * 获取指定的语音播报驱动
     *
     * @param provider 语音播报标识
     * @return 语音播报驱动
     */
    VoiceDriver voice(String provider);


    /**
     * @return 默认的身份证读取驱动
     */
    IdentityDriver identity();

    /**
     * 获取指定的身份证读取驱动
     *
     * @param provider 身份证读取标识
     * @return 身份证读取驱动
     */
    IdentityDriver identity(String provider);

    /**
     * @return 默认的磁条卡驱动
     */
    MSIcDriver mSIcCard();

    /**
     * 获取指定的磁条卡驱动
     *
     * @param provider 磁条卡标识
     * @return 磁条卡驱动
     */
    MSIcDriver mSIcCard(String provider);

    /**
     * @return 默认的多合一设备驱动
     */
    MultipleDeviceDriver multipleDeviceDriver();

    /**
     * 获取指定的多合一设备驱动
     *
     * @param provider 多合一设备标识
     * @return 多合一设备驱动
     */
    MultipleDeviceDriver multipleDeviceDriver(String provider);

    /**
     * @return 默认的接触卡驱动
     */
    ContactIcDriver contactIcDriver();

    /**
     * 获取指定的接触卡驱动
     *
     * @param provider 接触卡标识
     * @return 接触卡驱动
     */
    ContactIcDriver contactIcDriver(String provider);

    /**
     * @return 默认的非接触卡驱动
     */
    NotContactIcDriver notContactIcDriver();

    /**
     * 获取指定的非接触卡驱动
     *
     * @param provider 非接触卡标识
     * @return 非接触卡驱动
     */
    NotContactIcDriver notContactIcDriver(String provider);

    /**
     * @return 默认的IC卡驱动
     */
    ICCradDriver iCCradDriver();

    /**
     * 获取指定的IC卡驱动
     *
     * @param provider IC卡标识
     * @return IC驱动
     */
    ICCradDriver iCCradDriver(String provider);

}
