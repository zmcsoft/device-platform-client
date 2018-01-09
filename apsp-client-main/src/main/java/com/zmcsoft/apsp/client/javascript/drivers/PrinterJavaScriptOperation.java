package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.printer.*;
import com.zmcsoft.apsp.client.sdk.drivers.printer.builder.JsonPageBuilder;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class PrinterJavaScriptOperation extends AbstractJavaScriptObject {

    private String printerProvider;

    private JsonPageBuilder builder = new JsonPageBuilder();

    public PrinterJavaScriptOperation(String printerProvider) {
        if (null != printerProvider && !printerProvider.isEmpty()) {
            this.printerProvider = printerProvider;
        } else {
            this.printerProvider = ApplicationConfig.getConfig("printer", DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
        }
    }

    @Override
    public String getName() {
        return "printer";
    }

    public PrinterDriver getDriver() {
        return DeviceDriverManager.drivers()
                .printer(printerProvider);
    }

    public void preview(String json, Object callback) {
        Platform.runLater(() -> {
            List<Pager> pager = builder.build(json);
            BufferedImage image = PrinterUtils.graphicsToImage(pager, new PixelPaper(72, Paper.A4));
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(image, "png", outputStream);
                call(callback, Base64.encodeBase64String(outputStream.toByteArray()));
            } catch (IOException e) {
                log.error("创建预览图失败", e);
            }
        });
    }

    public void print(String json, Object callback) {
        Global.executorService.execute(() -> {
            Platform.runLater(() -> {
                try {
                    List<Pager> pagers = builder.build(json);
                    PrintJob job = getDriver().print(pagers);
                    job.onPrintDone((index, pager) -> {
                        Platform.runLater(() -> {
                            call(callback, index);
                        });
                    });
                } catch (Exception e) {
                    log.error("打印失败", e);
                }
            });
        });
    }

}
