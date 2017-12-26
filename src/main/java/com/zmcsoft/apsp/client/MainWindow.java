package com.zmcsoft.apsp.client;

import com.github.sarxos.webcam.Webcam;
import com.zmcsoft.apsp.client.config.ApplicationConfig;
import com.zmcsoft.apsp.client.database.H2DatabaseManager;
import com.zmcsoft.apsp.client.drivers.camera.CameraOperation;
import com.zmcsoft.apsp.client.javascript.JavaScriptObject;
import com.zmcsoft.apsp.client.javascript.Console;
import com.zmcsoft.apsp.client.javascript.drivers.PrintDriver;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class MainWindow extends Application {
    private String title = ApplicationConfig.getConfig("app.title", "apsp client 1.0");
    private Scene scene;

    private boolean debug = "true".equals(ApplicationConfig.getConfig("debug", "true"));

    private static List<JavaScriptObject> objects = new ArrayList<>();

    static {
        objects.add(new Console());
        objects.add(new PrintDriver());
        objects.add((JavaScriptObject) H2DatabaseManager.getSqlExecutor());
        log.info("load font YaHei.Consolas.ttf");
        Font.loadFont(MainWindow.class.getResource("YaHei.Consolas.ttf").toExternalForm(), 20);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage.setTitle(title);
        root.setPrefHeight(600);
        scene = new Scene(root, 900, 600, Color.web("#666970"));
        primaryStage.setScene(scene);

        WebView webView = new WebView();
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        CameraOperation cameraOperation = new CameraOperation(root);
        webView.setOnTouchPressed(event -> System.out.println(event));

        final WebEngine engine = webView.getEngine();
        engine.setUserDataDirectory(new File("./data/browser"));
        engine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    log.info("load page:{}", observable);
                    if (Worker.State.SUCCEEDED.equals(newValue)) {
                        JSObject jsObject = (JSObject) engine.executeScript("window");
                        for (JavaScriptObject object : objects) {
                            jsObject.setMember(object.getName(), object);
                        }
                        jsObject.setMember("database", H2DatabaseManager.getDatabase());
                        jsObject.setMember("camera", cameraOperation);
                        if (debug) {
                            webView.getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
                        }
                    }
                });
        engine.setOnError(event -> {
            log.error(event.getMessage(), event);
        });
        //engine.loadContent("<h1 style=\"font-family: 'YaHei Consolas Hybrid'\">abcABC1234测试</h1>");
        engine.load(new File("./ui/index.html").toURI().toString());
        root.getChildren().add(webView);

        primaryStage.setOnCloseRequest(event -> {
            cameraOperation.close();
            Global.executorService.shutdownNow();
        });
        primaryStage.show();
        root.setVisible(true);
        // primaryStage.setFullScreen(true);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
