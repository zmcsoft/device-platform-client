package com.zmcsoft.apsp.client.sdk.drivers.printer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhouhao
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PixelPaper {
    private int width;

    private int height;

    public PixelPaper(double dpi, MillimeterPaper paper) {
        BigDecimal decimal = BigDecimal.valueOf(dpi)
                .divide(BigDecimal.valueOf(25.4), 5, RoundingMode.CEILING);

        setHeight(decimal.multiply(BigDecimal.valueOf(paper.getHeight())).intValue());

        setWidth(decimal.multiply(BigDecimal.valueOf(paper.getWidth())).intValue());

    }

    public static void main(String[] args) {
        System.out.println(new PixelPaper(300, new MillimeterPaper(210, 297)));

    }
}
