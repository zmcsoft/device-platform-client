package com.zmcsoft.apsp.client.sdk.drivers.printer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGConverter;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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

    public static void svg2pdf(String svg, OutputStream out) throws TranscoderException {
        TranscoderInput input = new TranscoderInput(new StringReader(svg));
        TranscoderOutput output = new TranscoderOutput(out);
        PDFTranscoder transcoder = new PDFTranscoder();
        transcoder.addTranscodingHint(KEY_WIDTH, 800f);
        transcoder.addTranscodingHint(KEY_HEIGHT, 1200f);
        transcoder.addTranscodingHint(KEY_AUTO_FONTS, true);
        final int dpi = 300;
        transcoder.addTranscodingHint(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, 25.4f / dpi);
        transcoder.addTranscodingHint(XMLAbstractTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
        transcoder.addTranscodingHint(PDFTranscoder.KEY_STROKE_TEXT, Boolean.FALSE);
        transcoder.transcode(input, output);
    }

    public static List<String> graphicsToSvg(List<Pager> pagers) {
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();
        return pagers.stream().map(pager -> {
            // Create an instance of org.w3c.dom.Document.
            String svgNS = "http://www.w3.org/2000/svg";
            Document document = domImpl.createDocument(svgNS, "svg", null);
            document.createAttribute("style").setValue("width:220mm;height:220mm");

            SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);
            initGraphics2D(svgGraphics2D);
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
