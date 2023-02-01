package edu.ntnu.idatt2001.alekol.Wargames.Controller;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Battle.Battle;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Banner;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement.FileManagement;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;
import edu.ntnu.idatt2001.alekol.Wargames.View.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.*;

public class SelectArmies implements Initializable {

    public ComboBox<String> comboBoxArmyOne;
    public ComboBox<String> comboBoxArmyTwo;
    public ListView<String> listViewArmyOne;
    public ListView<String> listViewArmyTwo;
    public ListView<String> listViewArmyOneUnitInfo;
    public ListView<String> listViewArmyTwoUnitInfo;
    public ListView<String> listViewUnitStats;
    public ImageView imageTerrain;
    public ImageView imageBannerOne;
    public ImageView imageBannerTwo;
    public Label labelTerrainName;
    public Label labelHelp;
    public Button buttonNewArmy;
    public Button buttonStartBattle;
    public AnchorPane anchorPane;
    public Circle circleHelp;

    private HashMap<String, Army> armies = new HashMap<>();
    private ArrayList<String> armyNamesToRead;
    private ArrayList<String> armyNames=new ArrayList<>();
    private int terrain=0;
    private final String[] terrainImages= {"Forest", "Hill", "Plains"};
    private final String helpText= """
            Help
            To start a battle between two armies, select the armies from the two selection boxes below
            If you dont like the armies, or want something new, click 'Create new army' in the bottom of the page
            The selected armies will be displayed in the lists below the selection boxes
            Clicking on a unit type, e.g. '400 x Infantry unit' in the list will show the stats of infantry units
            You can also set the terrain of the battle by clicking the arrows beside the picture of the terrain
            When you are satisfied with the armies and the terrain, click 'Start battle' to go to the simulation""";


