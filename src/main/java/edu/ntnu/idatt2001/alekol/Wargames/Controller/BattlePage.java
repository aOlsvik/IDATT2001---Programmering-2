package edu.ntnu.idatt2001.alekol.Wargames.Controller;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Battle.Battle;
import edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement.FileManagement;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;
import edu.ntnu.idatt2001.alekol.Wargames.View.AlertBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BattlePage implements Initializable {

    public AnchorPane anchorPane;
    public Button startBattleButton;
    public Canvas canvasBattle;
    public Canvas unitCanvas1;
    public Canvas unitCanvas2;
    public Label labelArmyNameOne;
    public Label labelArmyNameTwo;
    public Label labelVS;
    public Label labelTerrain;
    public Label labelHelp;
    public ImageView imageTerrain;
    public Button pauseBattleButton;
    public Circle circleHelp;

    private Battle battle;
    private Timeline t= new Timeline();
    private GraphicsContext unitGC1;
    private GraphicsContext unitGC2;
    private Unit unit;
    private GraphicsContext battleGC;
    private Army armyOne;
    private Army armyTwo;
    private final String helpText= """
            Help
            On this page, you can simulate a battle between the two selected armies. The army names are displayed along with their file location.
            To start a simulation, press the 'Start battle' button, You can pause / resume the simulation with the 'Pause battle' button
            The simulation will update the armies that are drawn up, along with the unit information. After a battle, the final result will be displayed""";

    /**
     * method to go back to the army selector page
     * @param event - the click on the button
     */
    public void backToSelectArmies(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SelectArmies.fxml")));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            AlertBox.displayAlertBox("Could not load this page, please try again");
        }
    }

    /**
     * a method to initialize the page, based on the battle
     * Will draw up the initial battle and unit stats, and display the terrain of the battle
     * @param url -url
     * @param resourceBundle - resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        battleGC = canvasBattle.getGraphicsContext2D();
        unitGC1 = unitCanvas1.getGraphicsContext2D();
        unitGC2 = unitCanvas2.getGraphicsContext2D();
        getBattle();
        setBattleText();
        setTerrain();
        drawInitialUnitInfo();
        drawInitialBattle();
        circleSetUp();
    }

    /**
     * Method to get the battle from the file containing the current battle, and set the armies
     * Sets battle.getArmyOne() to armyOne, for easier coding and more readability
     */
    private void getBattle() {
        try{
            battle= FileManagement.readBattleNamesFromFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        armyOne=battle.getArmyOne();
        armyTwo=battle.getArmyTwo();
    }

    /**
     * Sets the text on the top, to display what armies are battling
     * Will also show you the file name from where the army was read
     */
    private void setBattleText(){
        labelArmyNameOne.setText(armyOne.getName() + ", read from " + armyOne.getName().replaceAll("\\s", "") + ".csv");
        labelVS.setVisible(true);
        labelArmyNameTwo.setText(armyTwo.getName() + ", read from " + armyTwo.getName().replaceAll("\\s", "") + ".csv");
    }

    /**
     * Method to display the terrain of the battle
     * Will display the name and a picture of the terrain
     */
    private void setTerrain() {
        String imageName = battle.getTerrain().toString().charAt(0) + battle.getTerrain().toString().substring(1).toLowerCase();
        labelTerrain.setText(imageName);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Terrains/"+imageName + ".png")));
        imageTerrain.setImage(img);
    }

    /**
     * when a battle is started / reset, this will be used to draw the two full armies on the canvas.
     * Will draw the units on a line, with color based on their type
     * Due to the canvas having the size it has, the army sizes are limited to 1072 per army.
     * This is so that every unit fits in the picture
     */
    private void drawInitialBattle(){
        int x=0;
        int y=238;
        int size;
        battleGC.clearRect(0,0,1000,500);
        for(Unit unit: armyOne.getAllUnits()){
            size=10;
            if(x>=1000){
                x=0;
                y-=15;
            }
            battleGC.setFill(getUnitDrawColor(armyOne,unit));
            unit.setY(y);
            unit.setX(x);
            battleGC.fillRect(x,y,size,size);
            x+=15;
        }
        battleGC.setFill(Color.BLACK);
        battleGC.strokeLine(0,255,1000,255);
        x=0;
        y=262;
        for(Unit unit: armyTwo.getAllUnits()){
            size= 10;
            if(x>=1000){
                x=0;
                y+=15;
            }
            battleGC.setFill(getUnitDrawColor(armyTwo,unit));
            unit.setY(y);
            unit.setX(x);
            battleGC.fillRect(x,y,size,size);
            x+=15;
        }
    }

    /**
     * Method to set up the function of the "help button". This was supposed to be round, so it is a circle,
     * not a button. Therefore, the styling (with hover) is set up manually, to make it act like a button
     * When clicked, it will toggle on / off the help text. This text will help you navigate in a page that
     * otherwise could be hard to understand.
     */
    private void circleSetUp(){
        circleHelp.hoverProperty().addListener((observable, s,t) -> {
            if(circleHelp.isHover())circleHelp.setFill(Color.valueOf("a1b5d0"));
            else circleHelp.setFill(Color.valueOf("7a94d0"));
        });
        circleHelp.setOnMouseClicked(event-> {
            if(labelHelp.isVisible()){
                labelHelp.setVisible(false);

            }else{
                labelHelp.setText(helpText);
                labelHelp.setVisible(true);
            }
        });
    }

    /**
     * Method to draw up the initial unit information on the canvas.
     * Will display how many there are of each unit, alongside the color that they are drawn with in the canvas
     */
    private void drawInitialUnitInfo(){
        unitGC1.clearRect(0,0,500,500);
        unitGC2.clearRect(0,0,500,500);
        unitGC1.setFont(new Font("Helvetica", 14));
        unitGC2.setFont(new Font("Helvetica", 14));

        unitGC1.setFill(getUnitDrawColor(armyOne,new InfantryUnit("test",100)));
        unitGC1.fillRect(5,5,10,10);
        unitGC1.setFill(Color.BLACK);
        unitGC1.fillText(armyOne.getInfantryUnits().size() + " x Infantry units",20,14 );

        unitGC1.setFill(getUnitDrawColor(armyOne,new RangedUnit("test",100)));
        unitGC1.fillRect(5,30,10,10);
        unitGC1.setFill(Color.BLACK);
        unitGC1.fillText(armyOne.getRangedUnits().size() + " x Ranged units",20,39 );

        unitGC1.setFill(getUnitDrawColor(armyOne,new CavalryUnit("test",100)));
        unitGC1.fillRect(5,55,10,10);
        unitGC1.setFill(Color.BLACK);
        unitGC1.fillText(armyOne.getCavalryUnits().size() + " x Cavalry units",20,64 );

        unitGC1.setFill(getUnitDrawColor(armyOne,new CommanderUnit("test",100)));
        unitGC1.fillRect(5,80,10,10);
        unitGC1.setFill(Color.BLACK);
        unitGC1.fillText(armyOne.getCommanderUnits().size() + " x Commander units",20,89 );

        unitGC1.fillText("Total units: " + armyOne.getAllUnits().size(), 20, 114);

        unitGC2.setFill(getUnitDrawColor(armyTwo,new InfantryUnit("test",100)));
        unitGC2.fillRect(185,5,10,10);
        unitGC2.setFill(Color.BLACK);
        unitGC2.fillText("Infantry units x " + armyTwo.getInfantryUnits().size(),55,14 );

        unitGC2.setFill(getUnitDrawColor(armyTwo,new RangedUnit("test",100)));
        unitGC2.fillRect(185,30,10,10);
        unitGC2.setFill(Color.BLACK);
        unitGC2.fillText("Ranged units x " + armyTwo.getRangedUnits().size(),55,39 );

        unitGC2.setFill(getUnitDrawColor(armyTwo,new CavalryUnit("test",100)));
        unitGC2.fillRect(185,55,10,10);
        unitGC2.setFill(Color.BLACK);
        unitGC2.fillText( "Cavalry units x "  +armyTwo.getCavalryUnits().size(),55,64 );

        unitGC2.setFill(getUnitDrawColor(armyTwo,new CommanderUnit("test",100)));
        unitGC2.fillRect(185,80,10,10);
        unitGC2.setFill(Color.BLACK);
        unitGC2.fillText("Commander units x " + armyTwo.getCommanderUnits().size(),45,89 );

        unitGC2.fillText("Total units: " + armyTwo.getAllUnits().size(), 80, 114);
    }

    /**
     * the method to get the color based on unit type.
     * The main color will be the color of the army's banner.
     * The color will be darker and darker based on unit type.
     * This is for displaying purposes, as you can easily see which unit is which.
     * Infantry unit - normal color
     * Ranged unit - darker color
     * Cavalry unit - even darker color
     * Commander unit - the darkest color.
     * @param army the army, which has a banner
     * @param unit - the unit, which has a type
     * @return the color for the specified unit from the specified army
     */
    private Color getUnitDrawColor(Army army, Unit unit){
        Color armyColor = Color.valueOf(army.getBanner().toString());

        switch(unit.getClass().getSimpleName()){
            case "InfantryUnit" -> {
                return armyColor;
            }
            case "RangedUnit" -> {
                return armyColor.darker();
            }
            case "CavalryUnit" -> {
                return armyColor.darker().darker();
            }
            case "CommanderUnit" -> {
                return armyColor.darker().darker().darker();
            }
        }
        return null;
    }

    /**
     * The method that is activated when you press the 'Start battle' button
     * If the battle is finished, it will reset the battle first. Then you can press it again to restart the battle
     * If the battle has been reset / or not simulated yet, pressing the button will activate this.
     * A timeline will run the battle, and draw the units on the canvas as the battle proceeds.
     * It will also update the unit info if a unit dies.
     * The battle continues until one of the armies have no units
     */
    public void simulateBattle()  {
        if(battle.isFinished()){
            try {
                getBattle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setBattleText();
            drawInitialBattle();
            drawInitialUnitInfo();
            startBattleButton.setText("Start battle");
        }
        else{
            pauseBattleButton.setDisable(false);
            startBattleButton.setDisable(true);
            drawInitialBattle();
            drawInitialUnitInfo();
            setBattleText();

            if(t.getStatus()!=Animation.Status.RUNNING){
                t = new Timeline(new KeyFrame(Duration.seconds(0.001),e->{
                    unit=battle.simulationStep();
                    if(unit!=null){
                        drawUpdatedUnitInfo();
                    }
                    drawUpdatedBattle();
                    if(!armyOne.hasUnits() || !armyTwo.hasUnits()) {
                        resetBattle();
                    }
                }));
                t.setCycleCount(Animation.INDEFINITE);
                t.play();
            }
        }
    }

    /**
     * Method to pause an ongoing battle. This will be called when 'Pause battle' button is pressed
     * Will pause / resume the battle based on the status of the timeline (Running / not)
     */
    public void pauseBattle(){
        if(t.getStatus().equals(Animation.Status.RUNNING)){
            t.pause();
            pauseBattleButton.setText("Resume battle");
        } else {
            t.play();
            pauseBattleButton.setText("Pause battle");
        }
    }

    /**
     * Method called when the battle is finished. Will stop the timeline, set the winner text, and make the
     * 'reset battle' button enabled.
     */
    private void resetBattle() {
        t.stop();
        battle.setWinner(armyOne.hasUnits() ? armyOne : armyTwo);
        if (battle.getWinner().equals(armyOne)){
            labelArmyNameOne.setText(armyOne.getName() + " won after "+ battle.getAttackCounter()+" attacks!");
            labelArmyNameTwo.setText(armyTwo.getName());
        } else {
            labelArmyNameTwo.setText(armyTwo.getName() + " won after "+ battle.getAttackCounter()+" attacks!");
            labelArmyNameOne.setText(armyOne.getName());
        }
        labelVS.setVisible(false);
        startBattleButton.setDisable(false);
        startBattleButton.setText("Reset battle");
        pauseBattleButton.setDisable(true);
    }

    /**
     * Method to draw up the updated unit information. Will be updated when a unit dies.
     * Will update how many there are of each type, as well as how many total units there are
     */
    private void drawUpdatedUnitInfo(){
        unitGC1.clearRect(19,0,181,115);
        unitGC2.clearRect(0,0,184,115);

        unitGC1.fillText(armyOne.getInfantryUnits().size() + " x Infantry units",20,14 );

        unitGC1.fillText(armyOne.getRangedUnits().size() + " x Ranged units",20,39 );


        unitGC1.fillText(armyOne.getCavalryUnits().size() + " x Cavalry units",20,64 );


        unitGC1.fillText(armyOne.getCommanderUnits().size() + " x Commander units",20,89 );

        unitGC1.fillText("Total units: " + armyOne.getAllUnits().size(), 20, 114);


        unitGC2.fillText("Infantry units x " + armyTwo.getInfantryUnits().size(),55,14 );


        unitGC2.fillText("Ranged units x " + armyTwo.getRangedUnits().size(),55,39 );


        unitGC2.fillText( "Cavalry units x "  +armyTwo.getCavalryUnits().size(),55,64 );


        unitGC2.fillText("Commander units x " + armyTwo.getCommanderUnits().size(),45,89 );

        unitGC2.fillText("Total units: " + armyTwo.getAllUnits().size(), 80, 114);
    }

    /**
     * Method to draw the updated battle as it proceeds. Both armies will be drawn up again, and each unit will be drawn
     * in their x/y location. They will stand still, but their health will determine the size of the unit drawn.
     * This makes it so that when watching the simulation, the units gradually become smaller. This looks nice, as you
     * can see that something is happening.
     * The minimum size is set to 3, so that the units don't get too small. That would just be a bad display
     */
    private void drawUpdatedBattle(){
        int x;
        int y;
        int size;
        battleGC.clearRect(0,0,1000,500);
        for(Unit unit: armyOne.getAllUnits()){
            battleGC.setFill(getUnitDrawColor(armyOne,unit));
            size=Math.max(unit.getHealth()/10,3);
            x= unit.getX();
            y= unit.getY();
            battleGC.fillRect(x,y,size,size);
        }
        if(!battle.isFinished()){
            battleGC.setFill(Color.BLACK);
            battleGC.strokeLine(0,255,1000,255);
        }
        for(Unit unit: armyTwo.getAllUnits()){
            battleGC.setFill(getUnitDrawColor(armyTwo,unit));
            size= Math.max(unit.getHealth()/10,3);
            x= unit.getX();
            y= unit.getY();
            battleGC.fillRect(x,y,size,size);
        }
    }
}
