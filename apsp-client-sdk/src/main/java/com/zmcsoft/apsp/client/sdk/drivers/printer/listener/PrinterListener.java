package com.zmcsoft.apsp.client.sdk.drivers.printer.listener;


import java.util.EventListener;
import java.util.EventObject;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PrinterListener<E extends EventObject> extends EventListener {
    void on(E event);
}
