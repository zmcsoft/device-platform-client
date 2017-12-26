package com.zmcsoft.apsp.client.drivers.camera;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * @author zhouhao
 * @since 1.0
 */
public class CameraOperation {
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private ImageView             view          = new ImageView();

    private Thread thread;

    private Webcam webcam = Webcam.getDefault();

    private boolean stopCamera = false;

    private BufferedImage image;

    private Pane pane;


    public CameraOperation(Pane pane) {
        this.pane = pane;
        view.imageProperty().bindBidirectional(imageProperty);
    }

    public void open() {
        webcam.open();
        stopCamera = false;
        pane.getChildren().add(view);
    }

    public void show(int x, int y, double width, double height) {
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
        open();
        startRecord();
    }

    public void close() {
        pane.getChildren().removeAll(view);
        stopCamera = true;
        webcam.close();
    }

    public void startRecord() {
        thread = new Thread(() -> {
            while (!stopCamera) {
                try {
                    if ((image = webcam.getImage()) != null) {
                        Platform.runLater(() -> {
                            Image mainiamge = SwingFXUtils.toFXImage(image, null);
                            imageProperty.set(mainiamge);
                        });
                        image.flush();
                    }
                } catch (Exception e) {
                }
            }
        });
        Platform.runLater(thread::start);
    }

    public String writeFile() {
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
        BufferedImage image = webcam.getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public String getImageBase64() {
        return Base64.encodeBase64String(getImageData());
    }

}
