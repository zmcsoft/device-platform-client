package com.zmcsoft.apsp.client.javascript.drivers;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.commons.codec.binary.Base64;

import java.util.List;


/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraJavaScriptOperation extends AbstractJavaScriptObject {
    private CameraDriver cameraDriver;
    private Pane         pane;
    private ImageView    view;

    public CameraJavaScriptOperation(Pane pane) {
        this.pane = pane;
        this.view = new ImageView();
        cameraDriver = new CameraDriver(getCamera());
        Webcam.addDiscoveryListener(new WebcamDiscoveryListener() {
            @Override
            public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {
                if (cameraDriver.getWebcam() != webcamDiscoveryEvent.getWebcam()) {
                    stop();
                    cameraDriver.setWebcam(webcamDiscoveryEvent.getWebcam());
                }
            }

            @Override
            public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {
                if (cameraDriver.getWebcam() == webcamDiscoveryEvent.getWebcam()) {
                    stop();
                    Webcam webcam = getCamera();
                    if (null != webcam) {
                        cameraDriver.setWebcam(webcam);
                    }
                }
            }
        });
    }

    private Webcam getCamera() {

        String name = ApplicationConfig.getConfig("camera.name", null);
        List<Webcam> webcams = Webcam.getWebcams();
        if (webcams.isEmpty()) {
            return Webcam.getDefault();
        }
        if (webcams.size() == 1) {
            return webcams.get(0);
        }
        if (name != null) {
            return Webcam.getWebcamByName(name);
        }
        return webcams.get(webcams.size() - 1);
    }

    public void open(Object callback) {
        cameraDriver.open(success ->
                Platform.runLater(() -> call(callback, success)));
    }

    public boolean isOpen() {
        return cameraDriver.isOpen();
    }

    public String getImageBase64() {
        return Base64.encodeBase64String(cameraDriver.getImageData());
    }

    public void stop() {
        pane.getChildren().remove(view);
        cameraDriver.stop();
    }

    public void close() {
        stop();
    }

    public void stopRecord() {
        pane.getChildren().remove(view);
        cameraDriver.stopRecord();
    }

    public void openRecord(double x, double y, double width, double height) {
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.relocate(x, y);
        cameraDriver.open(success -> {
            cameraDriver.record(image -> {
                Platform.runLater(() -> {
                    if (null != image) {
                        view.imageProperty().set(SwingFXUtils.toFXImage(image, null));
                        image.flush();
                    }
                });
            });
        });
        pane.getChildren().add(view);
    }

    @Override
    public String getName() {
        return "camera";
    }
}
