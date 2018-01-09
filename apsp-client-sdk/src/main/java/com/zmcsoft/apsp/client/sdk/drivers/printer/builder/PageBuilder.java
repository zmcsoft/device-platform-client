package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Pager;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PageBuilder {
    List<Pager> build(String config);
}
