package com.zmcsoft.apsp.client;

import com.zmcsoft.apsp.client.core.Global;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author zhouhao
 * @since 1.0
 */
public class ClientApplication extends Application {
    static Stage stage = new Stage();

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            SystemTray tray = SystemTray.get();
            tray.setImage(ClientApplication.class.getResource("/logo/zmcsoft.jpg"));
            tray.getMenu().add(new MenuItem("设置", e -> {
                Platform.runLater(() -> {
                    stage.show();
                });
            }));
            tray.getMenu().add(new MenuItem("打开主程序", e -> {
                Platform.runLater(MainWindow::show);
            }));
            tray.getMenu().add(new MenuItem("退出", e -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setWidth(100D);
                    alert.setTitle("确认?");
                    alert.setHeaderText(null);
                    alert.setContentText("确认退出本程序?");
                    alert.showAndWait()
                            .ifPresent(buttonType -> {
                                if (!buttonType.getButtonData().isCancelButton()) {
                                    tray.shutdown();
                                    Global.executorService.shutdown();
                                    Platform.runLater(Platform::exit);
                                    System.exit(0);
                                }
                            });
                });
            }));
        });
        thread.start();
        Platform.setImplicitExit(false);
        Application.launch(ClientApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.runLater(() -> {
            new SettingWindow().start(stage);
            new MainWindow().start(new Stage());
        });
    }
}
