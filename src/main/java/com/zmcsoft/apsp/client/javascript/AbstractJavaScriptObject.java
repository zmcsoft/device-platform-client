package com.zmcsoft.apsp.client.javascript;

import com.zmcsoft.apsp.client.javascript.JavaScriptObject;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public abstract class AbstractJavaScriptObject implements JavaScriptObject {

    @SuppressWarnings("all")
    protected <T> T getProperty(String property, Object jsObject) {
        try {
            JSObject object = ((JSObject) jsObject);
            return (T) object.getMember(property);
        } catch (Exception e) {
            log.error("获取js对象中的属性失败", e);
            throw e;
        }
    }

    protected Object call(Object object, String name, Object... args) {
        try {
            return ((JSObject) object).call(name, args);
        } catch (Exception e) {
            log.error("执行js函数失败:\n{}", getProperty(name, object), e);
            throw e;
        }
    }
}
