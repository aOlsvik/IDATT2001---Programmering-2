package edu.ntnu.idatt2001.alekol.Wargames.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


/**
 * the main method of the program, will launch the front page
 */

public class Main extends Application {

    /**
     * main method to run the program
     * @param args -
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * method to launch the front page
     * @param stage -
     */
    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("FrontPage.fxml"))));
            stage.setTitle("Wargames");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
