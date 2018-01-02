package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Pager;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PageBuilder {
    Pager build(String config);
}
