package com.zmcsoft.apsp.client.sdk.drivers.printer.layer;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
public abstract class AbstractLayer implements Layer {
    private Color color = Color.BLACK;

    private Font font;

    private int x;

    private int y;

    protected abstract void doDraw(Graphics2D graphics);

    @Override
    public void draw(Graphics2D graphics) {
        Color oldColor = graphics.getColor();
        Font oldFont = graphics.getFont();
        graphics.setColor(getColor());
        if (font != null) {
            graphics.setFont(font);
        }
        doDraw(graphics);
        graphics.setColor(oldColor);
        graphics.setFont(oldFont);
    }
}
