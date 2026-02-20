package cors.ui;

import cors.command.CommandType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Cors cors;

    private Image userImage;
    private Image corsImage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
        corsImage = new Image(this.getClass().getResourceAsStream("/images/anonymous.png"));

    }

    /** Injects the Cors instance */
    public void setCors(Cors cors) {
        this.cors = cors;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = cors.getResponse(input);
        CommandType commandType = cors.getCommandType();
        assert(userImage != null);
        assert(corsImage != null);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCorsDialog(response, corsImage, commandType)
        );
        userInput.clear();
    }
}
