package com.zmcsoft.apsp.client.sdk.drivers.printer;


import lombok.Data;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0
 */
@Data
public class PrintCommand {
    private String title;

    private List<Pager> pagers;

    private PixelPaper paper;

}
