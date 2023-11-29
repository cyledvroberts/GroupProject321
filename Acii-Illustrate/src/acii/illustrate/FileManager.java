package acii.illustrate;

import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

/**
 * This class handles operations involving files such as
 * saving, loading, and deleting
 */
public class FileManager{ 
    
    /**
     * saves list that holds the names of all the save file.
     */
    private ArrayList<String> saves = new ArrayList<String>();
     
    /**
     * characters, colors, and background variables hold temporary values used
     * in getter functions to transfer saved file data to other parts of the
     * program
     */
    private char[][] characters;
    private Color[][] colors; 
    private Color background;
    
    /**
     * folder name of folder to write save files in
     */
    private String folder = "saves";
    
    /**
     * folderPath the path to the save folder
     */
    private Path folderPath = Paths.get(folder);
    
    /**
     * This function checks for the save folder and then reads any names of 
     * files inside to the saves list
     */
    public void checkSaveFolder(){
        
        // this block checks if a saves folder exists
        // if not, it creates one.
        try{
            if (!Files.exists(folderPath)){
                Files.createDirectory(folderPath);
            }
        } catch (IOException e){
            e.getMessage();
        }   
        
        // this block surveys the save folder and adds the
        // names of any files inside to the saves list
        saves.clear();
        if (Files.exists(folderPath) && Files.isDirectory(folderPath)){
            try {
                Files.list(folderPath).forEach(file -> {
                    saves.add(file.getFileName().toString());
                });
            } catch (IOException e){
                e.getMessage();
            }
        }
    }
    
    /**
     * This function saves the current canvas to a file
     * @param fileName name of the file to be written
     * @param charArray the current character array that describes the canvas
     * @param colorArray the current color array that describes the color of each character
     * @param backgroundColor the background color of the current canvas
     */
    public void saveFile(String fileName, char[][] charArray, Color[][] colorArray, Color backgroundColor){
        
        Path file = folderPath.resolve(fileName + ".txt");
        
        try{
            if (!Files.exists(folderPath)){
                Files.createDirectory(folderPath);
            }
        } catch (IOException e){
            e.getMessage();
        }   
        
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file));
            outputStream.writeObject(charArray);
            outputStream.writeObject(colorArray);
            outputStream.writeObject(backgroundColor);
            outputStream.close();
            
        }catch(IOException e){
            e.getMessage();
        }
        
    }
    
    /**
     * This function loads file data into the characters, colors, and background
     * variables 
     * @param fileName name of the file to be loaded
     */
    public void loadFile(String fileName){
        
        Path filePath = Paths.get(folder, fileName);
        
        try{
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath));
            characters = (char[][]) inputStream.readObject();
            colors = (Color[][]) inputStream.readObject();
            background = (Color) inputStream.readObject(); 
            inputStream.close();
            
        }catch(IOException | ClassNotFoundException e){
            e.getMessage();
        }
        
    }
    
    /**
     * This function deletes a file that is in the saves folder
     * @param fileName name of the file to be deleted
     */
    public void deleteFile(String fileName){
        
        Path filePath = folderPath.resolve(fileName);
        
        try{
            if (Files.exists(filePath)){
                Files.delete(filePath);
            }
        } catch (IOException e){
            e.getMessage();
        }    
    }
    
    // getters******************************************************************
    
    /**
     * This function returns an array list of save names
     * @return saves an array list of the names of all the files in the saves folder
     */
    public ArrayList getSaves(){
        checkSaveFolder();  
        return saves;
    }
    
    /**
     * This function returns a loaded character grid
     * @return characters a 2d array of characters that can populate the canvas
     */
    public char[][] getNewCharacterGrid(){
        return characters;
    }
    
    /**
     * This function returns a loaded color grid
     * @return colors a 2d array of colors that correspond to the characters in the character array
     */
    public Color[][] getNewColorGrid(){
        return colors;
    }
    
    /**
     * This function returns a loaded background color
     * @return background a color used to determine the color of the background
     */
    public Color getNewBackground(){
        return background;
    }
    
    /**
     * This function checks whether a file exists in the saves folder
     * @param filename name of the file to search for
     * @return True is saves folder contains file. False if it does not.
     */
    public boolean isInSaves(String filename){
        return saves.contains(filename);
    }
}
