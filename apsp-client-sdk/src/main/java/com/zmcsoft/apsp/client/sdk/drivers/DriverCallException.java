package com.zmcsoft.apsp.client.sdk.drivers;

public class DriverCallException extends Exception {

    public DriverCallException(){
        super();
    }
    /**
     * 构建基本异常
     * @param message 错误消息
     */
    public DriverCallException(String message){
        super(message);
    }

}
