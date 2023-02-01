package edu.ntnu.idatt2001.alekol.Wargames.Model.Battle;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.Unit;
import java.util.Random;

/**
 * This class contains two ways of simulating a battle. You can either simulate it right away, or do it in steps
 * A battle contains two armies and the terrain of the batlle
 */

public class Battle  {
    private Army armyOne;
    private Army armyTwo;
    private Terrain terrain;
    private Unit attackingUnit;
    private Unit opponentUnit;
    private Army attackingArmy;
    private Army opponentArmy;
    private int attackCounter;
    private boolean isFinished;
    private Army winner=null;

    /**
     * Constructor for a battle. Will have two armies and a terrain
     * @param armyOne - the first army
     * @param armyTwo - the second army
     * @param terrain - the terrain of the battle
     * @throws IllegalArgumentException - if one or both of the armies does not have units
     */
    public Battle(Army armyOne,Army armyTwo,Terrain terrain) throws IllegalArgumentException{
        if(!armyOne.hasUnits() || !armyTwo.hasUnits()) throw new IllegalArgumentException("Both armies of a battle must have units");
        this.armyOne=armyOne;
        this.armyTwo=armyTwo;
        this.terrain=terrain;
        this.attackCounter=whoStarts();
        this.isFinished=false;
    }

    /**
     * method to get the first army
     * @return the army registered as army one
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * method to get the second army
     * @return - the army registered as army two
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * method to get the terrain of the battle
     * @return the terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * method to check if the battle is finished
     * @return true / false if the battle is finished or not
     */
    public boolean isFinished() {
        return !(armyOne.hasUnits() && armyTwo.hasUnits());
    }

    /**
     * method to set the winner of a battle
     * @param army - the army that won
     */
    public void setWinner(Army army){
        winner=army;
    }

    /**
     * method to get the winner of the battle
     * is null if no one has won
     * @return the winner
     */
    public Army getWinner() {
        return winner;
    }

    /**
     * method to set that the battle is finished
     */
    public void setFinished(){
        isFinished=true;
    }

    /**
     * Method to simulate a battle all in one. The armies will alternate in attacking each other until one of them
     * run out of units.
     * @return Army - the army that won the battle
     */
    public Army simulate()  {
        while(armyOne.hasUnits()&&armyTwo.hasUnits()){
            attack(attackCounter++);
        }
        setFinished();
        return armyOne.hasUnits() ?  armyOne : armyTwo;
    }

    /**
     * method to get the attack counter. Is useful / fun to look at after a battle
     * @return the number of attacks in total (might be 1 one more than the actual count, depending on who started)
     */
    public int getAttackCounter() {
        return attackCounter;
    }

    /**
     * Method to do a "simulation step", which is a single attack
     * @return - 'null' when an attack is successful, but doesn't kill.
     *  or - the unit that died when an attack is successful and kills
     *  or - 'null' if the battle is finished
     */
    public Unit simulationStep(){
        if(armyOne.hasUnits() && armyTwo.hasUnits()) return attack(attackCounter++);
        setFinished();
        return null;
    }

    /**
     * the method to make an attack happen.
     * The attacking / opponent army is set based on the attack counter
     * A random unit from the attacking army will attack a random unit from the opponent army
     * @param attackCounter - the attack counter
     * @return - the unit that died if a unit is killed in the attack. if not, 'null'
     */
    public Unit attack(int attackCounter){
        if(attackCounter%2==0){
            attackingArmy=armyOne;
            opponentArmy=armyTwo;
        }
        else{
            attackingArmy=armyTwo;
            opponentArmy=armyOne;
        }
        attackingUnit =attackingArmy.getRandom();
        opponentUnit=opponentArmy.getRandom();
        attackingUnit.attack(opponentUnit,terrain);
        if(opponentUnit.getHealth()==0)  {
            opponentArmy.remove(opponentUnit);
            return opponentUnit;
        }
        return null;
    }

    /**
     * method to decide who starts a battle, for testing purposes
     * This is random if army sizes are equal. If not, the army with fewer units will start
     * @return - int, 0 / 1 depending on which army is going to start.
     */
    public int whoStarts() {
        boolean armyOneStarts=true;
        int attackCounter=0;
        if(armyOne.getAllUnits().size()==armyTwo.getAllUnits().size()){
            Random whoStarts = new Random();
            if(whoStarts.nextInt(2)==1) armyOneStarts=false;
        }
        else{
            if(armyOne.getAllUnits().size()>armyTwo.getAllUnits().size()){
                armyOneStarts=false;
            }
        }
        if(!armyOneStarts) attackCounter+=1;
        return attackCounter;
    }

    /**
     * toString method for the Battle class.
     * @return the String of a battle
     */
    @Override
    public String toString() {
        return "Battle between: " + armyOne + " and " + armyTwo;
    }
}
