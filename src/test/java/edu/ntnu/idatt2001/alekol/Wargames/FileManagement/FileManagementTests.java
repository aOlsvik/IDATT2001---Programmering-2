package edu.ntnu.idatt2001.alekol.Wargames.FileManagement;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Banner;
import edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement.FileManagement;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class FileManagementTests {

    Army army = new Army("Human army");

    private void addTestDataToTheArmy(){
        Unit blueInfantry=UnitFactory.createUnit("InfantryUnit", "Footman", 100);
        Unit blueRanged=UnitFactory.createUnit("RangedUnit", "Archer", 100);
        Unit blueCavalry= UnitFactory.createUnit("CavalryUnit", "Knight", 100);
        Unit blueCommander = UnitFactory.createUnit("CommanderUnit", "Mountain King", 180);
        army.add(blueInfantry);
        army.add(blueRanged);
        army.add(blueCavalry);
        army.add(blueCommander);
    }
    private void removeTestData(){
        army = new Army("Human army");
    }

    @Nested
    class FileWritingTests{

        @BeforeEach
        private void addData(){
            addTestDataToTheArmy();
        }
        @AfterEach
        private void removeData(){
            removeTestData();
        }


        @Test
        @DisplayName("Test to see if the file writing works fine when input is wrong")
        public void writingToFileDoesNotThrowIOExceptionWhenInputIsCorrect(){
            try{
                FileManagement.writeArmyToFile(new File("src/test/resources/testFile.csv"), army);
            } catch (Exception e ){
                fail();
            }
        }


        @Test
        @DisplayName("Testing if the writeToFile will throw IOException when file is a txt file")
        public void writingToFileWithNameNotEndingWithCsvWillThrowIOException(){
            assertThrows(IOException.class,()->FileManagement.writeArmyToFile(new File("/src/test/resources/wrongFileName.txt"), army));
        }
    }

    @Nested
    class FileReadingTests{
        @BeforeEach
        private void addData(){
            addTestDataToTheArmy();
        }

        @AfterEach
        private void removeData(){
            removeTestData();
        }

        @Test
        @DisplayName("Testing if a file can be read correctly")
        public void testThatAFileCanBeReadWhenItExists(){
            try{
                Army readArmy =FileManagement.readArmyFromFile(new File("src/test/resources/testFile.csv"));
                assertEquals(army,readArmy);
            } catch(Exception e){
                e.printStackTrace();
                fail();
            }
        }
        @Test
        @DisplayName("Testing if a file can be read correctly when it has a banner")
        public void testThatAFileCanBeReadWithBannerWhenItExists(){
            Army armyWithBanner = new Army("Human army", Banner.ORANGE);
            Unit blueInfantry=UnitFactory.createUnit("InfantryUnit", "Footman", 100);
            Unit blueRanged=UnitFactory.createUnit("RangedUnit", "Archer", 100);
            Unit blueCavalry= UnitFactory.createUnit("CavalryUnit", "Knight", 100);
            Unit blueCommander = UnitFactory.createUnit("CommanderUnit", "Mountain King", 180);
            armyWithBanner.add(blueInfantry);
            armyWithBanner.add(blueRanged);
            armyWithBanner.add(blueCavalry);
            armyWithBanner.add(blueCommander);
            try{
                Army readArmy =FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithBanner.csv"));
                assertEquals(armyWithBanner,readArmy);
            } catch(Exception e){
                e.printStackTrace();
                fail();
            }
        }

        @Test
        @DisplayName("Tests to check that readArmyFromFile throws IOException when a .txt file is used")
        public void testThatAFileCannotEndWithSomethingElseThatCsv(){
            assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithWrongName.txt")));
        }

        @Test
        @DisplayName("Tests to check that readArmyFromFile throws IllegalArgumentException if army name is empty")
        public void testThatAFileCannotReadIfArmyNameIsEmpty(){
            assertThrows(IllegalArgumentException.class, () -> FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithEmptyName.csv")));
        }

        @Test
        @DisplayName("Tests to check that readArmyFromFile throws IllegalArgumentException if units class type is not supported")
        public void testThatAFileCannotReadIfTheClassOfAUnitIsWrong(){
            assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithWrongUnitClass.csv")));
        }

        @Test
        @DisplayName("Tests to check that readArmyFromFile throws IllegalArgumentException if the health of a unit is not written as an integer")
        public void testThatAFileCannotReadIfTheHealthOfAUnitIsNotAnInteger(){
            assertThrows(NumberFormatException.class, () -> FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithWrongHealthInfo.csv")));
        }

        @Test
        @DisplayName("Tests to check that readArmyFromFile throws IllegalArgumentException if the written unit only has two fields")
        public void testThatAFileCannotReadIfTheUnitInfoOnlyHasTwoFields(){
            assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/test/resources/testFileWithLessThanThreeFields.csv")));
        }
    }
}
