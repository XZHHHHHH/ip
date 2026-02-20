package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import zhbot.Zhbot;

import java.io.IOException;

/**
 * A GUI for Zhbot using FXML.
 */
public class Main extends Application {

    private final Zhbot zhbot = new Zhbot("data/zh.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane appRoot = fxmlLoader.load();
            assert appRoot != null : "Main window root should load from FXML.";
            Scene scene = new Scene(appRoot);
            stage.setScene(scene);
            stage.setTitle("Zhbot");
            stage.setResizable(false);
            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "Main window controller should be available after load.";
            controller.setZhbot(zhbot);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to start GUI.", e);
        }
    }
}
