package com.zmcsoft.apsp.client.sdk.drivers.printer.layer;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextLayer extends AbstractLayer {

    private String text;

    @Override
    protected void doDraw(Graphics2D graphics) {
        String text = getText();
        if (text == null) {
            text = "";
        }
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(text, getX(), getY() + graphics.getFontMetrics().getMaxAscent());
    }
}
