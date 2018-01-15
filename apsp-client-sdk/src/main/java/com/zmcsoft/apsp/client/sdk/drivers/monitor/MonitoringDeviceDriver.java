package com.zmcsoft.apsp.client.sdk.drivers.monitor;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

import java.util.List;

/**
 * 可进行监控的设备驱动
 *
 * @author zhouhao
 * @since 1.0
 */
public interface MonitoringDeviceDriver extends DeviceDriver {

    List<MonitorProject> getAllSupportProject();

    MonitorInfo getInfo(int monitorProjectId);
}
