package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.ImageLayer;
import org.apache.commons.codec.binary.Base64;
import org.hswebframework.expands.request.RequestBuilder;
import org.hswebframework.expands.request.SimpleRequestBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public class ImageLayerBuilder extends AbstractLayerBuilder {
    public ImageLayerBuilder() {
        super("image");
    }

    public static RequestBuilder requestBuilder = new SimpleRequestBuilder();

    @Override
    protected Layer doBuild() {
        ImageLayer layer = new ImageLayer();

        int height = getInt("height", 100);
        int width = getInt("width", 100);

        layer.setHeight(height);
        layer.setWidth(width);

        layer.setImage(createImage(width, height));
        layer.setX(getInt("x", 100));
        layer.setY(getInt("y", 100));

        return layer;
    }

    static BufferedImage createQrCode(int width, int height, String content) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>(6);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    static BufferedImage createBarCode(int width, int height, String content) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>(6);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.CODE_128, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private Image createImage(int width, int height) {
        String type = getString("imageType", "static");
        String imageData = getString("imageData", "");
        try {
            if ("static".equals(type)) {
                if (imageData.startsWith("http")) {
                    try (InputStream inputStream = requestBuilder.http(imageData)
                            .download().response().asStream()) {
                        return ImageIO.read(inputStream);
                    }
                } else if (imageData.startsWith("file")) {
                    try (InputStream inputStream = new URL(imageData).openStream()) {
                        return ImageIO.read(inputStream);
                    }
                } else {
                    try (InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(imageData))) {
                        return ImageIO.read(inputStream);
                    }
                }
            } else if ("qrCode".equals(type)) {
                return createQrCode(width, height, imageData);
            } else if ("barCode".equals(type)) {
                return createBarCode(width, height, imageData);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
