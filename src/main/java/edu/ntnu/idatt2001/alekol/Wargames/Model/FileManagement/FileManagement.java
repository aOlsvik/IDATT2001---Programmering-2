package edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement;

import edu.ntnu.idatt2001.alekol.Wargames.Model.Army.Army;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Battle.Battle;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Banner;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Enum.Terrain;
import edu.ntnu.idatt2001.alekol.Wargames.Model.Units.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is a class to read and write army objects to a file
 * An army will be written to a csv-file, and will be written on this format:
 *
 * <pre>{@code}
 * Army name
 * unit type, unit name, unit health
 * </pre>
 *
 * Example code:
 * <pre>{@code}
 * Human army
 * InfantryUnit, Footman, 100
 * InfantryUnit, Footman, 100
 * InfantryUnit, Footman, 100
 * </pre>
 */

public class FileManagement {

    private static final String SEPARATOR = ",";
    private static final String NEWLINE="\n";
    private static final String FILEENDING = ".csv";
    private static final String FILEENDINGTXT =".txt";
    private static final String FILEPATHNAME="src/main/resources/ArmyFiles/";


    /**
     * Method which will mainly be used in the program.
     * This method is a simple way to name the file where an army should be written.
     * It is based on the name, without spaces.
     * @param army - the army to written to file
     * @throws IOException - if there are errors in the file writing
     */
    public static void writeArmyToFile(Army army) throws IOException {
        String fileName = army.getName().replaceAll("\\s","");
        File file = new File(FILEPATHNAME+"/Armies/"+fileName+FILEENDING);
        armyWriting(army, file);
    }

    /**
     * method to writhe an army to a specified file. Is used in other methods for cleaner code
     * @param army the army to write
     * @param file - the file to be written to
     * @throws IOException - if the writing fail
     */
    private static void armyWriting(Army army, File file) throws IOException {
        ArrayList<Unit> units = army.getAllUnits();
        StringBuilder writeLine = new StringBuilder();

        try (FileWriter fileWriter = new FileWriter(file)) {
            writeLine.append(army.getName()).append(NEWLINE);
            writeLine.append(army.getBanner()).append(NEWLINE);
            units.forEach(s -> writeLine.append(s.getClass().getSimpleName()).append(SEPARATOR)
                    .append(s.getName()).append(SEPARATOR)
                    .append(s.getHealth()).append(NEWLINE));
            fileWriter.write(writeLine.toString());
            if(!file.getAbsolutePath().contains("test")){
                writeArmyNamesToFile(army);
            }
        } catch (IOException ioException) {
            throw new IOException("Could not write this army to the file with name: " + file.getName()
                    + " due to " + ioException.getMessage());
        }
    }

    /**
     * Method to write the info of an army to a file.
     * Will only accept .csv files, as these are easy to handle with the info used
     * Mostly used for testing, as another method has been implemented for the program
     * @param file - the .csv to be used
     * @param army - the army to be written
     * @throws IOException - If the file is not .csv, or if it could not write for any other reason
     */
    public static void writeArmyToFile(File file, Army army) throws IOException {
        if (!file.getName().endsWith(FILEENDING)) {
            throw new IOException("File writing for an army supports only " + FILEENDING + " files");
        }
        armyWriting(army, file);
    }

