package com.zmcsoft.apsp.client.http.monitor;

import com.alibaba.fastjson.JSON;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.sdk.drivers.monitor.MonitorInfo;
import com.zmcsoft.apsp.client.sdk.drivers.monitor.MonitorProject;
import com.zmcsoft.apsp.client.sdk.drivers.monitor.MonitoringDeviceDriver;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class WebsocketDeviceMonitor extends WebSocketClient {

    private static boolean running = false;

    private final List<MonitoringDeviceDriver> deviceDrivers = new ArrayList<>();

    public static void startMonitor(String serverUrl) {
        try {
            running = true;
            new WebsocketDeviceMonitor(new URI(serverUrl)).connect();

        } catch (URISyntaxException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private void doMonitor() {
        synchronized (deviceDrivers) {
            for (MonitoringDeviceDriver deviceDriver : deviceDrivers) {

            }
        }
    }

    private WebsocketDeviceMonitor(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("设备监控连接状态:{} {}", serverHandshake.getHttpStatus(), serverHandshake.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String s) {
        MonitorRequest request = JSON.parseObject(s, MonitorRequest.class);
        switch (request.getAction()) {
            case "getAllDevice": {
                MonitorInfo info = new MonitorInfo();
                info.setState(1);
                info.setMessage("成功");
                info.setInfo(deviceDrivers.stream().map(driver -> {
                    Map<String, List<MonitorProject>> v = new HashMap<>();
                    v.put(driver.getName(), driver.getAllSupportProject());
                    return v;
                }).collect(Collectors.toList()));
                send(new MonitorResponse(request.getId(), info).toString());
                break;
            }
            default:
                return;
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
