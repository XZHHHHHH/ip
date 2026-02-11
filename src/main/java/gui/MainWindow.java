package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import zhbot.Zhbot;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Zhbot zhbot;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/User.png"));
    private final Image darkraiImage = new Image(this.getClass().getResourceAsStream("/Zh.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.setFitToWidth(true);
    }

    /**
     * Injects the bot instance.
     *
     * @param bot bot instance used to generate responses.
     */
    public void setZhbot(Zhbot bot) {
        zhbot = bot;
    }

    /**
     * Creates two dialog boxes, one containing user input and the other containing Darkrai's reply,
     * then appends them to the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = zhbot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDarkraiDialog(response, darkraiImage)
        );
        userInput.clear();
    }
}
