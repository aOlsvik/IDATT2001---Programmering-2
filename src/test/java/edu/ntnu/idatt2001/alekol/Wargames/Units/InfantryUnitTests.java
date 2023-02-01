package edu.ntnu.idatt2001.alekol.Wargames.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.InfantryUnit;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The testing for infantry units
 * This is only specific testing, as other methods will be covered directly / indirectly in other unit tests
 */
public class InfantryUnitTests {

    private InfantryUnit infantryUnit;

    @BeforeEach
    void newUnit(){
        infantryUnit = (InfantryUnit) UnitFactory.createUnit("InfantryUnit", "Infantry unit", 100);
    }

    @Test
    @DisplayName("Check if the attack bonus of an infantry unit is correct when unaffected by terrain")
    public void correctAttackBonusForInfantryUnitWhenUnaffectedByTerrain(){
        final int EXPECTED_ATTACK_BONUS=2;
        assertEquals(EXPECTED_ATTACK_BONUS, infantryUnit.getAttackBonus(Terrain.HILL));
    }

    @Test
    @DisplayName("Test that the resistance bonus of an infantry unit is correct when unaffected by terrain")
    public void correctResistanceBonusForInfantryUnitWhenUnaffectedByTerrain(){
        final int EXPECTED_RESISTANCE_BONUS=1;
        assertEquals(EXPECTED_RESISTANCE_BONUS, infantryUnit.getResistanceBonus(Terrain.HILL));
    }

    @Test
    @DisplayName("Test that the attack bonus of an infantry unit is correct when the terrain is Forest")
    public void correctAttackBonusForInfantryUnitWhenTerrainIsForest(){
        final int EXPECTED_ATTACK_BONUS_IN_FOREST=4;
        assertEquals(EXPECTED_ATTACK_BONUS_IN_FOREST, infantryUnit.getAttackBonus(Terrain.FOREST));
    }

    @Test
    @DisplayName("Test that the resistance bonus of an infantry unit is correct when the terrain is forest")
    public void correctResistanceBonusForInfantryUnitWhenTerrainIsForest(){
        final int EXPECTED_RESISTANCE_BONUS_IN_FOREST=3;
        assertEquals(EXPECTED_RESISTANCE_BONUS_IN_FOREST, infantryUnit.getResistanceBonus(Terrain.FOREST));
    }
}
