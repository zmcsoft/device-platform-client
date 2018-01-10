package com.zmcsoft.apsp.client.http.driver;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.camera.CameraCallable;
import com.zmcsoft.apsp.client.http.driver.printer.PrinterCallable;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class HttpDriverHandler extends WebSocketServer implements HttpHandler {

    private static final Map<String, DeviceCallable> callableStorage = new HashMap<>();

    static {
        callableStorage.put("printer", new PrinterCallable());
        callableStorage.put("camera", new CameraCallable());
    }

    public HttpDriverHandler(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        String method = exchange.getRequestMethod();
        exchange.sendResponseHeaders(200, 0);
        if (!method.equals("POST")) {
            exchange.getResponseBody().close();
            return;
        }
        try (InputStream inputStream = exchange.getRequestBody();
             OutputStream responseBody = exchange.getResponseBody();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int i = -1;
            while ((i = inputStream.read()) != -1) {
                baos.write(i);
            }
            byte[] data = baos.toByteArray();
            DeviceCallRequest request = JSON.parseObject(data, DeviceCallRequest.class);
            DeviceCallable callable = callableStorage.get(request.getDevice());

            if (null != callable) {
                DeviceCallResponse result = callable.call(request.getProvider(), request.getAction(), request.getData());
                responseBody.write(result.toBytes());
            } else {
                responseBody.write(DeviceCallResponse.of(-1, "不支持的设备", null).toBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String callDevice(String json) {
        log.info("调用设备请求:{}", json);
        DeviceCallResponse response;
        try {
            DeviceCallRequest request = JSON.parseObject(json, DeviceCallRequest.class);

            DeviceCallable callable = callableStorage.get(request.getDevice());
            if (null != callable) {
                response = callable.call(request.getProvider(), request.getAction(), request.getData());
            } else {
                response = DeviceCallResponse.of(-1, "不支持的设备", null);
            }
            response.code = request.getCode();
        } catch (Exception e) {
            response = DeviceCallResponse.of(-2, "请求失败", e.getMessage());
        }
        log.info("调用设备请求完成:{}", response);
        return response.toString();
    }

    String response(int code, String message) {
        return response(code, message, null);
    }

    String response(int code, String message, Object result) {
        Map<String, Object> response = new HashMap<>(2);
        response.put("code", code);
        response.put("message", message);
        response.put("result", result);
        return JSON.toJSONString(response);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        webSocket.send(callDevice(s));
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        log.error(e.getMessage(), e);
    }
}
