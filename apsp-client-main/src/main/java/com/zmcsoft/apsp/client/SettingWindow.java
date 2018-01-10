package com.zmcsoft.apsp.client;

import com.zmcsoft.apsp.client.core.Global;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author zhouhao
 * @since 1.0
 */
public class SettingWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.getIcons().add(new Image(SettingWindow.class.getResource("/logo/zmcsoft.jpg").toString()));
        primaryStage.setTitle("设置");
        root.setPrefHeight(600);
        Scene scene = new Scene(root, 900, 600, Color.web("white"));
        primaryStage.setScene(scene);
        WebView webView = new WebView();
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        final WebEngine engine = webView.getEngine();

        engine.setUserDataDirectory(new File("./data/browser"));
        engine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
//                    if (Worker.State.SUCCEEDED.equals(newValue)) {
//                        JSObject jsObject = (JSObject) engine.executeScript("window");
//                        for (JavaScriptObject object : objects) {
//                            if (object instanceof AbstractJavaScriptObject) {
//                                ((AbstractJavaScriptObject) object).setEngine(engine);
//                            }
//                            jsObject.setMember("controller", new Controller());
//                            jsObject.setMember(object.getName(), object);
//                        }
//                        if (debug) {
//                            //  webView.getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
//                        }
//                    }
                });
        engine.load(SettingWindow.class.getResource("/ui/setting.html").toString());
        root.getChildren().add(webView);
        primaryStage.setAlwaysOnTop(true);

    }
}
