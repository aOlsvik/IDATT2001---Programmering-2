package edu.ntnu.idatt2001.alekol.Wargames.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.CommanderUnit;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The testing for Commander units
 * This is only specific testing, as other methods will be covered directly / indirectly in other unit tests
 */
public class CommanderUnitTests {

    private CommanderUnit commanderUnit1;

    @BeforeEach
    void newUnit(){
        commanderUnit1=(CommanderUnit) UnitFactory.createUnit("CommanderUnit", "Commander unit", 100);
    }

    @Test
    @DisplayName("Test for the correct expected health of a commander unit")
    public void correctHealthForCommanderUnit(){
        final int EXPECTED_HEALTH=100;
        assertEquals(EXPECTED_HEALTH,commanderUnit1.getHealth());
    }
    @Test
    @DisplayName("Test for the correct expected health of a commander unit")
    public void correctAttackForCommanderUnit(){
        final int EXPECTED_ATTACK=25;
        assertEquals(EXPECTED_ATTACK,commanderUnit1.getAttack());
    }
    @Test
    @DisplayName("Test for the correct expected health of a commander unit")
    public void correctArmorForCommanderUnit(){
        final int EXPECTED_ARMOR=15;
        assertEquals(EXPECTED_ARMOR,commanderUnit1.getArmor());
    }

    @Test
    @DisplayName("Test if attack bonus is correct when charging and unaffected by terrain")
    public void correctAttackBonusWhenChargingUnaffectedByTerrain(){
        final int EXPECTED_ATTACK_BONUS_WHEN_CHARGING_IN_HILL=6;
        assertEquals(EXPECTED_ATTACK_BONUS_WHEN_CHARGING_IN_HILL,commanderUnit1.getAttackBonus(Terrain.HILL));
    }

    @Test
    @DisplayName("Test if attack bonus is correct when charging and unaffected by terrain")
    public void correctResistanceBonusWhenChargingAffectedByTerrain() {
        final int EXPECTED_ATTACK_BONUS_IN_PLAINS_WHEN_CHARGING = 8;
        assertEquals(EXPECTED_ATTACK_BONUS_IN_PLAINS_WHEN_CHARGING, commanderUnit1.getAttackBonus(Terrain.PLAINS));
    }

    @Test
    @DisplayName("Test if resistance bonus is correct when unaffected by terrain")
    public void correctResistanceBonusUnaffectedByTerrain(){
        final int EXPECTED_RESISTANCE_BONUS_IN_HILL=1;
        assertEquals(EXPECTED_RESISTANCE_BONUS_IN_HILL,commanderUnit1.getResistanceBonus(Terrain.HILL));
    }

    @Test
    @DisplayName("Test if resistance bonus is correct when affected by terrain")
    public void correctResistanceBonusWhenAffectedByTerrain(){
        final int EXPECTED_RESISTANCE_BONUS_IN_FOREST=0;
        assertEquals(EXPECTED_RESISTANCE_BONUS_IN_FOREST,commanderUnit1.getResistanceBonus(Terrain.FOREST));
    }

    @Test
    @DisplayName("Test that the attack bonus of a commander unit updates after charging when unaffected by terrain")
    public void checkChargeBonusAfterAttackWhenUnaffectedByTerrain() {
        final int ATTACK_BONUS_AFTER_CHARGE=2;
        commanderUnit1.getAttackBonus(Terrain.HILL);
        assertEquals(ATTACK_BONUS_AFTER_CHARGE, commanderUnit1.getAttackBonus(Terrain.HILL));
    }

    @Test
    @DisplayName("Test that the attack bonus of a commander unit updates after charging when affected by terrain")
    public void checkAttackBonusAfterChargingWhenAffectedByTerrain() {
        final int ATTACK_BONUS_AFTER_CHARGE=4;
        commanderUnit1.getAttackBonus(Terrain.PLAINS);
        assertEquals(ATTACK_BONUS_AFTER_CHARGE, commanderUnit1.getAttackBonus(Terrain.PLAINS));
    }
}
