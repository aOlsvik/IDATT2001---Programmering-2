package edu.ntnu.idatt2001.alekol.Wargames.Controller;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Banner;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * the Controller Class for the army creator page, where you can create your own army
 * The controller will handle buttons, some layout and functions, and the creating of the army
 */
public class ArmyCreator implements Initializable {

    private final int MAX_ARMY_SIZE = 1072;

    public TextField textFieldArmyName;
    public ComboBox<String> comboBoxUnitTypes;
    public Slider sliderUnitCount;
    public AnchorPane anchorPane;
    public Label labelUnitCount;
    public ListView<String> listViewArmyInfo;
    public ImageView imageUnit;
    public ImageView imageBanner;
    public Label labelUnitType;
    public VBox unitBox;
    public Label labelFileName;
    public Label labelArmySavedAs;
    public Button buttonCreateArmy;
    public Label labelUnitStats;
    public Button buttonClearArmy;
    public Circle circleHelp;
    public Label labelHelp;
    public Label labelBannerColorInBattle;
    public HBox hboxBannerSwitch;

    private ArrayList<String> armyNames;
    private final String[] unitTypes = {"Infantry Unit", "Ranged Unit", "Cavalry Unit", "Commander Unit"};
    private final String[] bannerNames = {"FORESTGREEN", "ROYALBLUE", "ORANGE", "CRIMSON"};
    private ArrayList<Unit> units = new ArrayList<>();
    private int banner=0;
    private String armyName;
    private final String helpText = """
            Help
            To create a new army, first enter a name and press the button 'Register army name'
            Then select a unit type and how many of that unit. Then press the button 'Add units' to add units
            The max amount of units are 1072. If you have added this, but want to change something, you can press the button 'Clear army'
            Each army also has a banner, which will decide their color in battle. Press the arrows to switch banner for your army
            Finally, when you are satisfied with your army, press 'Create army' to create it, and be able to use it in battle""";


    /**
     * Method to go back to the page where you select armies
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
     * Method to initialize all the methods that will run when the page opens.
     * This will for example set up the information that will be shown, add some listeners to different javafx elements,
     * and read the army names from the files.
     * The methods have been extracted to make the initialize method cleaner, and easier to explain the smaller methods
     * @param url -url
     * @param resourceBundle - resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
             armyNames = FileManagement.readArmyNamesFromFile();
        } catch (Exception e){
            e.printStackTrace();
        }
        initializeAndDisable();
        setUnitCount();
        circleSetUp();
        displayBanner();
        displayUnitImage();

    }

    /**
     * this method will be used in the initialize method, and will disable the box where you add units, until an army
     * name has been given. Will also add the types of units to the combobox for selection, and set the initial image
     * of the unit, which is "NoUnit"
     */
    private void initializeAndDisable(){
        unitBox.setDisable(true);
        comboBoxUnitTypes.getItems().addAll(unitTypes);
        String imagePath = "/Images/Units/NoUnit.png";
        imageUnit.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
    }

