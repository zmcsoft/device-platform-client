package com.zmcsoft.apsp.client.sdk.drivers.multiple.device;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;
import com.zmcsoft.apsp.client.sdk.drivers.DriverCallException;

public interface MultipleDeviceDriver extends DeviceDriver {
    @Override
    default String getName() {
        return "multiple-device";
    }

    /**
     * 打开设备
     *
     * @param port 串口号,0 对应 COM1,1 对应 COM2……，取值范围 0~31
     * @param baud (入)通讯波特率，9600bps（缺省设置）19200bps，38400bps，57600bps，115200bps
     * @return 成功返回true
     */
    boolean deviceOpen(int port, long baud);

    /**
     * 关闭设备
     *
     * @return 成功返回true
     */
    boolean deviceClose() throws DriverCallException;


    /**
     * 设备led灯控制
     *
     * @param ledctrl 开关标识 0 关 1 开
     * @return
     */
    boolean deviceLedctrl(int ledctrl);

    /**
     * 获取设备状态，判断设备是否正常
     *
     * @param ndev_status 0 正常
     *                    1 接触卡通道异常
     *                    2 非接卡通道异常
     *                    3 接触卡和非接卡通道异常
     * @return 返回判断结果
     */
    boolean getDeviceStatus(int ndev_status);

    /**
     * 设备复位
     *
     * @return
     */
    boolean deviceReset();

    /**
     * 获取设备与pc机的通讯方式
     *
     * @return
     */
    String getDeviceType();

    /**
     * 设置设备波特率
     *
     * @param module 功能模块 1 字节 0：接触式, 1：非接触式
     * @param baud   串口波特率 9600bps（缺省设置）
     *               19200bps
     *               38400bps
     *               57600bps
     *               115200bps
     * @return
     */
    boolean setDeviceBaud(int module, long baud);

}
