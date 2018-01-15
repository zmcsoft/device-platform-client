package com.zmcsoft.apsp.client.http.monitor;

import com.alibaba.fastjson.JSON;
import com.zmcsoft.apsp.client.sdk.drivers.monitor.MonitorInfo;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitorResponse {
    private String requestId;

    private MonitorInfo result;

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>();
        map.put("parameters", this);
        map.put("command", "device-monitor");
        return JSON.toJSONString(map);
    }
}
