package edu.ntnu.idatt2001.alekol.Wargames.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class FrontPage {


    @FXML
    private Label aboutGameLabel;


    /**
     * Method to go to the game scene. Will activate when 'Start Game' button is pressed
     * @param event the click
     */
    public void switchToGameScene(ActionEvent event){
        try{
            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SelectArmies.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to get info about the game. Will display a short text in a box.
     * This button, 'About game' toggles this label on/off
     */
    public void aboutGame(){
        if(aboutGameLabel.isVisible()){
            aboutGameLabel.setVisible(false);
        } else {
            aboutGameLabel.setText(getAboutText());
            aboutGameLabel.setVisible(true);
        }
    }


    /**
     * Method to exit the game from the front page
     * Activates when 'quit' is pressed
     */
    public void quitGame(){
        System.exit(0);
    }

    /**
     * Method to get the text displayed in the 'about game' section
     * @return - the information text
     */
    public String getAboutText(){
        return """
                About the game
                This is a game to simulate a battle, or war, between two armies
                In this game you can create armies, or use the pre made ones
                You can then go to the simulation,
                where you will see which army won the battle

                Enjoy the game!""";
    }
}
