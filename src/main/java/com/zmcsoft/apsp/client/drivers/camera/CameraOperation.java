package com.zmcsoft.apsp.client.drivers.camera;

import com.github.sarxos.webcam.Webcam;
import com.zmcsoft.apsp.client.Global;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class CameraOperation {
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private ImageView view = new ImageView();

    private Thread thread;

    private Webcam webcam;

    private boolean stopCamera = false;

    private BufferedImage image;

    private Pane pane;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public CameraOperation(Pane pane) {
        this.pane = pane;
        view.imageProperty().bindBidirectional(imageProperty);
        Global.executorService.execute(() -> webcam = Webcam.getDefault());
    }

    public boolean open() {
        if (webcam.isOpen()) return false;
        stopCamera = false;
        readWriteLock.writeLock().lock();
        try {
             Global.executorService.execute(() -> {
                log.info("open camera");
                boolean success = webcam.open();

                log.info("open camera {}", success);
               // return success;
            });//.get(1, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return false;

    }

    public void show(double x, double y, double width, double height) {
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
        open();
        startRecord();
        pane.getChildren().add(view);
    }

    public boolean isOpen() {
        return webcam.isOpen();
    }

    public void close() {
        if (!webcam.isOpen()) return;
        stopCamera = true;
        this.image = null;

        Global.executorService.execute(() -> {
            readWriteLock.writeLock().lock();
            webcam.close();
            readWriteLock.writeLock().unlock();
        });

        pane.getChildren().remove(view);

    }

    public void startRecord() {
        Global.executorService.execute(() -> {
            while (!stopCamera) {
                try {
                    readWriteLock.readLock().lock();
                    if ((image = webcam.getImage()) != null) {
                        Platform.runLater(() -> {
                            Image mainiamge = SwingFXUtils.toFXImage(image, null);
                            imageProperty.set(mainiamge);
                        });
                        image.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        readWriteLock.readLock().unlock();
                    } catch (Exception e) {
                    }

                }
            }
        });
    }

    public String writeFile() {
        if (stopCamera) {
            return null;
        }
        String path = "./data/imags/camera";
        new File(path).mkdirs();
        String file = path + "/" + System.currentTimeMillis() + ".jpg";
        try (OutputStream outputStream = new FileOutputStream(file)) {
            ImageIO.write(image, "jpg", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new File(file).toURI().toString();
    }

    public byte[] getImageData() {
        if (stopCamera) {
            return null;
        }
        try {
            System.out.println(((ThreadPoolExecutor) Global.executorService).getActiveCount());
          //  readWriteLock.writeLock().lock();
            return Global.executorService.submit(() -> {
                BufferedImage image = this.image == null ? webcam.getImage() : this.image;
                if (null == image) {
                    return null;
                }
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(image, "png", outputStream);
                    return outputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
        } finally {
          //  readWriteLock.writeLock().unlock();
        }

        return null;
    }

    public String getImageBase64() {
        if (stopCamera) {
            return null;
        }
        return Base64.encodeBase64String(getImageData());
    }

}
