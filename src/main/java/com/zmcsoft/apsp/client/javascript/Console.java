package com.zmcsoft.apsp.client.javascript;

import com.zmcsoft.apsp.client.javascript.JavaScriptObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouhao
 * @since 3.0
 */
@Slf4j
public class Console implements JavaScriptObject {

    @Override
    public String getName() {
        return "console";
    }

    public void log(Object l) {
        log.info(String.valueOf(l));
    }

    public void error(Object l) {
        log.error(String.valueOf(l));
    }

    public void warn(Object l) {
        log.warn(String.valueOf(l));
    }

    public void debug(Object l) {
        log.debug(String.valueOf(l));
    }

    public void dir(Object l) {
        log(l);
    }
}
