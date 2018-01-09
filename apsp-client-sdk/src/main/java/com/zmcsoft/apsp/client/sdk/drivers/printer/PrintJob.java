package com.zmcsoft.apsp.client.sdk.drivers.printer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PrintJob {
    String getPrintJobId();

    void onPrintDone(BiConsumer<Integer, Pager> onPagerPrintDone);
}
