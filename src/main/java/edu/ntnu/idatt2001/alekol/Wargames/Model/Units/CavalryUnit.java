package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;

public class CavalryUnit extends Unit{
    private boolean hasAttacked;

    /**
     * The constructor which creates a cavalry unit.
     * This sets boolean value hasAttacked to false, which means the unit has not attacked anyone yet.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @param attack - int. the attack value of a unit
     * @param armor  - int. the armor value of a unit.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public CavalryUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
        this.hasAttacked=false;

    }

    /**
     * A constructor which creates a cavalry unit with fixed attack of 20 and armor of 12.
     * This sets boolean value hasAttacked to false, which means the unit has not attacked anyone yet.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public CavalryUnit(String name,int health){
        super(name,health, 20,12);
        this.hasAttacked=false;
    }

    /**
     * Method to get the attack bonus of a cavalry unit
     * @param terrain - the terrain where the attack happened
     * @return - will return an int value. 6 if the unit hasn't attacked, and 2 if it has attacked already
     *  - will be +2 if the terrain is 'PLAINS'
     */
    @Override
    public int getAttackBonus(Terrain terrain) {
        int attackBonus = 2;
        if(!hasAttacked){
            attackBonus = 6;
            hasAttacked=true;
        }
        if(terrain.equals(Terrain.PLAINS)) attackBonus+=2;
        return attackBonus;
    }

    /**
     * Method to get the resistance of a cavalry unit
     * @param terrain the terrain where the attack happened
     * @return - int = 1
     *  or - 0 if the terrain is 'FOREST'
     */
    @Override
    public int getResistanceBonus(Terrain terrain){
        if(terrain.equals(Terrain.FOREST)) return 0;
        return 1;
    }

}
