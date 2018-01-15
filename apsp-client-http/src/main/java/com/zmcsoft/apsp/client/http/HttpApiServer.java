package com.zmcsoft.apsp.client.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.http.driver.HttpDriverHandler;
import com.zmcsoft.apsp.client.http.jsapi.JSHandler;
import com.zmcsoft.apsp.client.http.monitor.WebsocketDeviceMonitor;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class HttpApiServer {
    public static void main(String[] args) throws Exception {
        start(5010, 5011);
    }

    static HttpServer server;

    static WebSocketServer socketServer;

    public static void shutdown() {
        if (server != null) {
            server.stop(10);
            try {
                socketServer.stop(10);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        server = null;
        socketServer = null;
    }

    public static boolean httpIsOpen() {
        return server != null;
    }

    public static boolean wsIsOpen() {
        return socketServer != null;
    }

    public static void start(int httpPort, int webSocketPort) {
        shutdown();
        try {
            InetSocketAddress addr = new InetSocketAddress(httpPort);
            HttpServer server = HttpServer.create(addr, 0);
            HttpDriverHandler handler = new HttpDriverHandler(new InetSocketAddress(webSocketPort));
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
            HttpApiServer.server = server;
            HttpApiServer.socketServer = handler;
            WebsocketDeviceMonitor.startMonitor(ApplicationConfig.getConfig("monitor.url", "http://localhost:8080/socket"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
