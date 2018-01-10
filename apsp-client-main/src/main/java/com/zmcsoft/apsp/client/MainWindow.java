package com.zmcsoft.apsp.client;

import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.javascript.JavaScriptObject;
import com.zmcsoft.apsp.client.javascript.Console;
import com.zmcsoft.apsp.client.javascript.drivers.DeviceDriversWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

    private static Stage stage;

    private boolean debug = "true".equals(ApplicationConfig.getConfig("debug", "true"));

    private static List<JavaScriptObject> objects = new ArrayList<>();

    static volatile boolean hide = false;

    public static void hide() {
        hide = true;
        stage.hide();
    }

    public static void show() {
        hide = false;
        stage.show();
    }

    static {
        objects.add(new Console());
        objects.add(new DeviceDriversWrapper());
        //log.info("load font YaHei.Consolas.ttf");
        //  Font.loadFont(MainWindow.class.getResource("YaHei.Consolas.ttf").toExternalForm(), 20);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        StackPane root = new StackPane();
        primaryStage.setTitle(title);
        root.setPrefHeight(600);
        scene = new Scene(root, 900, 600, Color.web("#666970"));
        primaryStage.setScene(scene);

        WebView webView = new WebView();
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        final WebEngine engine = webView.getEngine();

        engine.setUserDataDirectory(new File("./data/browser"));
        engine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    log.info("load page:{}", observable);
                    if (Worker.State.SUCCEEDED.equals(newValue)) {
                        JSObject jsObject = (JSObject) engine.executeScript("window");
//                        for (JavaScriptObject object : objects) {
//                            if (object instanceof AbstractJavaScriptObject) {
//                                ((AbstractJavaScriptObject) object).setEngine(engine);
//                            }
//                            jsObject.setMember(object.getName(), object);
//                        }
                        jsObject.setMember("controller", new Controller());
                        if (debug) {
                            //  webView.getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
                        }
                    }
                });
        engine.setOnError(event -> {
            log.error(event.getMessage(), event);
        });
        engine.load(MainWindow.class.getResource("/ui/index.html").toString());
        root.getChildren().add(webView);
        primaryStage.setOnCloseRequest(event -> {
            Global.executorService.execute(() -> {
                Platform.runLater(() -> {
                    if (!hide) {
                        show();
                    }
                });
            });
        });
        // root.setVisible(true);
        if (!debug) {
            primaryStage.setFullScreenExitKeyCombination(new KeyCombination() {
                @Override
                public boolean match(KeyEvent event) {
                    if (event.isControlDown() && event.isAltDown()) {
                        return true;
                    }
                    return false;
                }
            });
            primaryStage.setFullScreenExitHint("");

            primaryStage.setFullScreen(true);

            webView.setContextMenuEnabled(false);
            //webView.getEngine().setUserAgent("");
            primaryStage.show();
        }
        primaryStage.setAlwaysOnTop(true);
    }

    public class Controller {
        public void hide() {
            MainWindow.hide();
        }

        public void show() {
            MainWindow.show();
        }

        public void fullScreen(boolean fc) {
            stage.setFullScreen(fc);
        }

        public boolean isFullScreen() {
            return stage.isFullScreen();
        }
    }
}
