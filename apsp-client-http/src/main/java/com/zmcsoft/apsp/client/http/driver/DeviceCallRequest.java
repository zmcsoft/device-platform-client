package com.zmcsoft.apsp.client.http.driver;

/**
 * @author zhouhao
 * @since 1.0
 */

import lombok.Data;

@Data
public class DeviceCallRequest {
    private String device;

    private String provider;

    private String action;

    private String data;

    private String code;
}
