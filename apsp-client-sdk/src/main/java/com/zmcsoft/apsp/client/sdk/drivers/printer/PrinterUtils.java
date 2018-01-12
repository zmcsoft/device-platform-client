package com.zmcsoft.apsp.client.sdk.drivers.printer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PrinterUtils {
    public static void draw(List<Layer> layers, Graphics2D g2) {
        initGraphics2D(g2);
        layers.forEach(layer -> layer.draw(g2));
    }

    public static void initGraphics2D(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    }

    public static BufferedImage graphicsToImage(List<Pager> pagers, PixelPaper pixelPaper) {
        BufferedImage image = new BufferedImage(pixelPaper.getWidth(), (pixelPaper.getHeight() + 10) * pagers.size(), BufferedImage.TYPE_INT_ARGB);
        int tempY = 0;

        Graphics2D g2 = ((Graphics2D) image.getGraphics());
        initGraphics2D(g2);
        for (Pager pager : pagers) {
            BufferedImage preview = new BufferedImage(pixelPaper.getWidth(), pixelPaper.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = ((Graphics2D) preview.getGraphics());
            initGraphics2D(graphics2D);
            graphics2D.setBackground(Color.white);
            graphics2D.setColor(Color.white);
            graphics2D.fillRect(0, 0, pixelPaper.getWidth(), pixelPaper.getHeight());
            pager.getLayers().forEach(layer -> layer.draw(graphics2D));

            image.getGraphics()
                    .drawImage(preview, 0, tempY, pixelPaper.getWidth(), pixelPaper.getHeight(), (img, infoflags, x, y, width, height) -> false);
            tempY += pixelPaper.getHeight() + 10;
        }
        int degrees = 0;
        // graphics2D.rotate(Math.toDegrees(degrees));
        return image;
    }
}
