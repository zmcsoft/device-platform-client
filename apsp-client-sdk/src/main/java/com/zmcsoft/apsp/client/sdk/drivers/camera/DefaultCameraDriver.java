package com.zmcsoft.apsp.client.sdk.drivers.camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DefaultCameraDriver implements CameraDriver {
    private static final Logger log = LoggerFactory.getLogger(DefaultCameraDriver.class);

    private int readImageTimeOut = 1;

    private static final byte[] empty = new byte[0];

    private volatile Webcam webcam;

    private BufferedImage tempImage;

    private List<Consumer<BufferedImage>> recordListeners = new ArrayList<>();

    static {
        DeviceDriverManager.register(register -> {
            register.register(CameraDriver.class, new DefaultCameraDriver(Webcam.getDefault()), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
            for (Webcam webcam : Webcam.getWebcams()) {
                register.register(CameraDriver.class, new DefaultCameraDriver(webcam), webcam.getName());
            }
            Webcam.addDiscoveryListener(new WebcamDiscoveryListener() {
                @Override
                public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {
                    register.register(CameraDriver.class, new DefaultCameraDriver(Webcam.getDefault()), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
                    register.register(CameraDriver.class, new DefaultCameraDriver(webcamDiscoveryEvent.getWebcam()), webcamDiscoveryEvent.getWebcam().getName());
                }

                @Override
                public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {

                }
            });
        });
    }

    public DefaultCameraDriver() {
        this(Webcam.getDefault());
    }


    public void setWebcam(Webcam webcam) {
        if (webcam != this.webcam) {
            stop();
        }
        this.webcam = webcam;
    }

    public Webcam getWebcam() {
        return webcam;
    }

    @Override
    public boolean isOpen() {
        return webcam.isOpen();
    }

    public DefaultCameraDriver(Webcam webcam) {
        this.webcam = webcam;
    }

    @Override
    public boolean open() {
        if (isOpen()) {
            return true;
        }
        return webcam.open();
    }

    @Override
    public boolean close() {
        return stop();
    }

    @Override
    public void record(Consumer<BufferedImage> recordListener) {
        recordListeners.add(recordListener);
        tryStartRecord();
    }

    @Override
    public BufferedImage photographImage() {
        return webcam.getImage();
    }

    @Override
    public String photographBase64() {
        return Base64.encodeBase64String(photographBytes());
    }

    @Override
    public byte[] photographBytes() {
        return getImageData();
    }

    @Override
    public void stopRecord() {
        recordListeners.clear();
    }

    public boolean stop() {
        if (!isOpen()) {
            return true;
        }
        this.tempImage = null;
        recordListeners.clear();
        return webcam.close();
    }

    private void tryStartRecord() {
        if (recordListeners.isEmpty()) {
            return;
        }
        Global.executorService.execute(() -> {
            while (!recordListeners.isEmpty()) {
                try {
                    if ((tempImage = webcam.getImage()) != null) {
                        for (Consumer<BufferedImage> listener : recordListeners) {
                            listener.accept(tempImage);
                        }
                    }
                } catch (Exception e) {
                    log.warn("update camera image error", e);
                }
            }
        });
    }

    public byte[] getImageData() {
        if (!isOpen()) {
            return empty;
        }
        try {
            return Global.executorService.submit(() -> {
                BufferedImage image = this.tempImage == null ? webcam.getImage() : this.tempImage;
                if (null == image) {
                    return empty;
                }
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(image, "png", outputStream);
                    return outputStream.toByteArray();
                } catch (IOException e) {
                    log.warn("get camera image error!", e);
                }finally {
                    image.flush();
                }
                return empty;
            }).get(readImageTimeOut, TimeUnit.SECONDS);
        } catch (TimeoutException ignore) {
        } catch (Exception e) {
            log.warn("get camera image error!", e);
        }
        return empty;
    }

    public void setReadImageTimeOut(int readImageTimeOut) {
        this.readImageTimeOut = readImageTimeOut;
    }
}
