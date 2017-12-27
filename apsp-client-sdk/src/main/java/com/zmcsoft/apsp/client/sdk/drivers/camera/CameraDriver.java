package com.zmcsoft.apsp.client.sdk.drivers.camera;

import com.github.sarxos.webcam.Webcam;
import com.zmcsoft.apsp.client.core.Global;
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

/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraDriver {
    private static final Logger log = LoggerFactory.getLogger(CameraDriver.class);

    private int readImageTimeOut = 1;

    private static final byte[] empty = new byte[0];

    private volatile Webcam webcam;

    private volatile boolean open = false;

    private BufferedImage tempImage;

    private List<CameraListener<BufferedImage>> recordListeners = new ArrayList<>();

    public CameraDriver() {
        this(Webcam.getDefault());
        // Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
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

    public boolean isOpen() {
        return webcam.isOpen();
    }

    public CameraDriver(Webcam webcam) {
        this.webcam = webcam;
    }


    public void open(CameraListener<Boolean> openCallback) {
        if (open) {
            openCallback.on(true);
            return;
        }
        this.tempImage = null;
        Global.executorService.execute(() -> {
            try {
                open = webcam.open();
            } finally {
                openCallback.on(open);
            }
        });
    }

    public void stopRecord() {
        recordListeners.clear();
    }

    public void stop() {
        if (!open) {
            return;
        }
        this.tempImage = null;
        open = false;
        recordListeners.clear();
        Global.executorService.execute(() -> {
            webcam.close();
        });
    }

    private void tryStartRecord() {
        if (recordListeners.isEmpty()) {
            return;
        }
        Global.executorService.execute(() -> {
            while (!recordListeners.isEmpty()) {
                try {
                    if ((tempImage = webcam.getImage()) != null) {
                        for (CameraListener<BufferedImage> listener : recordListeners) {
                            listener.on(tempImage);
                        }
                    }
                } catch (Exception e) {
                    log.warn("update camera image error", e);
                }
            }
        });
    }

    public void record(CameraListener<BufferedImage> recordListener) {
        recordListeners.add(recordListener);
        tryStartRecord();
    }

    public byte[] getImageData() {
        if (!open) {
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
