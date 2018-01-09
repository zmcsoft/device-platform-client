package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import io.reactivex.Observable;
import javafx.application.Platform;

import static com.zmcsoft.apsp.client.core.Global.executorService;

/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraJavaScriptOperation extends AbstractJavaScriptObject {
    private CameraDriver cameraDriver;

    public void setCameraDriver(CameraDriver cameraDriver) {
        this.cameraDriver = cameraDriver;
    }

    public CameraJavaScriptOperation(CameraDriver driver) {
        this.cameraDriver = driver;
    }

    public void open(Object callback) {
        execute(cameraDriver::open, callback);
    }

    public void isOpen(Object callback) {
        execute(cameraDriver::isOpen, callback);
    }

    public void getImageBase64(Object callback) {
        execute(cameraDriver::photographBase64, callback);
    }

    public void stop(Object callback) {
        execute(cameraDriver::close, callback);
    }

    public void close(Object callback) {
        stop(callback);
    }

    @Override
    public String getName() {
        return "camera";
    }
}
