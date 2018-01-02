package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.TextLayer;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
public class TextLayerBuilder extends AbstractLayerBuilder {

    public TextLayerBuilder() {
        super("text");
    }

    @Override
    protected Layer doBuild() {
        TextLayer textLayer = new TextLayer();
        textLayer.setText(getString("text", ""));
        textLayer.setX(getInt("x", 0));
        textLayer.setY(getInt("y", 0));
        textLayer.setFont(getFont(null));
        textLayer.setColor(getColor(Color.BLACK));
        return textLayer;
    }
}
