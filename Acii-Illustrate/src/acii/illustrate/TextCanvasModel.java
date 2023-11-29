package acii.illustrate;

import java.awt.*;
import java.util.Arrays;

/**
 * This class handles the data structures and operations that make up the
 * model of the program.
 */
public class TextCanvasModel
{
    
    /**
     * currentColor stores the color of the characters
     */
    private static Color currentColor = Color.BLACK;
    
    /**
     * currentCharList stores a list of characters that
     * the user inputs to be drawn on the canvas
     */
    private static char[] currentCharList = {' '};
    
    /**
     * currentCharPosition stores the index position used with currentCharList
     */
    private static int currentCharPosition = 0;
    
    /**
     * currentCharacter stores the character that is added to the character grid
     */
    private static char currentCharacter = ' ';
    
    /**
     * backgroundColor stores the color that the background is set to
     */
    private static Color backgroundColor = Color.white;
    
    /**
     * cellSize is used to change the size of the "cells" that the characters
     * exist in. larger numbers create more space between characters and 
     * vise versa.
     */
    private int cellSize = 10;
    
    /**
     * gridCharacters is a 2d array that stores the position of the characters
     */
    private char[][] gridCharacters;
    
    /**
     * gridColors is a 2d array that stores the corresponding colors for each
     * of the characters in the gridCharacters array
     */
    private Color[][] gridColors;
    
    /**
     * This is the constructor for a TextCanvas object.
     * It initializes the gridCharacters and gridColors arrays with default values.
     * @param x number of rows of the character and color arrays
     * @param y number of columns of the character and color arrays
     */
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
    
    // setter functions*********************************************************
    
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
    
    // function used for erasing
    public void characterEraser(int x, int y, String size){
        
        // used for detecting if out of bounds
        int numRows = gridCharacters.length;
        int numCols = gridCharacters[0].length;
        
        if (size == "Small"){
            gridCharacters[x][y] = currentCharacter;
        }
        
        if (size == "Medium"){
            
            // iterates through a 5 x 5 cell around the center point
            for (int i = -2; i <= 2; i++){
                for (int j = -2; j <= 2; j++){
                    // this makes sure the array index is not out of bounds
                    if (!(x + i < 0) && !(x + i > numRows - 1) && !(y + j < 0) && !(y + j > numCols - 1)){
                        // these if statements leave out certain cells
                        // to make the eraser shape more circular
                        if (x + i == x - 2 && y + j == y + 2){
                            continue;
                        }
                        if (x + i == x - 2 && y + j == y - 2){
                            continue;
                        }
                        if (x + i == x + 2 && y + j == y + 2){
                            continue;
                        }
                        if (x + i == x + 2 && y + j == y - 2){
                            continue;
                        }
                        // set remaining cells to the appropriate character
                        gridCharacters[x + i][y + j] = currentCharacter;
                    }
                }
                
            } 
        }
        
        if (size == "Large"){
            
            // iterates through a 7 x 7 cell around the center point
            for (int i = -3; i <= 3; i++){
                for (int j = -3; j <= 3; j++){
                    // this makes sure the array index is not out of bounds 
                    if (!(x + i < 0) && !(x + i > numRows - 1) && !(y + j < 0) && !(y + j > numCols - 1)){
                        // these if statements leave out certain cells
                        // to make the eraser shape more circular
                        if (x + i == x - 3 && y + j == y + 3){
                            continue;
                        }
                        if (x + i == x - 2 && y + j == y + 3){
                            continue;
                        }
                        if (x + i == x + 2 && y + j == y + 3){
                            continue;
                        }
                        if (x + i == x + 3 && y + j == y + 3){
                            continue;
                        }
                        if (x + i == x - 3 && y + j == y + 2){
                            continue;
                        }
                        if (x + i == x + 3 && y + j == y + 2){
                            continue;
                        }
                        if (x + i == x - 3 && y + j == y - 2){
                            continue;
                        }
                        if (x + i == x + 3 && y + j == y - 2){
                            continue;
                        }
                        if (x + i == x - 3 && y + j == y - 3){
                            continue;
                        }
                        if (x + i == x - 2 && y + j == y - 3){
                            continue;
                        }
                        if (x + i == x + 2 && y + j == y - 3){
                            continue;
                        }
                        if (x + i == x + 3 && y + j == y - 3){
                            continue;
                        }
                        // set remaining cells to the appropriate character
                        gridCharacters[x + i][y + j] = currentCharacter;
                    }
                }
            }
        }
    }
    
    public void setCurrentCharacter(char n){
        currentCharacter = n;
    }
    
    public void nextCharacter() {
        setCurrentCharacter(currentCharList[currentCharPosition]);
        currentCharPosition += 1;
        if (currentCharPosition == currentCharList.length) {
            currentCharPosition = 0;
        }
    }
    
    public void setCurrentCharList(char[] newList) {
        currentCharList = newList;
        currentCharPosition = 0;
        nextCharacter();
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
