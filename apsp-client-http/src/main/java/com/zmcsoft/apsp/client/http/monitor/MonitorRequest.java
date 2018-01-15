package com.zmcsoft.apsp.client.http.monitor;

import lombok.Data;

/**
 * @author zhouhao
 * @since 1.0
 */
@Data
public class MonitorRequest {
    private String id;

    private String action;

    private String deviceName;

    private int projectId;
}
