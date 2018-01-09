package com.zmcsoft.apsp.client.core;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
public class DeviceCallResponse {
    public int code;

    public String message;

    public Object result;

    public static DeviceCallResponse success(Object result) {
        return of(1, "请求成功", result);
    }

    public static DeviceCallResponse of(int code, String message, Object result) {
        DeviceCallResponse response = new DeviceCallResponse();
        response.code = code;
        response.message = message;
        response.result = result;
        return response;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public byte[] toBytes() {
        return toString().getBytes();
    }
}
