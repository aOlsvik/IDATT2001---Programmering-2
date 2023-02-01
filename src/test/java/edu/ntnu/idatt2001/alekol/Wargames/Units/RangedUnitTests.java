package edu.ntnu.idatt2001.alekol.Wargames.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.RangedUnit;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The testing for ranged units
 * This is only specific testing, as other methods will be covered directly / indirectly in other unit tests
 */
public class RangedUnitTests {

    private RangedUnit rangedUnit;

    @BeforeEach
    void newUnit(){
        rangedUnit = (RangedUnit) UnitFactory.createUnit("RangedUnit", "Ranged unit 1", 100);
    }

    @Test
    @DisplayName("Check that the attack bonus is correct when unaffected by terrain, such as plains")
    public void correctAttackBonusForRangedUnitWhenUnaffectedByTerrain(){
        final int EXPECTED_ATTACK_BONUS=3;
        assertEquals(EXPECTED_ATTACK_BONUS, rangedUnit.getAttackBonus(Terrain.PLAINS));
    }
    @Test
    @DisplayName("Check that the attack bonus is correct when affected by terrain hill")
    public void correctAttackBonusForRangedUnitWhenAffectedByTerrainHill(){
        final int EXPECTED_ATTACK_BONUS_ON_HILL=5;
        assertEquals(EXPECTED_ATTACK_BONUS_ON_HILL, rangedUnit.getAttackBonus(Terrain.HILL));
    }
    @Test
    @DisplayName("Check that the attack bonus is correct when affected by terrain forest")
    public void correctAttackBonusForRangedUnitWhenAffectedByTerrainForest(){
        final int EXPECTED_ATTACK_BONUS_IN_FOREST=4;
        assertEquals(EXPECTED_ATTACK_BONUS_IN_FOREST, rangedUnit.getAttackBonus(Terrain.FOREST));
    }

    @Test
    @DisplayName("Test for the resistance bonus of a ranged unit when it is attacked for the first time")
    public void correctResistanceBonusForRangedWhenAttackedFirstTime(){
        final int EXPECTED_RESISTANCE_BONUS_AFTER_FIRST_ATTACK=6;
        assertEquals(EXPECTED_RESISTANCE_BONUS_AFTER_FIRST_ATTACK, rangedUnit.getResistanceBonus(Terrain.PLAINS));
    }
    @Test
    @DisplayName("Test for the resistance bonus of a ranged unit when it is attacked for the second time")
    public void correctResistanceBonusForRangedWhenAttackedSecondTime(){
        final int EXPECTED_RESISTANCE_BONUS_AFTER_SECOND_ATTACK=4;
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        assertEquals(EXPECTED_RESISTANCE_BONUS_AFTER_SECOND_ATTACK, rangedUnit.getResistanceBonus(Terrain.PLAINS));
    }
    @Test
    @DisplayName("Test for the resistance bonus of a ranged unit when it is attacked for the third time")
    public void correctResistanceBonusForRangedWhenAttackedThirdTime(){
        final int EXPECTED_RESISTANCE_BONUS_AFTER_THIRD_ATTACK=2;
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        assertEquals(EXPECTED_RESISTANCE_BONUS_AFTER_THIRD_ATTACK, rangedUnit.getResistanceBonus(Terrain.PLAINS));
    }
    @Test
    @DisplayName("Test for the resistance bonus of a ranged unit when it is attacked for the fourth time / rest of its life")
    public void correctResistanceBonusForRangedWhenAttackedRestOfLifetime(){
        final int EXPECTED_RESISTANCE_BONUS_AFTER_FOURTH_ATTACK=2;
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        rangedUnit.getResistanceBonus(Terrain.PLAINS);
        assertEquals(EXPECTED_RESISTANCE_BONUS_AFTER_FOURTH_ATTACK, rangedUnit.getResistanceBonus(Terrain.PLAINS));
    }
}
