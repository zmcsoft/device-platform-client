package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import javafx.scene.web.WebEngine;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DeviceDriversWrapper extends AbstractJavaScriptObject {
    public volatile CameraJavaScriptOperation camera;

    public volatile PrinterJavaScriptOperation printer;

    @Override
    public String getName() {
        return "devices";
    }

    @Override
    public void setEngine(WebEngine engine) {
        if (camera == null) {
            camera = new CameraJavaScriptOperation(DeviceDriverManager.drivers().camera());
            printer = new PrinterJavaScriptOperation(null);
        }
        camera.setCameraDriver(DeviceDriverManager.drivers().camera());
        camera.setEngine(engine);
        printer.setEngine(engine);
        super.setEngine(engine);
    }

    private <T extends AbstractJavaScriptObject> T wrap(T target) {
        target.setEngine(engine);
        return target;
    }

    public CameraJavaScriptOperation getCamera(String provider) {
        return wrap(new CameraJavaScriptOperation(DeviceDriverManager.drivers().camera(provider)));
    }

    public PrinterJavaScriptOperation getPrinter(String provider) {
        return wrap(new PrinterJavaScriptOperation(provider));
    }
}
