package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class PrintDriver extends AbstractJavaScriptObject {
    @Override
    public String getName() {
        return "printer";
    }

    public void print(Object request) {
        String template = getProperty("template", request);
        String dataJson = getProperty("data", request);
        log.info("执行打印:\n{}\n{}", template, dataJson);
        Object obj = call(request, "callback", "打印成功");
        System.out.println(obj);
    }

    interface PrintCallback extends Consumer<String> {

    }
}
