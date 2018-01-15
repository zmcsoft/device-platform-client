package com.zmcsoft.apsp.client.sdk.drivers.monitor;

import lombok.Data;

/**
 * @author zhouhao
 * @since 1.0
 */
@Data
public class MonitorInfo {
    private int state;

    private String message;

    private Object info;
}
