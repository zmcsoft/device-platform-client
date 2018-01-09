package com.zmcsoft.apsp.client.javascript;

import com.zmcsoft.apsp.client.core.Global;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.hswebframework.utils.RandomUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public abstract class AbstractJavaScriptObject implements JavaScriptObject {
    protected volatile WebEngine engine;

    protected volatile JSObject window;

    protected ReadWriteLock lock = new ReentrantReadWriteLock();

    private AtomicLong counter = new AtomicLong();

    protected  static Set<Object> cache = new HashSet<>();

    public void setEngine(WebEngine engine) {
        try {
            cache.clear();
            this.engine = engine;
//        Platform.runLater(() -> {
            window = (JSObject) engine.executeScript("window");
//        });
        } finally {
            counter.set(0L);
        }
    }

    protected Disposable execute(Callable callable, Object callback) {
        cache.add(callback);
        return execute(callable)
                .subscribe(r -> call(callback, r));

    }

    protected void execute(Runnable runnable) {
        Global.executorService.submit(runnable);
    }

    protected <T> Observable<T> execute(Callable<T> callable) {
        return Observable.create((r) -> {
            Global.executorService.execute(() -> {
                try {
                    r.onNext(callable.call());
                } catch (Exception e) {
                    r.onError(e);
                }
            });
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

    protected synchronized void call(Object object, Object args) {
        if (object == null) {
            return;
        }
        if (!cache.contains(object)) {
            log.warn("page reloaded!");
            return;
        }
        Platform.runLater(() -> {
            if (!cache.contains(object)) {
                log.warn("page reloaded!");
                return;
            }
            String funName = "f_" + RandomUtil.randomChar();
            String paramName = "p_" + RandomUtil.randomChar();
            window.setMember(funName, object);
            window.setMember(paramName, args);
            try {
                engine.executeScript("window." + funName + "(" + paramName + ")");
            } catch (Exception e) {
                log.warn("eval function error :\n{} \n{}", object, args, e);
            } finally {
                cache.remove(object);
                window.removeMember(funName);
                window.removeMember(paramName);
            }
        });

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
