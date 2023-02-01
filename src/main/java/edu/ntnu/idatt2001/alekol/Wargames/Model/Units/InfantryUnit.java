package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;

public class InfantryUnit extends Unit{

    /**
     * A constructor which creates a unit of type InfantryUnit.
     * @param name   - String, the name of the Unit.
     * @param health - int. Has to be greater than zero.
     * @param attack - int. the attack value of a unit
     * @param armor  - int. the armor value of a unit.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public InfantryUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
    }

    /**
     * A constructor which creates an infantry unit with initial attack and armor of 15 and 10.
     * @param name - String, name of the unit
     * @param health - int. Has to be greater than zero.
     */
    public InfantryUnit(String name, int health){
        super(name,health,15,10);
    }

    /**
     * Method to get the attack bonus of an infantry unit
     * @param terrain the terrain where the attack happened
     * @return - int. the attack bonus of an Infantry Unit is 2
     *  or - 4 if the terrain is 'FOREST'
     */
    @Override
    public int getAttackBonus(Terrain terrain) {
        if(terrain.equals(Terrain.FOREST)) return 4;
        return 2;
    }

    /**
     * Method to get the resistance bonus of an infantry unit
     * @param terrain the terrain where the attack happened
     * @return - int. the resistance bonus of an Infantry Unit is 1
     * or - 3 if the terrain is 'FOREST'
     */
    @Override
    public int getResistanceBonus(Terrain terrain) {
        if(terrain.equals(Terrain.FOREST)) return 3;
        return 1;
    }




}