    /**
     * Method to bring you to the page where you can create your own army
     * @param event the click on the 'Create new army' button
     */
    public void createNewArmy(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ArmyCreator.fxml")));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
            AlertBox.displayAlertBox("Could not enter this page. Please try again");
        }
    }

    /**
     * Method to take you back to the homepage when you click the arrow top left
     * @param event the click
     */
    public void backToHomePage(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FrontPage.fxml")));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            AlertBox.displayAlertBox("Could not go back to the home page, please try again");
        }
    }

    /**
     * Method to set up all that is needed on the page
     * Will read armies, add the names to the selection boxes, and set up some listeners
     * @param url -url
     * @param resourceBundle - resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readArmiesFromFile();
        addArmyNamesToComboBox();
        addArmiesToListView();
        displayTerrain();
        displayUnitInfo();
        circleSetUp();
        imageBannerOne.setStyle("-fx-background-color: rgba(59, 56, 56, 0.96)");
        imageBannerTwo.setStyle("-fx-background-color: rgba(59, 56, 56, 0.96)");
    }

    /**
     * reads all the army names from the files, and adds the army name and army to a hashmap such as:
     * {Army One,[ Army One, units...]}
     */
    private void readArmiesFromFile() {
        String armyPath = "src/main/resources/ArmyFiles/Armies/";
        try{
            armyNamesToRead=FileManagement.readArmyNamesFromFile();
            for(String armyName : armyNamesToRead) {
                File file = new File(armyPath + armyName + ".csv");
                Army army = FileManagement.readArmyFromFile(file);
                armies.put(army.getName(), army);
                armyNames.add(army.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds all the army names to the selection box
     */
    private void addArmyNamesToComboBox() {
        for(String s: armyNames){
            comboBoxArmyOne.getItems().add(s);
            comboBoxArmyTwo.getItems().add(s);
        }
    }

    /**
     * Method that adds a listener on the selected item in each selection box.
     * Will add the army info to a list under the box, and display info to let you see unit stats
     */
    private void addArmiesToListView(){
        comboBoxArmyOne.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            String armyName = comboBoxArmyOne.getValue();
            if(armyName!=null){
                Army army = armies.get(armyName);
                String[] armyInfo= army.toString().split("\n");
                listViewArmyOne.getItems().clear();
                listViewArmyOne.getItems().addAll(armyInfo);
                listViewArmyOne.setStyle(".armyListView");
                imageBannerOne.setImage(getBannerImage(army));
                listViewArmyOneUnitInfo.getItems().clear();
                listViewArmyOneUnitInfo.getItems().add("Click on a unit to view its stats!");
                listViewArmyOneUnitInfo.getItems().add("\n");
                int unitTypes = listViewArmyOne.getItems().size()-2;
                for(int i = 0; i<unitTypes; i++){
                    listViewArmyOneUnitInfo.getItems().add("\uD83E\uDC78");
                }
            }
        });
        comboBoxArmyTwo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            String armyName = comboBoxArmyTwo.getValue();
            if(armyName!=null){
                Army army = armies.get(armyName);
                String[] armyInfo= army.toString().split("\n");
                listViewArmyTwo.getItems().clear();
                listViewArmyTwo.getItems().addAll(armyInfo);
                listViewArmyTwo.setStyle(".armyListView");
                imageBannerTwo.setImage(getBannerImage(army));
                listViewArmyTwoUnitInfo.getItems().clear();
                listViewArmyTwoUnitInfo.getItems().add("Click on a unit to view its stats!");
                listViewArmyTwoUnitInfo.getItems().add("\n");
                int unitTypes = listViewArmyTwo.getItems().size()-2;
                for(int i = 0; i<unitTypes; i++){
                    listViewArmyTwoUnitInfo.getItems().add("\uD83E\uDC78");
                }
            }
        });
    }

    /**
     * Method to display the current terrain and the stats for this specific terrain.
     * The current terrain will become the terrain for the battle
     */
    private void displayTerrain(){
        try{
            String terrainName = terrainImages[terrain];
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Terrains/" + terrainName + ".png")));
            imageTerrain.setImage(image);
            labelTerrainName.setText(terrainName);
            listViewUnitStats.getItems().clear();
            String[] stats = getTerrainStats().split("\n");
            Arrays.stream(stats).forEach(s-> listViewUnitStats.getItems().add(s));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to get the stats for a terrain
     * @return the stats, based on the current terrain
     */
    private String getTerrainStats(){
        String terrain = labelTerrainName.getText();
        StringBuilder sb = new StringBuilder();
        switch (terrain) {
            case "Hill" -> sb.append("Ranged Unit: +2 Attack Bonus\n");
            case "Forest" -> {
                sb.append("Infantry Unit: +2 Attack Bonus\n");
                sb.append("Infantry Unit: +2 Resistance Bonus\n");
                sb.append("Ranged Unit: +1 Attack Bonus\n");
                sb.append("Cavalry & Commander Unit: -1 Resistance Bonus\n");
            }
            case "Plains" -> sb.append("Cavalry & Commander Unit: +2 Attack Bonus\n");
        }
        return sb.toString();
    }

    /**
     * method to switch the terrain. Will be used when the left arrow is pressed.
     * Will decrease the terrain index, and display the previous terrain
     */
    public void switchTerrainLeft(){
        if(terrain==0) terrain=2;
        else terrain--;
        displayTerrain();
        listViewArmyOne.getSelectionModel().selectNext();
        listViewArmyOne.getSelectionModel().selectPrevious();
        listViewArmyTwo.getSelectionModel().selectNext();
        listViewArmyTwo.getSelectionModel().selectPrevious();
    }

    /**
     * method to switch the terrain. Will be used when the right arrow is pressed.
     * Will increase the terrain index, and display the next terrain
     */
    public void switchTerrainRight(){
        if(terrain==2) terrain=0;
        else terrain++;
        displayTerrain();
        listViewArmyOne.getSelectionModel().selectNext();
        listViewArmyOne.getSelectionModel().selectPrevious();
        listViewArmyTwo.getSelectionModel().selectNext();
        listViewArmyTwo.getSelectionModel().selectPrevious();
    }

    /**
     * method that adds a listener to both of the lists with army info. Selecting an item in one of those will display
     * the info about the unit selected. This can be done for both armies. Will display armor, attack, health and the
     * offensive / defensive bonuses based on the terrain that is selected.
     * If the selected item is either total units or the army name, an info text will be displayed
     */
    private void displayUnitInfo(){
        listViewArmyOne.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String unitTypeSelected = listViewArmyOne.getSelectionModel().getSelectedItem();
            Unit unit = null;
            if(unitTypeSelected!=null){
                if(unitTypeSelected.contains("Infantry Unit")) unit = UnitFactory.createUnit("InfantryUnit", "Infantry Unit" , 100);
                else if (unitTypeSelected.contains("Ranged Unit")) unit = UnitFactory.createUnit("RangedUnit", "Ranged Unit" , 100);
                else if( unitTypeSelected.contains("Cavalry Unit")) unit = UnitFactory.createUnit("CavalryUnit", "Cavalry Unit" , 100);
                else if(unitTypeSelected.contains("Commander Unit"))unit = UnitFactory.createUnit("CommanderUnit", "Commander Unit" , 180);
            }
            listViewArmyOneUnitInfo.getItems().clear();
            if(unit!=null){
                String unitInfo = unit.getClass().getSimpleName().replace("U", " U") + "\n" +
                        "Attack: " + unit.getAttack() + "\n" +
                        "Armor: " + unit.getArmor() +"\n" +
                        "Health: " + unit.getHealth() + "\n" +
                        "Defensive bonus: " + unit.getResistanceBonus(Terrain.valueOf(terrainImages[terrain].toUpperCase())) +"\n" +
                        "Attack bonus: " + unit.getAttackBonus(Terrain.valueOf(terrainImages[terrain].toUpperCase()));
                String[] unitInfoList = unitInfo.split("\n");
                listViewArmyOneUnitInfo.getItems().addAll(unitInfoList);
            } else {
                listViewArmyOneUnitInfo.getItems().add("Click on a unit to view its stats!");
                listViewArmyOneUnitInfo.getItems().add("\n");
                int unitTypes = listViewArmyOne.getItems().size()-2;
                for(int i = 0; i<unitTypes; i++){
                    listViewArmyOneUnitInfo.getItems().add("\uD83E\uDC78");
                }

            }

        });
        listViewArmyTwo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String unitTypeSelected = listViewArmyTwo.getSelectionModel().getSelectedItem();
            Unit unit = null;
            if(unitTypeSelected!=null){
                if(unitTypeSelected.contains("Infantry Unit")) unit = UnitFactory.createUnit("InfantryUnit", "Infantry Unit" , 100);
                else if (unitTypeSelected.contains("Ranged Unit")) unit = UnitFactory.createUnit("RangedUnit", "Ranged Unit" , 100);
                else if( unitTypeSelected.contains("Cavalry Unit")) unit = UnitFactory.createUnit("CavalryUnit", "Cavalry Unit" , 100);
                else if(unitTypeSelected.contains("Commander Unit"))unit = UnitFactory.createUnit("CommanderUnit", "Commander Unit" , 180);
            }
            listViewArmyTwoUnitInfo.getItems().clear();
            if(unit!=null){
                String unitInfo = unit.getClass().getSimpleName().replace("U", " U") + "\n" +
                        "Attack: " + unit.getAttack() + "\n" +
                        "Armor: " + unit.getArmor() +"\n" +
                        "Health " + unit.getHealth() + "\n" +
                        "Defensive bonus: " + unit.getResistanceBonus(Terrain.valueOf(terrainImages[terrain].toUpperCase())) +"\n" +
                        "Attack bonus: " + unit.getAttackBonus(Terrain.valueOf(terrainImages[terrain].toUpperCase()));
                String[] unitInfoList = unitInfo.split("\n");
                listViewArmyTwoUnitInfo.getItems().addAll(unitInfoList);
            }else {
                listViewArmyTwoUnitInfo.getItems().add("Click on a unit to view its stats!");
                listViewArmyTwoUnitInfo.getItems().add("\n");
                int unitTypes = listViewArmyTwo.getItems().size()-2;
                for(int i = 0; i<unitTypes; i++){
                    listViewArmyTwoUnitInfo.getItems().add("\uD83E\uDC78");
                }
            }
        });
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
     * method to get the image of the banner of an army, to be displayed in to top left / right for army one / army two
     * @param army the army
     * @return - the image
     */
    private Image getBannerImage(Army army){
        try{
            Banner bannerName = army.getBanner();
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/ArmyBanners/" + bannerName + ".jpg")));
        } catch (Exception e){
            e.printStackTrace();
        } return null;
    }

    /**
     * Method to start a battle between to armies, and go to the Battle Page.
     * Will read the info that has been entered and create a battle between to armies as long as:
     * - Two armies are selected
     * - The armies are not equal
     * After creating a battle and writing this to file,
     * it will take you to the battle page where the army will be displayed.
     * @param event the click
     */
    public void startBattle(ActionEvent event){
        String armyOne = comboBoxArmyOne.getValue();
        String armyTwo = comboBoxArmyTwo.getValue();
        String terrainName = labelTerrainName.getText().toUpperCase();
        Battle battle;
        Army army1 = armies.get(armyOne);
        Army army2 = armies.get(armyTwo);
        Terrain terrain = Terrain.valueOf(terrainName);
        if(armyOne !=null && armyTwo != null){
            if(!armyOne.equals(armyTwo)){
                try{
                    battle = new Battle(army1,army2,terrain);
                    FileManagement.writeBattleToFile(battle);
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(("BattlePage.fxml"))));
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch(Exception e){
                    e.printStackTrace();
                    AlertBox.displayAlertBox("Could not load this page, please try again");
                }
            }else{
                AlertBox.displayAlertBox("You must select two different armies");
            }
        }else{
            AlertBox.displayAlertBox("You must select two armies to start a battle");
        }
    }
}
