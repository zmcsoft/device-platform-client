package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;

import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface LayerBuilder {
    String getType();

    Layer build(Map<String, Object> config);
}
