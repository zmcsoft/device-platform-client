package com.zmcsoft.apsp.client.sdk.drivers.printer;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGConverter;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.fonts.FontInfo;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.fop.svg.PDFDocumentGraphics2DConfigurator;
import org.apache.fop.svg.PDFGraphics2D;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.xmlgraphics.java2d.GraphicContext;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.batik.transcoder.SVGAbstractTranscoder.KEY_HEIGHT;
import static org.apache.batik.transcoder.SVGAbstractTranscoder.KEY_WIDTH;
import static org.apache.fop.svg.AbstractFOPTranscoder.KEY_AUTO_FONTS;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PrinterUtils {
    public static void draw(List<Layer> layers, Graphics2D g2) {
        initGraphics2D(g2);
        layers.forEach(layer -> layer.draw(g2));
    }

    public static void initGraphics2D(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public static void graphicsToPdf(List<Pager> pagers, PixelPaper pixelPaper, OutputStream outputStream) throws Exception {
        PDFDocumentGraphics2D graphics2D = new PDFDocumentGraphics2D(false);
        GraphicContext context = new GraphicContext();
        graphics2D.setGraphicContext(context);
        initGraphics2D(graphics2D);
        PDFDocumentGraphics2DConfigurator configurator = new PDFDocumentGraphics2DConfigurator();
        Configuration configuration = new DefaultConfiguration("default");
        configurator.configure(graphics2D, configuration, false);
        graphics2D.setupDocument(outputStream, pixelPaper.getWidth(), pixelPaper.getHeight());
        pagers.get(0).setOrientation(2);
        for (Pager pager : pagers) {
            // TODO: 18-3-14 旋转错误,不能只旋转一页
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), pixelPaper.getWidth() / 2D, pixelPaper.getHeight() / 2D);
                graphics2D.setTransform(affineTransform);
            }
            for (Layer layer : pager.getLayers()) {
                layer.draw((Graphics2D) graphics2D.create());
            }
            graphics2D.nextPage(pixelPaper.getWidth(), pixelPaper.getHeight());
        }
        graphics2D.finish();
        graphics2D.dispose();
    }

    public static List<String> graphicsToSvg(List<Pager> pagers) {
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();

        return pagers.stream().map(pager -> {
            String svgNS = "http://www.w3.org/2000/svg";
            Document document = domImpl.createDocument(svgNS, "svg", null);
            SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);
            // TODO: 18-3-14 旋转无效
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), 800 / 2D, 500 / 2D);
                svgGraphics2D.setTransform(affineTransform);
            }
            pager.getLayers().forEach(layer -> layer.draw(svgGraphics2D));
            StringWriter writer = new StringWriter();
            try {
                svgGraphics2D.stream(writer);
            } catch (SVGGraphics2DIOException e) {
                e.printStackTrace();
            }
            return writer.toString();
        }).collect(Collectors.toList());
    }

    public static BufferedImage graphicsToImage(List<Pager> pagers, PixelPaper pixelPaper) {
        BufferedImage image = new BufferedImage(pixelPaper.getWidth(), (pixelPaper.getHeight() + 10) * pagers.size(), BufferedImage.TYPE_INT_ARGB);
        int tempY = 0;

        Graphics2D g2 = ((Graphics2D) image.getGraphics());
        initGraphics2D(g2);
        for (Pager pager : pagers) {
            pager.setOrientation(3);
            BufferedImage preview = new BufferedImage(pixelPaper.getWidth(), pixelPaper.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = ((Graphics2D) preview.getGraphics());
            initGraphics2D(graphics2D);
            // TODO: 18-3-14 优化旋转，当前存在问题: 90度或者270度选择时,位置不对
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), pixelPaper.getWidth() / 2D, pixelPaper.getHeight() / 2D);
                graphics2D.setTransform(affineTransform);
            }

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