    /**
     * Method to write the names of all the armies to a file.
     * This will be useful when reading all the armies registered from the files,
     * because the file will have a name depending on the army name.
     * All the names will be written to the file src/main/resources/ArmyFiles/ArmyNames.txt.
     * @param army - the army, which name should write to file
     * @throws IOException - if writing army name fails
     */
    public static void writeArmyNamesToFile(Army army) throws IOException {
        File file = new File(FILEPATHNAME+"ArmyNames"+FILEENDINGTXT);
        String nameToBeWritten=army.getName().replaceAll("\\s","");
        StringBuilder names = new StringBuilder();
        ArrayList<String> armyNames= readArmyNamesFromFile();
        armyNames.forEach(s->names.append(s).append("\n"));
        if(!armyNames.contains(nameToBeWritten)){
            names.append(nameToBeWritten).append("\n");
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(names.toString());
            } catch (IOException ioException) {
                throw new IOException("Could not write this army name to the file with name: " + file.getName()
                        + " due to " + ioException.getMessage());
            }
        }
    }

    /**
     * Method to remove an army from the register of armies
     * Will also delete the file with the registered information
     * @param army - the army to remove from file management
     * @throws IOException - if writing the remaining armies fails
     */
    public static boolean removeArmyFromFile(Army army) throws IOException {
        boolean deleted=false;
        String filePath=FILEPATHNAME+"Armies/"+army.getName().replaceAll("\\s","");
        File file = new File(filePath);
        File fileToBeDeleted= new File("src/main/resources/ArmyFiles/Armies/"+army.getName()+FILEENDINGTXT);
        String nameToBeDeleted=army.getName().replaceAll("\\s","");
        StringBuilder names = new StringBuilder();
        ArrayList<String> armyNames= readArmyNamesFromFile();
        if(armyNames.contains(nameToBeDeleted)){
            armyNames.remove(nameToBeDeleted);
            names.append(armyNames);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(names.toString());
                deleted=fileToBeDeleted.delete();
            } catch (IOException ioException) {
                throw new IOException("Could not write this army name to the file with name: " + file.getName()
                        + " due to " + ioException.getMessage());
            }
        }
        return deleted;
    }

    /**
     * method to read all the army names from the file '/ArmyFiles/ArmyNames.txt'
     * @return the army names registered
     * @throws IOException - if the rading fails
     */
    public static ArrayList<String> readArmyNamesFromFile() throws IOException {
        File file = new File(FILEPATHNAME+"ArmyNames"+FILEENDINGTXT);
        ArrayList<String> names=new ArrayList<>();

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNext()) {
                String armyName=sc.nextLine();
                names.add(armyName);
            }
        } catch(Exception e){
            throw new IOException("Something went wrong reading the army names from the file");
        }
        return names;
    }

    /**
     * A method to read an army from a file. Will only read .csv files
     * @param file - the file to read from
     * @return - the Army that is read from the file
     * @throws IOException - if the file is not .csv, if the format is wrong (more/less than 3 fields),
     *  or if the class of a unit written is not a supported class (a subclass of unit)
     * @throws IllegalArgumentException - if the info that was read cannot be used to create a unit or an army
     * @throws NumberFormatException - if the health of a unit is not written as an integer
     */
    public static Army readArmyFromFile(File file) throws IOException, IllegalArgumentException, NumberFormatException{
        if(!file.getName().endsWith(FILEENDING)){
            throw new IOException("File writing for an army supports only "+ FILEENDING+ " files");
        }
        String armyName;
        ArrayList<Unit> units=new ArrayList<>();
        String armyBanner;
        Unit unit;
        String[] separatedLine;
        String unitClass;
        String unitName;
        int unitHealth;

        try(Scanner sc = new Scanner(file)){
            armyName=sc.nextLine();
            armyBanner= sc.nextLine();
            while(sc.hasNext()){
                String line = sc.nextLine();
                separatedLine=line.split(SEPARATOR);
                if(separatedLine.length!=3){
                    throw new IOException("The army could not be read, due to an error in the written file");
                }
                unitClass=separatedLine[0];
                unitName=separatedLine[1];

                try {
                    unitHealth=Integer.parseInt(separatedLine[2]);
                } catch (NumberFormatException numberFormatException){
                    throw new NumberFormatException("The units health could not be processed, due to it not being an integer");
                }

                unit = switch (unitClass) {
                    case "InfantryUnit" -> UnitFactory.createUnit("InfantryUnit", unitName, unitHealth);
                    case "RangedUnit" -> UnitFactory.createUnit("RangedUnit",unitName, unitHealth);
                    case "CavalryUnit" -> UnitFactory.createUnit("CavalryUnit", unitName, unitHealth);
                    case "CommanderUnit" -> UnitFactory.createUnit("CommanderUnit", unitName, unitHealth);
                    default -> throw new IOException("Could not read the units class");
                };
                units.add(unit);
            }
            if(!Objects.equals(armyBanner, "null")) return new Army(armyName, units, Banner.valueOf(armyBanner));

            return new Army(armyName, units);

        } catch (IOException ioException){
            throw new IOException("The info in the file could not be read due to: " + ioException.getMessage());
        } catch (NumberFormatException numberFormatException){
            throw new NumberFormatException("Could not create a unit from the info, due to: " + numberFormatException.getMessage());
        } catch(IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Could not recreate the unit, due to: " + illegalArgumentException.getMessage());
        }
    }

    /**
     * Method to write a battle to file. Will write the two names of the armies, and the terrain
     * Writes to '/ArmyFiles/CurrentBattle.txt'
     * @param battle - the battle to be written
     * @throws IOException - if the writing fails
     */
    public static void writeBattleToFile(Battle battle) throws IOException {
        File file = new File(FILEPATHNAME+"CurrentBattle"+FILEENDINGTXT);
        String terrain = battle.getTerrain().toString();
        String name1=battle.getArmyOne().getName().replaceAll("\\s","");
        String name2=battle.getArmyTwo().getName().replaceAll("\\s","");
        StringBuilder names = new StringBuilder();
        names.append(terrain).append("\n").append(name1).append("\n").append(name2);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(names.toString());
        } catch (IOException ioException) {
            throw new IOException("Could not write this army name to the file with name: " + file.getName()
                    + " due to " + ioException.getMessage());
        }
    }

    /**
     * Method to read a battle from the file. will return the battle written in '/ArmyFiles/CurrentBattle.txt'
     * @return - the battle that was read
     * @throws IOException - if the reading fails
     */
    public static Battle readBattleNamesFromFile() throws IOException {
        ArrayList<Army> armies=new ArrayList<>();
        File file = new File(FILEPATHNAME+"CurrentBattle"+FILEENDINGTXT);
        String armyName;
        File fileToRead;
        Army army;
        Battle battle;
        Terrain terrain;
        try(Scanner s = new Scanner(file)){
            terrain=Terrain.valueOf(s.nextLine());
            while(s.hasNext()){
                armyName = s.nextLine();
                fileToRead=new File(FILEPATHNAME+"/Armies/"+armyName+FILEENDING);
                army=readArmyFromFile(fileToRead);
                armies.add(army);
            }
        }catch (Exception e){
            throw new IOException("Could not read this battle from the file due to: " + e.getMessage());
        }
        battle= new Battle(armies.get(0),armies.get(1), terrain );
        return battle;
    }
}
