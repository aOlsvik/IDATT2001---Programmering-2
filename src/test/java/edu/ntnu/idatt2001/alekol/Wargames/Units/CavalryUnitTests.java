package edu.ntnu.idatt2001.alekol.Wargames.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.CavalryUnit;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The testing for cavalry units
 * This is only specific testing, as other methods will be covered directly / indirectly in other unit tests
 * Resistance bonuses will be tested in CommanderUnitTests, as they use the same method
 */
public class CavalryUnitTests {

    private CavalryUnit cavalryUnit;

    @BeforeEach
    void newUnit(){
        cavalryUnit =(CavalryUnit) UnitFactory.createUnit("CavalryUnit", "Cavalry unit", 100);
    }

    @Test
    @DisplayName("Test for the correct expected health of a cavalry unit")
    public void correctHealthForCavalryUnit(){
        final int EXPECTED_HEALTH=100;
        assertEquals(EXPECTED_HEALTH, cavalryUnit.getHealth());
    }

    @Test
    @DisplayName("Test for the correct expected health of a cavalry unit")
    public void correctAttackForCavalryUnit(){
        final int EXPECTED_ATTACK=20;
        assertEquals(EXPECTED_ATTACK, cavalryUnit.getAttack());
    }

    @Test
    @DisplayName("Test for the correct expected health of a cavalry unit")
    public void correctArmorForCavalryUnit(){
        final int EXPECTED_ARMOR=12;
        assertEquals(EXPECTED_ARMOR, cavalryUnit.getArmor());
    }
}
