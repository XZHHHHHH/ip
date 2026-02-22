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

/**
 * Dialog box component used to render user and bot chat messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image image) {
        assert text != null : "Dialog text should not be null.";
        assert image != null : "Dialog image should not be null.";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load DialogBox FXML.", e);
        }

        assert dialog != null : "Dialog label should be injected by FXML.";
        assert displayPicture != null : "Display picture should be injected by FXML.";
        dialog.setText(text);
        displayPicture.setImage(image);
        applySharedStyling();
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        List<Node> flippedChildren = new ArrayList<>(this.getChildren());
        Collections.reverse(flippedChildren);
        this.getChildren().clear();
        this.getChildren().addAll(flippedChildren);
    }

    /**
     * Creates a right-aligned dialog for user input.
     *
     * @param text Message text.
     * @param image Avatar image.
     * @return Styled dialog box for the user.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        DialogBox dialog = new DialogBox(text, image);
        dialog.applyUserStyling();
        return dialog;
    }

    /**
     * Creates a left-aligned dialog for bot responses.
     *
     * @param text Message text.
     * @param image Avatar image.
     * @return Styled dialog box for the bot.
     */
    public static DialogBox getDarkraiDialog(String text, Image image) {
        DialogBox dialog = new DialogBox(text, image);
        dialog.flip();
        dialog.applyBotStyling();
        return dialog;
    }

    private void applySharedStyling() {
        setSpacing(10);
        dialog.setMaxWidth(260);
        dialog.setStyle("-fx-background-radius: 16;"
                + "-fx-padding: 10 14 10 14;"
                + "-fx-font-size: 14px;");
        displayPicture.setFitWidth(52);
        displayPicture.setFitHeight(52);
        displayPicture.setPreserveRatio(false);
        displayPicture.setStyle("-fx-background-color: #ffffff;"
                + "-fx-background-radius: 8;"
                + "-fx-border-color: #c4d1e4;"
                + "-fx-border-radius: 8;");
    }

    private void applyUserStyling() {
        dialog.setStyle(dialog.getStyle()
                + "-fx-background-color: #ffffff;"
                + "-fx-text-fill: #1f2937;"
                + "-fx-border-color: #c6d5ea;"
                + "-fx-border-radius: 16;");
    }

    private void applyBotStyling() {
        dialog.setStyle(dialog.getStyle()
                + "-fx-background-color: #dbeaff;"
                + "-fx-text-fill: #102a43;"
                + "-fx-border-color: #9cb8e8;"
                + "-fx-border-radius: 16;");
    }
}
