package edu.ntnu.idatt2001.alekol.Wargames.Model.Units;

import java.util.ArrayList;

public class UnitFactory {

    /**
     * Method to create a unit with specified information
     * @param className - the type / class of the unit
     * @param name - name of the unit
     * @param health - the health of the unit
     * @return - the unit which was created
     * @throws IllegalArgumentException - if no unit was created (due to wrong input parameters)
     */
    public static Unit createUnit(String className, String name, int health) throws IllegalArgumentException{
        try{
            return switch (className) {
                case "InfantryUnit" -> new InfantryUnit(name, health);
                case "RangedUnit" -> new RangedUnit(name, health);
                case "CavalryUnit" -> new CavalryUnit(name, health);
                case "CommanderUnit" -> new CommanderUnit(name, health);
                default -> throw new IllegalArgumentException("Could not create a unit with class: " + className);
            };
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * A method to create a specified amount of units of a specified type / class
     * @param className - the type / class of the unit
     * @param name - name of the unit
     * @param health - the health of the unit
     * @param numberOfUnits - the amount of units to be created
     * @return - a list containing all the units
     * @throws IllegalArgumentException - if something doesn't work with creating units / the list
     */
    public static ArrayList<Unit> createSeveralUnits(String className, String name, int health, int numberOfUnits) throws IllegalArgumentException{
        ArrayList<Unit> units = new ArrayList<>();
        try{
            for(int i=0; i<numberOfUnits; i++){
                units.add(createUnit(className, name, health));
            }
            return units;
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
