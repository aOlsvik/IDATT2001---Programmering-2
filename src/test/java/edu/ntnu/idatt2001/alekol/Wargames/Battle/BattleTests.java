package edu.ntnu.idatt2001.alekol.Wargames.Battle;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Battle.Battle;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTests {

    Army armyOne = new Army("Army One");
    Army armyTwo = new Army("Army Two");

    private void addTestDataToBothArmies(){
        Unit blueInfantry= UnitFactory.createUnit("InfantryUnit", "Footman", 100);
        Unit blueRanged= UnitFactory.createUnit("RangedUnit", "Archer", 100);
        Unit blueCavalry= UnitFactory.createUnit("CavalryUnit", "Knight", 100);
        Unit blueCommander = UnitFactory.createUnit("CommanderUnit", "Mountain King", 180);
        armyOne.add(blueInfantry);
        armyOne.add(blueRanged);
        armyOne.add(blueCavalry);
        armyOne.add(blueCommander);
        Unit redInfantry= UnitFactory.createUnit("InfantryUnit", "Grunt", 100);
        Unit redRanged= UnitFactory.createUnit("RangedUnit", "Spearman", 100);
        Unit redCavalry= UnitFactory.createUnit("CavalryUnit", "Raider", 100);
        Unit redCommander = UnitFactory.createUnit("CommanderUnit", "Gul'dan", 180);
        armyTwo.add(redInfantry);
        armyTwo.add(redRanged);
        armyTwo.add(redCavalry);
        armyTwo.add(redCommander);
    }
    @Nested
    class SimulationTest{
        @Test
        @DisplayName("Constructor check")
        public void checksTheConstructorParameters(){
            Army armyWithNoUnits = new Army("Army with no units");
            assertThrows(IllegalArgumentException.class,()-> new Battle(armyWithNoUnits, armyTwo,Terrain.FOREST));
        }
        @Test
        @DisplayName("Checks the simulations of a battle")
        public void simulateBattle(){
            addTestDataToBothArmies();
            assertFalse(!armyOne.hasUnits() || !armyTwo.hasUnits());
            assertEquals(armyOne.getAllUnits().size(), armyTwo.getAllUnits().size());
            Battle battleSimulation = new Battle(armyOne,armyTwo,Terrain.HILL);
            battleSimulation.simulate();
            assertTrue(armyOne.getAllUnits().size()!=armyTwo.getAllUnits().size());

        }
        @Test
        @DisplayName("Tests if the army with fewer units will start")
        public void willTheArmyWithFewerUnitsStartTheBattle(){
            final int NUMBER_OF_SIMULATIONS=100;
            int[] tracker = new int[2];
            addTestDataToBothArmies();
            armyOne.add(UnitFactory.createUnit("InfantryUnit", "Footman", 100));
            Battle battleSimulation = new Battle(armyOne,armyTwo,Terrain.HILL);
            addTestDataToBothArmies();
            for (int i=0; i<NUMBER_OF_SIMULATIONS; i++){
                tracker[battleSimulation.whoStarts()]+=1;
            }
            assertEquals(100, tracker[1]);
        }
        @Test
        @DisplayName("Test if an winner is declared")
        public void willAWinnerBeDeclared(){
            addTestDataToBothArmies();
            Battle testBattle = new Battle(armyOne,armyTwo,Terrain.FOREST);
            assertTrue(testBattle.simulate().equals(armyOne) || testBattle.simulate().equals(armyTwo));
        }
    }
}
