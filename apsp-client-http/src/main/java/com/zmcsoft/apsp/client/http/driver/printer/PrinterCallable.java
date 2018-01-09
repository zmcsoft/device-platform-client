package com.zmcsoft.apsp.client.http.driver.printer;

import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.DeviceCallResponse;
import com.zmcsoft.apsp.client.http.driver.DeviceCallable;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.printer.*;
import com.zmcsoft.apsp.client.sdk.drivers.printer.builder.JsonPageBuilder;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PrinterCallable implements DeviceCallable {

    JsonPageBuilder pageBuilder = new JsonPageBuilder();

    @Override
    public DeviceCallResponse call(String provider, String action, String data) {
        List<Pager> pagers = pageBuilder.build(data);
        switch (action) {
            case "preview":
                BufferedImage image = PrinterUtils.graphicsToImage(pagers, new PixelPaper(72, Paper.A4));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(image, "png", stream);
                } catch (IOException e) {
                    return DeviceCallResponse.of(500, "创建预览失败", e.getMessage());
                }
                return DeviceCallResponse.success(Base64.encodeBase64String(stream.toByteArray()));
            case "print":
                print(provider, pagers);
                return DeviceCallResponse.success(true);
            default:
                return DeviceCallResponse.of(404, "未知操作", null);
        }
    }

    void print(String provider, List<Pager> pagers) {
        PrinterDriver driver = DeviceDriverManager.drivers()
                .printer(Optional.ofNullable(provider).orElseGet(() -> ApplicationConfig.getConfig("printer", DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER)));
        CountDownLatch latch = new CountDownLatch(pagers.size());

        driver.print(pagers)
                .onPrintDone((index, pages) -> latch.countDown());
        try {
            latch.await(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
