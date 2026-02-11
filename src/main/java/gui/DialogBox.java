package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load DialogBox FXML.", e);
        }

        dialog.setText(text);
        displayPicture.setImage(image);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        List<Node> flippedChildren = new ArrayList<>(this.getChildren());
        Collections.reverse(flippedChildren);
        this.getChildren().clear();
        this.getChildren().addAll(flippedChildren);
    }

    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    public static DialogBox getDarkraiDialog(String text, Image image) {
        DialogBox dialog = new DialogBox(text, image);
        dialog.flip();
        return dialog;
    }
}
