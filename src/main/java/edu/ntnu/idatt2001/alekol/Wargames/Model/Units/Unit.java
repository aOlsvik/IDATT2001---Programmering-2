package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;

import java.util.Objects;

/**
 * The type Unit.
 */
public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;
    private int x;
    private int y;

    /**
     * The constructor which creates a unit. This belongs to an abstracts class, so this constructor won't be used
     * directly. Other classes will use this constructor as a guideline.
     *
     * @param name   - String, name of the unit
     * @param health - int. Has to be greater than zero.
     * @param attack - int. the attack value of a unit
     * @param armor  - int. the armor value of a unit.
     * @throws IllegalArgumentException - if health, attack, or armor is lower than zero, or the name is blank
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if(health<=0) throw new IllegalArgumentException("Health has to be greater than zero");
        if(attack<0) throw new IllegalArgumentException("Attack has to be greater than zero");
        if(armor<0) throw new IllegalArgumentException("Armor has to be greater than zero");
        if(name.isBlank()) throw new IllegalArgumentException("The unit has to have a name");
        this.name = name.trim();
        this.setHealth(health);
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * The method to attack another unit.The new health of a unit is based on their current health,
     * armor and resistance bonus, and the attackers attack and attack bonus.
     * NB! if the attack and attack bonus are lower than the armor and resistance bonus, the unit won't take any damage
     * This is to prevent a unit "healing" from being attacked. That would not make sense.
     * @param opponent - the unit who should be attacked.
     * @param terrain  the terrain where the attack happened
     */
    public void attack(Unit opponent, Terrain terrain){
        final int opponentHealth= opponent.getHealth();
        opponent.setHealth(opponent.getHealth()-(this.getAttack()+this.getAttackBonus(terrain))+
                (opponent.getArmor()+opponent.getResistanceBonus(terrain)));
        if(opponent.getHealth()>opponentHealth){
            opponent.setHealth(opponentHealth);
        }
    }

    /**
     * Method to get the name of a unit
     * @return - String, the name of the unit
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the health of a unit
     * @return - int, health of the unit
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method to get the attack of a unit
     * @return - int, attack of the unit
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Method to get the armor of a unit
     * @return - int, armor of the unit
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Method to get the attack bonus of a unit. This varies based on unit-type, and is therefore abstract
     * @param terrain the terrain of the attack
     * @return - will return an int value, which represents the attack bonus, based on unit-type
     */
    public abstract int getAttackBonus(Terrain terrain);

    /**
     * Method to get the resistance of a unit. This varies based on unit-type, and is therefore abstract
     * @param terrain the terrain of the attack
     * @return - will return an int value, which represents the resistance bonus, based on unit-type
     */
    public abstract int getResistanceBonus(Terrain terrain);

    /**
     * Method to set the health of a unit.
     * @param health - the new health of a unit. if this is lower than 0, health will be set to zero.
     */
    public void setHealth(int health){
        this.health = Math.max(health, 0);
    }

    /**
     * Gets the x-coordinate of a unit
     * @return the current x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of a unit
     * @param x the x-coordinate to be set to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y position of a unit
     * @return the current y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of a unit
     * @param y the y-coordinate to be set to
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * toString method for a Unit.
     * @return a string with information about a unit.
     */
    @Override
    public String toString() {
        return "Unit type:" + this.getClass().getSimpleName() +"Name: " + name + ", Health: " + health+", Attack: " + attack + ", Armor: " + armor +" ";

    }

    /**
     * method to check if two units are the same
     * this happens when the object is the same
     * @param unit - the unit to check
     * @return true/false if they are equal or not
     */
    @Override
    public boolean equals(Object unit) {
        return this == unit;
    }

}
