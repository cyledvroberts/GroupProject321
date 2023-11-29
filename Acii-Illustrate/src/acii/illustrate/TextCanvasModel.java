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
    
    /**
     * This function resets the text canvas to the default state
     * @param x the number of rows in the character array
     * @param y the number of columns in the character array
     */
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
    
    /**
     * This function is used for erasing characters from the character grid.
     * characters are erased in a circular area around the mouse position.
     * @param x the x-position of the mouse
     * @param y the y-position of the mouse
     * @param size the size of the eraser
     */
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
    
    /**
     * This function updates the currentCharacter variable, which is used to
     * update the character grid
     * @param n character to set as currentCharacter
     */
    public void setCurrentCharacter(char n){
        currentCharacter = n;
    }
    
    /**
     * This function sets the current character as the character at the current index value in the character
     * list. It then increments the index position of the character list by one.
     */
    public void nextCharacter() {
        setCurrentCharacter(currentCharList[currentCharPosition]);
        currentCharPosition += 1;
        
        // if the index position is at the end of the character list, reset 
        // index position to 0.
        if (currentCharPosition == currentCharList.length) {
            currentCharPosition = 0;
        }
    }
    
    /**
     * This function sets the currentCharList to a given list
     * @param newList char[] list used to set the current character list
     */
    public void setCurrentCharList(char[] newList) {
        currentCharList = newList;
        // initialize index position to 0.
        currentCharPosition = 0;
        
        // nextCharacter() is used here to set the initial character and increment
        // the index position. It's use here fixed a bug where characters were not
        // switching correctly when word wrapping
        nextCharacter();
    }
    
    /**
     * This function sets the current color of the character
     * @param n color used to set current color
     */
    public void setCurrentColor(Color n){
        currentColor = n;
    }
    
    /**
     * This function sets the background color
     * @param n color used to set the background color
     */
    public void setBackgroundColor(Color n){
        backgroundColor = n;
    }
    
    /**
     * This function sets a character at the given coordinates in the character grid 
     * to the current character
     * @param x row to set the current character
     * @param y column to set the current character
     */
    public void setCharacter(int x, int y){
        gridCharacters[x][y] = currentCharacter;
    }
    
    /**
     * This function sets the cell size which is used to scale the distance between
     * the characters that are drawn on the canvas
     * @param x integer to set the cell size
     */
    public void setCellSize(int x){
        cellSize = x;
    }
    
    /**
     * This function sets the color at the given coordinates in the color grid to
     * the current color
     * @param x row to set the current color
     * @param y column to set the current color
     */
    public void setCharacterColor(int x, int y){
        gridColors[x][y] = currentColor;
    }
    
    /**
     * This function loads a canvas by copying temporary loaded character and color
     * arrays, as well as the background color, to the variables used for the
     * displayed canvas
     * @param characters 2d array of characters to copy to the gridCharacters array
     * @param colors 2d array of colors to copy to the gridColors array
     * @param background color to copy to backgroundColor
     */
    public void loadCanvas(char[][] characters, Color[][] colors, Color background){
        gridCharacters = characters;
        gridColors = colors;
        backgroundColor = background;
    }
    // getter functions*********************************************************
    
    /**
     * Function to get the value of the current character
     * @return currentCharacter character value
     */
    public char getCurrentCharacter(){
        return currentCharacter;
    }
    
    /**
     * Function to get the value of the current color
     * @return currentColor color value
     */
    public Color getCurrentColor(){
        return currentColor;
    }
    
    /**
     * Function to get the value of the current background color
     * @return backgroundColor color value
     */
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }
    
    /**
     * Function to get the value of the current cell size
     * @return cellSize integer value
     */
    public int getCellSize(){
        return cellSize;
    }
    
    /**
     * Function to return the character grid
     * @return gridCharacters 2d array of characters
     */
    public char[][] getCharacters(){
        return gridCharacters;
    }
    
    /**
     * Function to return the color grid
     * @return gridColors 2d array of colors
     */
    public Color[][] getCharacterColors(){
        return gridColors;
    }
}
