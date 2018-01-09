package com.zmcsoft.apsp.client.http.jsapi;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author zhouhao
 * @since 1.0
 */
public class JSHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        Headers responseHeaders = exchange.getResponseHeaders();

        String path = uri.getPath();
        try (InputStream stream = JSHandler.class.getResourceAsStream("/static" + path);
             OutputStream out = exchange.getResponseBody()) {
            responseHeaders.set("Content-Type", "application/javascript");
            responseHeaders.set("Content-Length", String.valueOf(stream.available()));

            exchange.sendResponseHeaders(200, 0L);
            byte[] buf = new byte[512];
            int t;
            while ((t = stream.read(buf)) != -1) {
                out.write(buf, 0, t);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
