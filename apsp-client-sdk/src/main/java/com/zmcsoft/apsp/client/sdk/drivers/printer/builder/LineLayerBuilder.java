package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.LineLayer;

import java.awt.*;


/**
 * @author zhouhao
 * @since 1.0
 */
public class LineLayerBuilder extends AbstractLayerBuilder {
    public LineLayerBuilder() {
        super("line");
    }
    @Override
    protected Layer doBuild() {
        LineLayer lineLayer = new LineLayer();

        lineLayer.setX(getInt("x1", 0));
        lineLayer.setY(getInt("y1", 0));
        lineLayer.setEndX(getInt("x2", 0));
        lineLayer.setEndY(getInt("y2", 0));

        lineLayer.setColor(getColor(Color.BLACK));
        lineLayer.setFont(getFont(null));
        return lineLayer;
    }
}
