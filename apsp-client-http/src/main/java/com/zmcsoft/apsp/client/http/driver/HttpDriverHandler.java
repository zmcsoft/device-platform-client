package com.zmcsoft.apsp.client.http.driver;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.camera.CameraCallable;
import com.zmcsoft.apsp.client.http.driver.printer.PrinterCallable;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public class HttpDriverHandler implements HttpHandler {

    private static final Map<String, DeviceCallable> callableStorage = new HashMap<>();

    static {
        callableStorage.put("printer", new PrinterCallable());
        callableStorage.put("camera", new CameraCallable());
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
}
