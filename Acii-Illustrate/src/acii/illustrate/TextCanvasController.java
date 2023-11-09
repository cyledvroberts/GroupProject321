/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
public class TextCanvasController extends JPanel
{
    // these variables are the size of the character and
    // color arrays and used to construct the TextCanvasModel
    private static final int ROW = 200;
    private static final int COL = 200;
    
    private static TextCanvasModel canvas = new TextCanvasModel(ROW, COL);
    private static FileManager fileManager = new FileManager();
    private static Button button = new Button();
    
    // variable to store the name of a canvas
    // used when saving and deleting in order to reset the
    // current canvas on the screen if it gets deleted
    private String currentCanvas;
    private String eraserSize;
    private boolean eraser = false;
    
    public TextCanvasController()
    {   
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // xPress and yPress stores the x and y coordinates of the mouse
                // gotten from .getX() and .getY(). Dividing these by cellSize
                // here keeps the mouse position accurate to where the character
                // is drawn because the location that the character is drawn
                // is also scaled by cellSize later.
                int xPress = e.getX() / canvas.getCellSize();
                int yPress = e.getY() / canvas.getCellSize();
                // if the x and y coordinates are greater than 0 but less than
                // the size of the text canvas,
                // the current character and color are copied to the appropriate locations
                // in the arrays.
                // repaint() is called to instantly redraw the text canvas for
                // the user.
                if (xPress >= 0 && xPress < ROW && yPress >= 0 && yPress < COL) {
                    if (eraser == true){
                        canvas.characterEraser(xPress, yPress, eraserSize);
                    }else{
                        canvas.setCharacter(xPress, yPress);
                    }
                    canvas.setCharacterColor(xPress, yPress);
                    repaint();
                }
            }
        });
        
        // This function handles dragging the mouse while pressed and works
        // the exact same way as the previous function.
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xDrag = e.getX() / canvas.getCellSize();
                int yDrag = e.getY() / canvas.getCellSize();
                if (xDrag >= 0 && xDrag < ROW && yDrag >= 0 && yDrag < COL) {
                    if (eraser == true){
                        canvas.characterEraser(xDrag, yDrag, eraserSize);
                    }else{
                        canvas.setCharacter(xDrag, yDrag);
                    }
                    canvas.setCharacterColor(xDrag, yDrag);
                    repaint();
                }
            }
        });
    }
    
    // functions to add action listeners for buttons****************************
    
    public void addColorActionListener(JButton colorSwitchButton, JFrame frame) {
        colorSwitchButton.addActionListener(e -> {
            eraser = false;
            canvas.setCurrentColor(JColorChooser.showDialog(frame, "Choose a Color", canvas.getCurrentColor()));
        });
    }
    
    public void addCharActionListener(JButton charSwitchButton, JFrame frame) {
        charSwitchButton.addActionListener(e -> {
            eraser = false;
            String input = JOptionPane.showInputDialog(frame, "Enter a character:");
            if (input != null && !input.isEmpty()) {
                canvas.setCurrentCharacter(input.charAt(0));
            }
        });
    }
    
    public void addEraserItemActionListener(JMenuItem eraserSmall, JMenuItem eraserMedium, JMenuItem eraserLarge){
        eraserSmall.addActionListener (e -> {
           eraser = true;
           eraserSize = "Small";
           canvas.setCurrentCharacter(' ');
        });
        eraserMedium.addActionListener (e -> {
           eraser = true;
           eraserSize = "Medium";
           canvas.setCurrentCharacter(' ');
        });
        eraserLarge.addActionListener (e -> {
           eraser = true;
           eraserSize = "Large";
           canvas.setCurrentCharacter(' ');
        });
    }
    
    public void addEraserButtonActionListener(JPopupMenu menu, JButton eraserButton){
        eraserButton.addActionListener( e -> {
            menu.show(eraserButton, 10, eraserButton.getHeight());
        });
    }
    
    public void addBackgroundActionListener(JButton backgroundSwitchButton, JFrame frame, TextCanvasController drawingArea) {
        backgroundSwitchButton.addActionListener(e -> {
            eraser = false;
            canvas.setBackgroundColor((Color) JColorChooser.showDialog(frame, "Choose a Background Color", canvas.getBackgroundColor()));
            updateBackground();
        });
    }
    
    public void addSaveMenuItemActionListener(JMenuItem saveItem, JFrame frame, TextCanvasController drawingArea) {
        saveItem.addActionListener (e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter the name of your canvas:");
            if (input != null){
                if (drawingArea.isInSavesList(input)){
                    int choice = JOptionPane.showConfirmDialog(frame, "The canvas already exists. Would you like to overwrite?");
                    if (choice == 0){
                    drawingArea.saveCanvas(input);
                    JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                    setCurrentCanvas(input + ".txt"); 
                    }
                }else {
                drawingArea.saveCanvas(input);
                JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                setCurrentCanvas(input + ".txt");
                }
            }
        });
    }
    
    public void addSaveButtonActionListener(JButton saveButton, JPopupMenu saveMenu, TextCanvasController drawingArea, JFrame frame) {
        saveButton.addActionListener( e -> {
            saveMenu.removeAll();
            Button.updateSaveMenu(saveMenu, drawingArea, frame);
            
            saveMenu.show(saveButton, 10, saveButton.getHeight());
        });
    }
    
    public void addLoadActionListener(JButton loadButton, JPopupMenu loadMenu, TextCanvasController drawingArea, JFrame frame) {
        loadButton.addActionListener( e -> {
            loadMenu.removeAll();
            Button.updateLoadMenu(loadMenu, drawingArea, frame);
            
            loadMenu.show(loadButton, 10, loadButton.getHeight());
        });
    }
    
    public void addDeleteActionListener(JButton deleteButton, JPopupMenu deleteMenu, TextCanvasController drawingArea, JFrame frame) {
        deleteButton.addActionListener( e -> {
            deleteMenu.removeAll();
            Button.updateDeleteMenu(deleteMenu, drawingArea, frame);
            
            deleteMenu.show(deleteButton, 10, deleteButton.getHeight());
        });
    }
    
    public void addResetActionListener(JButton resetButton, TextCanvasController drawingArea, JFrame frame){
        resetButton.addActionListener(e -> {
            eraser = false;
            canvas.resetTextCanvas(ROW, COL);
            setBackground(Color.white);
            repaint();
        });
    }
    
    // function to access saveFile() from FileManager
    public void saveCanvas(String fileName){
        fileManager.saveFile(fileName, canvas.getCharacters(), canvas.getCharacterColors(), canvas.getBackgroundColor());    
    }
    
    // Getters******************************************************************
    // function to get the saves list from fileManager
    public ArrayList<String> getSavesList(){
        fileManager.checkSaveFolder();
        return fileManager.getSaves();
    }
    
    // function to get the most recently opened canvas
    public String getCurrentCanvas(){
        return currentCanvas;
    }
    
    // function to get the background color from TextCanvasModel
    public Color getBackgroundColor(){
        return canvas.getBackgroundColor();
    }
    
    // Check if the saves list contains a certain file name
    public boolean isInSavesList(String name){
        String filename = name + ".txt";
        fileManager.checkSaveFolder();
        return fileManager.isInSaves(filename);
    }
    
    // Setters******************************************************************
    // function to set the most recently loaded save
    public void setCurrentCanvas(String name){
        currentCanvas = name;
    }
    
    // function to reset the text canvas using a TextCanvasController object
    public void resetTextCanvas(){
        canvas.resetTextCanvas(ROW, COL);
    }
    
    public void setBackgroundColor(Color color){
        canvas.setBackgroundColor(color);
    }
    // function to update the background color
    public void updateBackground(){
        setBackground(canvas.getBackgroundColor());
        repaint();
    }
    
    // Additional functions*****************************************************
    // function to load a file using a TextCanvasController object
    // gets a copy of the required arrays from fileManager
    // and updates canvas appropriately
    public void loadCanvas(String fileName){
        fileManager.checkSaveFolder();
        fileManager.loadFile(fileName);
        canvas.loadCanvas(fileManager.getNewCharacterGrid(), fileManager.getNewColorGrid(), fileManager.getNewBackground());
        repaint();
    }
    
    // Function to delete a file using a TextCanvasController object
    public void deleteCanvas(String fileName){
        fileManager.deleteFile(fileName);
    }
  
    // To my understanding, paintComponent() is a built-in, under-the-hood 
    // function that redraws a component "when it needs to".
    // I think the repaint() function utilizes it for certain things.
    // Here it is overrided to redraw the character array to the text canvas
    // with .drawString() as well as apply the correct color from the color 
    // array using .setColor() These are done simultaneously in nested for loops
    // that iterate through the entire 2d arrays
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < canvas.getCharacters().length; x++) {
            for (int y = 0; y < canvas.getCharacters()[0].length; y++) {
                g.setColor(canvas.getCharacterColors()[x][y]);
                // in drawString() the second two parameters are the x and y
                // starting coordinates on the screen. Starting from x and y = 0
                // and iterating up draws the characters across the screen 
                // in a matrix-like fashion one ROW at a time corresponding with the gridCharacters
                // array. cellSize is used to scale these coordinates so we get
                // bigger cellSize = farther apart
                g.drawString(String.valueOf(canvas.getCharacters()[x][y]), x * canvas.getCellSize(), y * canvas.getCellSize() + 8);
            }
        }
    }
}
