package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
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
        assert scrollPane != null : "ScrollPane should be injected by FXML.";
        assert dialogContainer != null : "Dialog container should be injected by FXML.";
        assert userInput != null : "User input field should be injected by FXML.";
        assert sendButton != null : "Send button should be injected by FXML.";
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.setFitToWidth(true);
    }

    /**
     * Injects the bot instance.
     *
     * @param bot bot instance used to generate responses.
     */
    public void setZhbot(Zhbot bot) {
        assert bot != null : "Bot instance should not be null.";
        zhbot = bot;
        dialogContainer.getChildren().add(
                DialogBox.getDarkraiDialog(zhbot.getWelcomeMessage(), darkraiImage)
        );
    }

    /**
     * Creates two dialog boxes, one containing user input and the other containing Darkrai's reply,
     * then appends them to the dialog container.
     */
    @FXML
    private void handleUserInput() {
        assert zhbot != null : "Bot should be set before handling user input.";
        String input = userInput.getText();
        String response = zhbot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDarkraiDialog(response, darkraiImage)
        );
        userInput.clear();
        if (isByeCommand(input)) {
            closeAfterDelay();
        }
    }

    private boolean isByeCommand(String input) {
        return input != null && input.trim().equals("bye");
    }

    private void closeAfterDelay() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }
}
