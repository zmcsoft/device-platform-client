package com.zmcsoft.apsp.client.sdk.drivers;

import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrinterDriver;

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
}
