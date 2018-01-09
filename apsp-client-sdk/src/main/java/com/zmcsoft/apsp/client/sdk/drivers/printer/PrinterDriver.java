package com.zmcsoft.apsp.client.sdk.drivers.printer;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PrinterDriver extends DeviceDriver {
    PrintJob print(List<Pager> pagers);

    PrintState getState();
}
