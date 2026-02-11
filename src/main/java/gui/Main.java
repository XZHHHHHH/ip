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
            Scene scene = new Scene(appRoot);
            stage.setScene(scene);
            stage.setTitle("Zhbot");
            stage.setResizable(false);
            fxmlLoader.<MainWindow>getController().setZhbot(zhbot);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to start GUI.", e);
        }
    }
}
