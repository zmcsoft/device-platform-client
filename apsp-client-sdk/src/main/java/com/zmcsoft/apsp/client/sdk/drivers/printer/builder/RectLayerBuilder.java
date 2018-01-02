package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.RectLayer;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
public class RectLayerBuilder extends AbstractLayerBuilder {
    public RectLayerBuilder() {
        super("rect");
    }

    @Override
    protected Layer doBuild() {
        RectLayer layer = new RectLayer();
        layer.setHeight(getInt("width", 100));
        layer.setWidth(getInt("height", 100));
        layer.setY(getInt("y", 0));
        layer.setX(getInt("x", 0));
        layer.setColor(getColor(Color.BLACK));
        return layer;
    }
}
