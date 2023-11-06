/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;

import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class FileManager{ 
    
    // list that holds the names of all the save files
    private ArrayList<String> saves = new ArrayList<String>();
    
    // holds temporary values
    // used in getter functions to transfer
    // saved file data to other parts of the program
    private char[][] characters;
    private Color[][] colors; 
    private Color background;
    // name of folder to save canvases
    private String folder = "saves";
    
    // path to the save folder
    private Path folderPath = Paths.get(folder);
    
    
    // this function checks for the save folder and then reads
    // any names of files inside to the saves list
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
    
    // getters
    public ArrayList getSaves(){
        checkSaveFolder();
        return saves;
    }
    
    public char[][] getNewCharacterGrid(){
        return characters;
    }
    
    public Color[][] getNewColorGrid(){
        return colors;
    }
    
    public Color getNewBackground(){
        return background;
    }
    
    public boolean isInSaves(String filename){
        return saves.contains(filename);
    }
}
