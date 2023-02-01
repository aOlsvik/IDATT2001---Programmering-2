package edu.ntnu.idatt2001.alekol.Wargames.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Useful class, which only has a static method
 * This will display an alert box, where information can be relayed to the user of the program
 */
public class AlertBox {

    /**
     * A method to create a new stage with a message and a button to close it. This will work as an error message,
     * when the user clicks or does something the program will not allow.
     * @param message - The message to be displayed in the program
     */
    public static void displayAlertBox(String message) {
        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Error");
        popUpWindow.setMinWidth(250);
        popUpWindow.setMinHeight(250);

        Label notification = new Label();
        notification.setText(message);

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> popUpWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(notification, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        popUpWindow.setScene(scene);
        popUpWindow.setResizable(false);
        popUpWindow.showAndWait();
    }
}
