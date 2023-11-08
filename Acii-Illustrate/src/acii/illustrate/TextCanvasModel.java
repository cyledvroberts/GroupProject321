/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TextCanvasModel
{
    private static Color currentColor = Color.BLACK; 
    private static char currentCharacter = ' ';
    private static Color backgroundColor = Color.white;    
    // cellSize is used to change the size of the "cells" that the characters
    // exist in. larger numbers create more space between characters and 
    // vise versa.
    private int cellSize = 10;
    // gridCharacters is a 2d array that stores the position of the characters
    private char[][] gridCharacters;
    // gridColors is a 2d array that stores the corresponding colors for each
    // of the characters in the gridCharacters array
    private Color[][] gridColors;
        
    // this is the constructor for a TextCanvas object
    // it initializes the gridCharacters and gridColors arrays with default values
    // right now the size of the arrays is hard coded
    public TextCanvasModel(int x, int y) 
    {
        gridCharacters = new char[x][y];
        gridColors = new Color[x][y];
        for (int i = 0; i < x; i++) 
        {
            // Arrays.fill() is from the java.util.Arrays library.
            // It completely fills an array with the specified value.
            // right here, each loop fills the i'th row with the value.
            // the array of characters is filled with spaces.
            // the array of colors is filled with black.
            Arrays.fill(gridCharacters[i], ' ');
            Arrays.fill(gridColors[i], Color.BLACK);
        }
    }
    
    // setter functions
    
    public void resetTextCanvas(int x, int y){
        
        currentCharacter = ' ';
        currentColor = Color.black;
        
        for (int i = 0; i < x; i++) 
        {
            // Arrays.fill() is from the java.util.Arrays library.
            // It completely fills an array with the specified value.
            // right here, each loop fills the i'th row with the value.
            // the array of characters is filled with spaces.
            // the array of colors is filled with black.
            Arrays.fill(gridCharacters[i], ' ');
            Arrays.fill(gridColors[i], Color.BLACK);
        }
    }
    
    public void setCurrentCharacter(char n){
        currentCharacter = n;
    }
    
    public void setCurrentColor(Color n){
        currentColor = n;
    }

    public void setBackgroundColor(Color n){
        backgroundColor = n;
    }
    
    public void setCharacter(int x, int y){
        gridCharacters[x][y] = currentCharacter;
    }
    
    public void setCellSize(int x){
        cellSize = x;
    }
    
    public void setCharacterColor(int x, int y){
        gridColors[x][y] = currentColor;
    }
    
    public void loadCanvas(char[][] characters, Color[][] colors, Color background){
        gridCharacters = characters;
        gridColors = colors;
        backgroundColor = background;
    }
    // getter functions
    public char getCurrentCharacter(){
        return currentCharacter;
    }
    
    public Color getCurrentColor(){
        return currentColor;
    }
    
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }
    
    public int getCellSize(){
        return cellSize;
    }
    
    public char[][] getCharacters(){
        return gridCharacters;
    }
    
    public Color[][] getCharacterColors(){
        return gridColors;
    }
}
