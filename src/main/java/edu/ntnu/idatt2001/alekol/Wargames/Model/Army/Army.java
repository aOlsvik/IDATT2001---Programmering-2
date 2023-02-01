package edu.ntnu.idatt2001.alekol.Wargames.Model.Army;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Banner;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


public class Army {
    private String name;
    private ArrayList<Unit> units;
    private Banner banner;

    /**
     * Constructor for the army class, where an arraylist is created
     * @param name - Name of the army
     * @throws IllegalArgumentException - If the name of the army is empty
     */
    public Army(String name)throws IllegalArgumentException{
        if(name.isBlank()) throw new IllegalArgumentException("The name of an army cannot be blank");
        this.name=name;
        this.units=new ArrayList<>();
        this.banner=null;
    }

    /**
     * Constructor where an army of units already exist is taken in
     * @param name - name of the army
     * @param units - the army to be registered. If this is empty, the other constructor will be used instead
     * @throws IllegalArgumentException - if the name is blank
     */
    public Army(String name, ArrayList<Unit> units)throws IllegalArgumentException{
        if(units.isEmpty()){
            Army army = new Army(name);
        }
        if(name.isBlank()) throw new IllegalArgumentException("The name of an army cannot be blank");
        else{
            this.name=name;
            this.units=units;
            this.banner=null;
        }
    }

    /**
     * A constructor to create an army with a banner. the banner will be used to get the color of the army in battle
     * @param name - the name of the army
     * @param banner - the banner
     * @throws IllegalArgumentException - if the name of the army is blank
     */

    public Army(String name, Banner banner)throws IllegalArgumentException{
        if(name.isBlank()) throw new IllegalArgumentException("The name of an army cannot be blank");
        this.name=name;
        this.units=new ArrayList<>();
        this.banner=banner;
    }

    /**
     * A constructor to create an army with banner and a list of units
     * @param name - the name of the army
     * @param units - the units of the army
     * @param banner - the banner of the army
     * @throws IllegalArgumentException - if the army name is blank
     */
    public Army(String name, ArrayList<Unit> units, Banner banner)throws IllegalArgumentException{
        if(name.isBlank()) throw new IllegalArgumentException("The name of an army cannot be blank");
        this.name=name;
        this.units=units;
        this.banner=banner;
    }

    /**
     * get method to find the name of an army
     * @return - the name
     */
    public String getName() {
        return name;
    }

    /**
     * method to add a new unit to an army
     * @param unit - the unit to be added
     */
    public void add(Unit unit){
        if(unit.getHealth()!=0){
            units.add(unit);
        }
    }

    /**
     * method to get the banner of an army
     * @return - the banner. null if the army doesn't have a banner
     */
    public Banner getBanner() {
        return banner;
    }

    /**
     * Method to add all units from a list to an army
     * Uses add method from Army, so that it uses the same checks
     * @param units - the list of units
     */
    public void addAll(ArrayList<Unit> units){
        for (Unit u:units) {
            this.add(u);
        }
    }

    /**
     * Method to remove a specified unit from the army
     * @param unit - the unit to be removed
     */
    public void remove(Unit unit){
        units.remove(unit);
        this.units.trimToSize();
    }

    /**
     * Method to check if an army has units in it
     * @return - true if it's not empty, false if it is empty
     */
    public boolean hasUnits(){
        return !getAllUnits().isEmpty();
    }

    /**
     * method to get all the units in an army
     * @return - an arraylist of units
     */
    public ArrayList<Unit> getAllUnits(){
        return this.units;
    }

    /**
     * method to get a random unit from an army
     * @return - a random unit
     */
    public Unit getRandom(){
        Random randomIndex = new Random();
        return units.get(randomIndex.nextInt(units.size()));
    }

    /**
     * Method to get all the units that are Infantry units
     * @return the infantry units
     */
    public ArrayList<Unit> getInfantryUnits(){
        return units.stream().filter(s -> s instanceof InfantryUnit).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method to get all the units that are Ranged units
     * @return the ranged units
     */
    public ArrayList<Unit> getRangedUnits(){
        return units.stream().filter(s -> s instanceof RangedUnit).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method to get all the units that are Cavalry units
     * uses getClass() to not add the commander units, which are also a cavalry unit
     * @return the cavalry units
     */
    public ArrayList<Unit> getCavalryUnits(){
        return units.stream().filter(s -> s.getClass() == CavalryUnit.class).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method to get all the units that are Commander units
     * @return the commander units
     */
    public ArrayList<Unit> getCommanderUnits(){
        return units.stream().filter(s -> s.getClass() == CommanderUnit.class).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * method to get all info about the units of an army
     * @return a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getName());
        if(getBanner()!=null) sb.append(", ").append(getBanner().getName());
        sb.append("\nNumber of units: ").append(getAllUnits().size()).append("\n");
        if(!getInfantryUnits().isEmpty()) sb.append(getInfantryUnits().size()).append(" x Infantry Units\n");
        if(!getRangedUnits().isEmpty()) sb.append(getRangedUnits().size()).append(" x Ranged Units\n");
        if(!getCavalryUnits().isEmpty()) sb.append(getCavalryUnits().size()).append(" x Cavalry Units\n");
        if(!getCommanderUnits().isEmpty())  sb.append(getCommanderUnits().size()).append(" x Commander Units\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Army army)) return false;
        return Objects.equals(getName(), army.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), units);
    }
}