    /**
     * method which will be used in the initialize method
     * This adds a listener on a label under the slider for adding units. This makes it so that the label displays the
     * value of the slider. It will disable the slider if number of units added are 1072 or more, as this is the max
     * amount of units in an army (for displaying purposes)
     */
    private void setUnitCount() {
        sliderUnitCount.valueProperty().addListener((observableValue, oldValue, newValue) ->{
                labelUnitCount.setText(String.valueOf((int) sliderUnitCount.getValue()));
                if(units.size()>=MAX_ARMY_SIZE){
                    sliderUnitCount.setDisable(true);
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
     * This method displays the banner image. Initially, this wil be the Green banner
     * The banner can be changed with the arrow buttons under it. There are two methods for this.
     * This method simply uses an array and an index to display the current image
     */
    private void displayBanner(){
        try{
            String bannerName = bannerNames[banner];
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/ArmyBanners/" + bannerName + ".jpg")));
            imageBanner.setImage(image);
            labelBannerColorInBattle.setTextFill(Color.valueOf(bannerName));
            labelBannerColorInBattle.setText("Your army will be " + Banner.valueOf(bannerName).getName() + " in battle");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method for the right arrow, to increase the index of the current banner.
     * this will increase the index by one (unless it has reached the end of the array) and display the next banner
     */
    public void switchBannerRight() {
        if(banner==3) banner=0;
        else banner++;
        displayBanner();
    }

    /**
     * method for the left arrow, to decrease the index of the current banner.
     * this will decrease the index by one (unless it has reached the start of the array) and display the previous banner
     */
    public void switchBannerLeft() {
        if(banner==0) banner=3;
        else banner--;
        displayBanner();
    }

    /**
     * Method to update the unit image and stats based on what unit is selected in the combo box. This is also a listener
     * The method will also set the value of the slider to 0 each time to prevent bugs
     */
    private void displayUnitImage(){
        comboBoxUnitTypes.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            String unitType = comboBoxUnitTypes.getValue();
            if(units.size()==MAX_ARMY_SIZE){
                labelUnitCount.setFont(new Font("Helvetica", 12));
                labelUnitCount.setText("Max units have been added");
                return;
            }
            if(unitType==null) return;
            if(!unitType.isBlank()){
                sliderUnitCount.setDisable(false);
            }
            String imagePath= "/Images/Units/";
            switch (unitType) {
                case "Infantry Unit" -> {
                    imagePath += "InfantryUnit.jpg";
                    sliderUnitCount.setMax( MAX_ARMY_SIZE - units.size());
                    labelUnitCount.setText("0");
                    sliderUnitCount.setValue(0);
                }
                case "Ranged Unit" -> {
                    imagePath += "RangedUnit.jpg";
                    sliderUnitCount.setMax( MAX_ARMY_SIZE - units.size());
                    labelUnitCount.setText("0");
                    sliderUnitCount.setValue(0);
                }
                case "Cavalry Unit" -> {
                    imagePath += "CavalryUnit.jpg";
                    sliderUnitCount.setMax( MAX_ARMY_SIZE - units.size());
                    labelUnitCount.setText("0");
                    sliderUnitCount.setValue(0);
                }
                case "Commander Unit" -> {
                    imagePath += "CommanderUnit.jpg";
                    sliderUnitCount.setMax(Math.min(MAX_ARMY_SIZE-units.size(), (5-units.stream().filter(s-> s instanceof CommanderUnit).count())));
                    labelUnitCount.setText("0");
                    sliderUnitCount.setValue(0);
                }
                default -> {
                    imagePath+="NoUnit.png";
                    sliderUnitCount.setDisable(true);
                }
            }
            displayImage(imagePath);
            labelUnitStats.setText(getUnitStats(unitType));
        });
    }

    /**
     * Method used to get the stats of a unit based on its type.
     * Will return the armor, attack and health.
     * Does not display bonuses, as this is a "hidden feature" to make the units interactions unique, and it also
     * varies based on the terrain.
     * @param unitType - the type of unit as a string
     * @return - the string with the information
     */
    private String getUnitStats(String unitType){
        Unit unit=null;
        switch(unitType){
            case "Infantry Unit" -> unit = UnitFactory.createUnit("InfantryUnit", "Infantry Unit" , 100);
            case "Ranged Unit" -> unit = UnitFactory.createUnit("RangedUnit", "Infantry Unit" , 100);
            case "Cavalry Unit" -> unit = UnitFactory.createUnit("CavalryUnit", "Infantry Unit" , 100);
            case "Commander Unit" -> unit = UnitFactory.createUnit("CommanderUnit", "Infantry Unit" , 180);
        }
        if(unit==null) return "";
        return  "Attack: " + unit.getAttack() + "\n" +
                "Armor: " + unit.getArmor() +"\n" +
                "Health: " + unit.getHealth() + "\n";
    }


    /**
     * Method used when the combobox selection is a unit. this will display the image of the unit.
     * will also set the text over the image to the name of the unit
     * @param path the path of the image to display
     */
    private void displayImage(String path){
        imageUnit.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        labelUnitType.setText(comboBoxUnitTypes.getValue());
        sliderUnitCount.setValue(0);
    }

    /**
     * The method which will activate when you press 'Register new army' button.
     * This will set the name of the army, unless it already exists, is blank or has more than 10 characters.
     * It will also set up some features for the rest of the program, such as enabling other buttons and setting
     * the visibility of labels.
     */
    public void setArmyName() {
        String suggestedName = textFieldArmyName.getText();
        if(!suggestedName.isBlank()){
            if(suggestedName.length()<=10){
                if (armyNames.stream().noneMatch(s-> s.equalsIgnoreCase(suggestedName.replaceAll("\\s","")))) {
                    armyName = suggestedName;
                    labelFileName.setText(armyName.replaceAll("\\s","")+".csv");
                    labelFileName.setVisible(true);
                    labelArmySavedAs.setVisible(true);
                    unitBox.setDisable(false);
                    buttonCreateArmy.setDisable(false);
                    addArmyInfoToListView();
                    buttonClearArmy.setDisable(false);
                    imageUnit.setVisible(true);
                    hboxBannerSwitch.setVisible(true);
                    imageBanner.setVisible(true);
                    labelBannerColorInBattle.setVisible(true);
                } else AlertBox.displayAlertBox("This army name already exists");
            } else AlertBox.displayAlertBox("The name of an army cant be longer than 10 characters");
        } else AlertBox.displayAlertBox("You must enter a name for your army");
    }

    /**
     * The method that is activated when 'Add units' is pressed.
     * As long as a unit type is selected, and the number of units is more than 0, units will be added
     * This will add 'x' amount of units of 'y' type. Health will be set to 100, and the name will be the same as the type
     * After units have been added, the box is reset in a way, to prevent bugs
     */
    public void addUnits() {
        if(comboBoxUnitTypes.getValue() == null){
            AlertBox.displayAlertBox("You must select a unit type");
            return;
        }
        String unitType = comboBoxUnitTypes.getValue().replaceAll(" ", "");
        String unitName = unitType.replaceAll("U", " U");
        int unitHealth = 100;
        String unitInput= labelUnitCount.getText();

        if(!unitInput.isBlank() && !unitInput.equals("0")){
            int unitCount = Integer.parseInt(unitInput);
            units.addAll(UnitFactory.createSeveralUnits(unitType, unitName, unitHealth, unitCount));
            sortUnits();
            addArmyInfoToListView();
            comboBoxUnitTypes.getSelectionModel().clearSelection();
            sliderUnitCount.setDisable(true);
            labelUnitCount.setText("");
            displayImage("/Images/Units/NoUnit.png");
            labelUnitStats.setText("");
        }
        else{
            AlertBox.displayAlertBox("You must add 1 or more units");
        }
    }

    private void sortUnits(){
        ArrayList<Unit> newUnits = new ArrayList<>();
        units.stream().filter(s-> s instanceof InfantryUnit).forEach(newUnits::add);
        units.stream().filter(s-> s instanceof RangedUnit).forEach(newUnits::add);
        units.stream().filter(s-> s instanceof CavalryUnit && !(s instanceof CommanderUnit)).forEach(newUnits::add);
        units.stream().filter(s-> s instanceof CommanderUnit).forEach(newUnits::add);
        units=newUnits;
    }

    /**
     * Method used to get the info about an army. This contains:
     *  - Army name
     *  - Number of Infantry units
     *  - Number of Ranged units
     *  - Number of Cavalry units
     *  - Number of Commander units
     *  - Total number of units
     * @return the info about the army as a string
     */
    private String getArmyInfo(){
        StringBuilder armyInfo = new StringBuilder(armyName).append("\n");
        if(units.stream().anyMatch(s-> s instanceof InfantryUnit)) {
            armyInfo.append(units.stream().filter(s-> s instanceof InfantryUnit).count()).
                    append(" x Infantry Units\n");
        }
        if(units.stream().anyMatch(s -> s instanceof RangedUnit)) {
            armyInfo.append(units.stream().filter(s-> s instanceof RangedUnit).count()).
                    append(" x Ranged Units\n");
        }
        if(units.stream().anyMatch(s -> s.getClass() == CavalryUnit.class)) {
            armyInfo.append(units.stream().filter(s-> s.getClass() == CavalryUnit.class).count()).
                    append(" x Cavalry Units\n");
        }
        if(units.stream().anyMatch(s -> s instanceof CommanderUnit)) {
            armyInfo.append(units.stream().filter(s-> s instanceof CommanderUnit).count()).
                    append(" x Commander Units\n");
        }
        if(!units.isEmpty()) armyInfo.append("Total number of units: ").append(units.size());
        return armyInfo.toString();
    }

    /**
     * This method will add the army info, from the previous method, to the listview.
     */
    private void addArmyInfoToListView() {
        listViewArmyInfo.getItems().clear();
        String[] unitInfo = getArmyInfo().split("\n");
        listViewArmyInfo.getItems().addAll(unitInfo);
    }

    /**
     * Method to clear an army if you have added the wrong amount of units, or is not satisfied.
     * This will basically reset the page to default, so that you can start the process anew.
     */
    public void clearArmy(){
        units= new ArrayList<>();
        listViewArmyInfo.getItems().clear();
        unitBox.setDisable(true);
        labelFileName.setVisible(false);
        buttonCreateArmy.setDisable(true);
        labelArmySavedAs.setVisible(false);
        labelUnitStats.setVisible(false);
        buttonClearArmy.setDisable(true);
        labelUnitCount.setText("");
        sliderUnitCount.setValue(0);
        labelUnitCount.setFont(new Font("Helvetica", 22));
        comboBoxUnitTypes.getSelectionModel().clearSelection();
        textFieldArmyName.clear();
        imageUnit.setVisible(false);
        hboxBannerSwitch.setVisible(false);
        imageBanner.setVisible(false);
        labelBannerColorInBattle.setVisible(false);
    }
    /**
     * Method to create an army, based on the name, banner, and units added.
     * Will reset the page after adding, and display a message that the army was created
     */
    public void createArmy(){
        Banner bannerAdd = Banner.valueOf(bannerNames[banner]);
        if(!units.isEmpty()){
            try {
                Army army = new Army(armyName,units, bannerAdd);
                FileManagement.writeArmyToFile(army);
                units=new ArrayList<>();
            } catch (IOException e) {
                AlertBox.displayAlertBox("Could not create this army, please try again");
            }
            listViewArmyInfo.getItems().clear();
            textFieldArmyName.clear();
            labelFileName.setText("Army was created!");
            labelArmySavedAs.setVisible(false);
            comboBoxUnitTypes.getSelectionModel().clearSelection();
            labelUnitCount.setFont(new Font("Helvetica", 22));
        } else AlertBox.displayAlertBox("You must add units to your army before creating it!");
    }


}
