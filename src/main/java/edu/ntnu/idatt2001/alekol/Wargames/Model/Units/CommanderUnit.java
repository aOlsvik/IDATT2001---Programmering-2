package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

public class CommanderUnit extends CavalryUnit{

    /**
     * The constructor which creates a commander unit.
     * This sets boolean value hasAttacked to false, which means the unit has not attacked anyone yet.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @param attack - int. the attack value of a unit
     * @param armor  - int. the armor value of a unit.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public CommanderUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
    }

    /**
     * A constructor which creates a commander unit with fixed attack of 25 and armor of 15.
     * This sets boolean value hasAttacked to false, which means the unit has not attacked anyone yet.
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public CommanderUnit(String name,int health){
        super(name,health, 25,15);
    }

}
