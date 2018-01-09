package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import javafx.application.Platform;

/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraJavaScriptOperation extends AbstractJavaScriptObject {
    private CameraDriver cameraDriver;

    public CameraJavaScriptOperation(CameraDriver driver) {
        this.cameraDriver = driver;
    }

    public void open(Object callback) {
        Global.executorService.execute(() -> Platform.runLater(() -> call(callback, cameraDriver.open())));
    }

    public void isOpen(Object callback) {
        call(callback, cameraDriver.isOpen());
    }

    public void getImageBase64(Object callback) {
        call(callback, cameraDriver.photographBase64());
    }

    public void stop(Object callback) {
        call(callback, cameraDriver.close());
    }

    public void close(Object callback) {
        stop(callback);
    }

    public void stopRecord(Object callback) {
        cameraDriver.stopRecord();
        call(callback, true);
    }

    @Override
    public String getName() {
        return "camera";
    }
}
