package com.zmcsoft.apsp.client.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.http.driver.HttpDriverHandler;
import com.zmcsoft.apsp.client.http.jsapi.JSHandler;
import javafx.application.Application;
import org.java_websocket.WebSocketImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author zhouhao
 * @since 1.0
 */
public class HttpApiServer {
    public static void main(String[] args) throws Exception {
        start();
    }

    static void start() throws Exception {
//        WebSocketImpl.DEBUG = "true".equalsIgnoreCase(ApplicationConfig.getConfig("debug", "true"));
        InetSocketAddress addr = new InetSocketAddress(5010);
        HttpServer server = HttpServer.create(addr, 0);
        HttpDriverHandler handler = new HttpDriverHandler(new InetSocketAddress(5011));
        server.createContext("/drivers", handler).getFilters()
                .add(new Filter() {
                    @Override
                    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

                        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "*");
                        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                        chain.doFilter(httpExchange);
                    }

                    @Override
                    public String description() {
                        return "";
                    }
                });
        server.createContext("/js-api", new JSHandler());
        server.setExecutor(Global.executorService);
        server.start();
        handler.start();
        System.out.println("Server is listening on port 5010");
    }
}
