package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;

public class RangedUnit extends Unit{

    private int numberOfAttacksReceived;
    /**
     * A constructor which creates a ranged unit. This will include a counter to keep track of how many attacks
     * the ranged unit has received.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @param attack - int. the attack value of a unit
     * @param armor  - int. the armor value of a unit.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public RangedUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
        this.numberOfAttacksReceived=0;
    }

    /**
     * A constructor which creates a ranged unit with fixed attack of 15 and armor of 8.
     * This constructor will include a counter to keep track of how many attacks the ranged unit has received.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public RangedUnit(String name, int health){
        super(name,health,15,8);
        this.numberOfAttacksReceived=0;
    }

    /**
     * Method to get the attack bonus of a ranged unit
     * @param terrain the terrain where the attack happened
     * @return - int. The attack bonus of a ranged unit is 3
     * or - 5 if the terrain is 'HILL'
     * or - 4 if the terrain is 'FOREST'
     */
    @Override
    public int getAttackBonus(Terrain terrain) {
        if(terrain.equals(Terrain.HILL)) return 5;
        else if(terrain.equals(Terrain.FOREST)) return 4;
        return 3;
    }

    /**
     * Method to get the resistance of a ranged unit
     * @param terrain the terrain where the attack happened - will not affect resistance of ranged unit
     * @return - int. The resistance bonus of a ranged unit is based on the assumed distance to an enemy
     *                and will start at 6, then go down to 4, then it's 2 for the rest of its lifetime.
     */
    @Override
    public int getResistanceBonus(Terrain terrain) {
        int resistanceBonus=2;
        if(numberOfAttacksReceived>=2){
            resistanceBonus+=0;
        }
        else if(numberOfAttacksReceived==1){
            resistanceBonus=4;
        }
        else if(numberOfAttacksReceived==0){
            resistanceBonus=6;
        }
        numberOfAttacksReceived++;
        return resistanceBonus;
    }
}
