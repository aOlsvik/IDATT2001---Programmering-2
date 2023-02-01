package edu.ntnu.idatt2001.alekol.Wargames.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.CommanderUnit;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    CommanderUnit commanderUnit1;
    CommanderUnit commanderUnit2;

    @BeforeEach
    void newUnit(){
        commanderUnit1=(CommanderUnit) UnitFactory.createUnit("CommanderUnit", "Commander unit 1", 100);
        commanderUnit2 = new CommanderUnit("Commander unit 2", 100, 10,10);
    }


    @Test
    @DisplayName("Check if the super constructor throws exception when name is blank")
    public void createNewInfantryUnitWithBlankName(){
        assertThrows(IllegalArgumentException.class,()-> UnitFactory.createUnit("CommanderUnit", "", 100));
    }

    @Test
    @DisplayName("Check if the super constructor throws exception when health is zero")
    public void createNewInfantryUnitWithHealthEqualToZero(){
        assertThrows(IllegalArgumentException.class,()-> new CommanderUnit("Commander unit", 0, 10, 10));
    }

    @Test
    @DisplayName("Check if the super constructor throws exception when attack is lower than zero")
    public void createNewInfantryUnitWithAttackBelowZero(){
        assertThrows(IllegalArgumentException.class,()-> new CommanderUnit("Commander unit", 100, -1, 10));
    }

    @Test
    @DisplayName("Check if the super constructor throws exception when armor is lower than zero")
    public void createNewInfantryUnitWithArmorBelowZero(){
        assertThrows(IllegalArgumentException.class,()-> new CommanderUnit("Commander unit", 100, 10, -1));
    }

    @Test
    @DisplayName("Test to see if an attack works")
    public void attackLowerTheHealthOfEnemy(){
        //78 is commander unit2 (health + armor + resistance bonus) - opponent (attack + attack bonus)
        final int EXPECTED_HEALTH_AFTER_ATTACK=78;
        commanderUnit1.attack(commanderUnit2,Terrain.PLAINS);
        assertEquals(EXPECTED_HEALTH_AFTER_ATTACK,commanderUnit2.getHealth());
    }

    @Test
    @DisplayName("Check if an attack can drop the health below zero")
    public void healthCannotBeDroppedBelowZero(){
        final int EXPECTED_HEALTH=0;
        for(int i = 0; i<150; i++){
            commanderUnit1.attack(commanderUnit2,Terrain.HILL);
        }
        assertEquals(EXPECTED_HEALTH,commanderUnit2.getHealth());
    }
}

