package edu.ntnu.idatt2001.alekol.Wargames.Army;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ArmyTests {
    Army testArmy = new Army("Test army");
    

    private void addTestDataToOneArmy(Army army){
        Unit blueinfantry=UnitFactory.createUnit("InfantryUnit", "Footman", 100);
        Unit bluerange=UnitFactory.createUnit("RangedUnit", "Archer", 100);
        Unit bluecavalry=UnitFactory.createUnit("CavalryUnit", "Knight", 100);
        Unit bluecommander = UnitFactory.createUnit("CommanderUnit", "Mountain King", 100);
        army.add(blueinfantry);
        army.add(bluerange);
        army.add(bluecavalry);
        army.add(bluecommander);
    }
    private void addTestDataForFiltering(int n, int m, int o, int p){
        for(int i=0; i<n; i++){
            testArmy.add(UnitFactory.createUnit("InfantryUnit", "Infantry unit", 100));
        }
        for(int i=0; i<m; i++){
            testArmy.add(UnitFactory.createUnit("RangedUnit", "Ranged unit", 100));
        }
        for(int i=0; i<o; i++){
            testArmy.add(UnitFactory.createUnit("CavalryUnit", "Cavalry unit", 100));
        }
        for(int i=0; i<p; i++){
            testArmy.add(UnitFactory.createUnit("CommanderUnit", "Commander unit", 100));
        }
    }

    @Nested
    class ArmyConstructorTests{
        @Test
        @DisplayName("Checks if the constructor throws IllegalArgumentException when the name is blank")
        public void doesConstructorThrowExceptionsWhenNameIsBlank(){
            assertThrows(IllegalArgumentException.class,()-> new Army(""));
        }
        @Test
        @DisplayName("Checks if you can create an army from a list of units")
        public void instantiatingAnArmyWithAListOfUnits(){
            ArrayList<Unit> units = UnitFactory.createSeveralUnits("InfantryUnit", "Footman", 100,20);
            Army army = new Army("My Army", units);
            assertEquals(20, army.getAllUnits().size());
        }

    }
    @Nested
    class AddingUnits{
        @Test
        @DisplayName("Checks if units are added to an army")
        public void addNewUnitToArmy(){
            assertFalse(testArmy.hasUnits());
            addTestDataToOneArmy(testArmy);
            assertTrue(testArmy.hasUnits());


        }

        @Test
        @DisplayName("Checks if you can add an arraylist of units to an army")
        public void addAllUnitsToArmy(){
            ArrayList<Unit> units = UnitFactory.createSeveralUnits("InfantryUnit", "Footman", 100,20);
            assertFalse(testArmy.hasUnits());
            testArmy.addAll(units);
            assertEquals(units,testArmy.getAllUnits());
        }

        @Test
        @DisplayName("Checks if you can add a unit with health equal to zero")
        public void addingUnitWithHealthEqualToZero(){
            Unit healthEqualsOne = UnitFactory.createUnit("InfantryUnit", "Footman", 1);
            healthEqualsOne.attack(healthEqualsOne, Terrain.PLAINS);
            testArmy.add(healthEqualsOne);
            assertEquals(0, testArmy.getAllUnits().size());
        }
    }
    @Nested
    class RemovingUnits{
        @Test
        @DisplayName("Check if you can remove a specified unit from an army")
        public void removeSpecifiedUnitFromArmy(){
            Unit testA= UnitFactory.createUnit("InfantryUnit", "Footman", 100);
            testArmy.add(testA);
            assertTrue(testArmy.hasUnits());
            testArmy.remove(testA);
            assertFalse(testArmy.hasUnits());
        }
        @Test
        @DisplayName("Checks if then random method works, and that a random units is removed")
        public void removeRandomUnitFromArmy(){
            addTestDataToOneArmy(testArmy);
            final int ARMY1_SIZE_AFTER_ADDED_UNITS= testArmy.getAllUnits().size();
            assertTrue(testArmy.hasUnits()|| testArmy.getAllUnits().size()==4);
            testArmy.remove(testArmy.getRandom());
            assertTrue(ARMY1_SIZE_AFTER_ADDED_UNITS> testArmy.getAllUnits().size()|| testArmy.getAllUnits().size()==3);
        }
    }
    @Nested
    class FilteringUnits{
        @Test
        @DisplayName("Checks if we get the 5 infantry units after adding them, and other units")
        public void infantryUnitFiltering(){
            addTestDataForFiltering(5,5,5,5);
            assertEquals(5,testArmy.getInfantryUnits().size());
        }
        @Test
        @DisplayName("Checks if we get the 7 ranged units after adding them, and other units")
        public void rangedUnitFiltering(){
            addTestDataForFiltering(2,7,1,2);
            assertEquals(7,testArmy.getRangedUnits().size());
        }
        @Test
        @DisplayName("Checks if we get the 100 cavalry units after adding them, and other units")
        public void cavalryUnitFiltering(){
            addTestDataForFiltering(12,32,100,5);
            assertEquals(100,testArmy.getCavalryUnits().size());
        }
        @Test
        @DisplayName("Checks if we get the commander unit after adding one, and other units")
        public void commanderUnitFiltering(){
            addTestDataForFiltering(0,2,21,1);
            assertEquals(1,testArmy.getCommanderUnits().size());
        }
    }

}
