package com.zmcsoft.apsp.client.javascript;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.hswebframework.utils.RandomUtil;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public abstract class AbstractJavaScriptObject implements JavaScriptObject {
    protected WebEngine engine;

    protected JSObject window;

    public void setEngine(WebEngine engine) {
        this.engine = engine;
        Platform.runLater(()->{
            window = (JSObject) engine.executeScript("window");
        });
    }

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

    protected Object call(Object object, Object args) {
        if (object == null) {
            return null;
        }

        String funName = "f_" + RandomUtil.randomChar();
        String paramName = "p_" + RandomUtil.randomChar();
        window.setMember(funName, object);
        window.setMember(paramName, args);
        try {
            return engine.executeScript("window." + funName + "(" + paramName + ")");
        } catch (Exception e) {
            log.warn("eval function error :\n{} \n{}", object, args, e);
        } finally {
            window.removeMember(funName);
            window.removeMember(paramName);
        }
        return null;
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
