package com.zmcsoft.apsp.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author zhouhao
 * @since 1.0
 */
public class SettingWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setTitle("设置");
        root.setPrefHeight(600);
        Scene scene = new Scene(root, 900, 600, Color.web("white"));
        primaryStage.setScene(scene);

        root.setVisible(true);
    }
}
