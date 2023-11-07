/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ArrayList;
public class TextCanvasController extends JPanel
{
    // these variables are the size of the character and
    // color arrays and used to construct the TextCanvasModel
    private static final int ROW = 200;
    private static final int COL = 200;
    TextCanvasModel canvas = new TextCanvasModel(ROW, COL);
    FileManager fileManager = new FileManager();
    
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
                    canvas.setCharacter(xPress, yPress);
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
                    canvas.setCharacter(xDrag, yDrag);
                    canvas.setCharacterColor(xDrag, yDrag);
                    repaint();
                }
            }
        });
    }
    
    public static void addColorActionListener(JButton colorSwitchButton, JFrame frame) {
        colorSwitchButton.addActionListener(e -> {
            TextCanvasModel.currentColor = JColorChooser.showDialog(frame, "Choose a Color", TextCanvasModel.currentColor);
        });
    }
    
    public static void addCharActionListener(JButton charSwitchButton, JFrame frame) {
        charSwitchButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter a character:");
            if (input != null && !input.isEmpty()) {
                TextCanvasModel.currentCharacter = input.charAt(0);
            }
        });
    }
    
    public static void addBackgroundActionListener(JButton backgroundSwitchButton, JFrame frame, TextCanvasController drawingArea) {
        backgroundSwitchButton.addActionListener(e -> {
            TextCanvasModel.backgroundColor = (Color) JColorChooser.showDialog(frame, "Choose a Background Color", TextCanvasModel.backgroundColor);
            drawingArea.setBackground(TextCanvasModel.backgroundColor);
            drawingArea.repaint();
        });
    }
    
    public static void addSaveActionListener(JButton saveButton, JFrame frame, TextCanvasController drawingArea) {
        saveButton.addActionListener (e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter the name of your canvas:");
            if (drawingArea.isInSavesList(input)){
                int choice = JOptionPane.showConfirmDialog(frame, "The canvas already exists. Would you like to overwrite?");
                if (choice == 0){
                    drawingArea.saveCanvas(input);
                    JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                }
            } else{
                drawingArea.saveCanvas(input);
                JOptionPane.showMessageDialog(frame, "Canvas has been saved");
            }
        });
    }
    
    public static void addLoadActionListener(JButton loadButton, JPopupMenu loadMenu, TextCanvasController drawingArea) {
        loadButton.addActionListener((ActionEvent e) -> {
            loadMenu.removeAll();
            Button.updateLoadMenu(loadMenu, drawingArea);
            
            loadMenu.show(loadButton, 0, loadButton.getHeight());
        });
    }
    
    public static void addDeleteActionListener(JButton deleteButton, JPopupMenu deleteMenu, TextCanvasController drawingArea) {
        deleteButton.addActionListener((ActionEvent e) -> {
            deleteMenu.removeAll();
            Button.updateDeleteMenu(deleteMenu, drawingArea);
            
            deleteMenu.show(deleteButton, 0, deleteButton.getHeight());
        });
    }
    
    // function to access saveFile() from FileManager
    public void saveCanvas(String fileName){
        fileManager.saveFile(fileName, canvas.getCharacters(), canvas.getCharacterColors(), canvas.getBackgroundColor());    
    }
    
    // function to get the saves list from fileManager
    public ArrayList<String> getSavesList(){
        fileManager.checkSaveFolder();
        return fileManager.getSaves();
    }
    
    // function to load a file
    // gets a copy of the required arrays from fileManager
    // and updates canvas appropriately
    public void loadCanvas(String fileName){
        fileManager.checkSaveFolder();
        fileManager.loadFile(fileName);
        canvas.loadCanvas(fileManager.getNewCharacterGrid(), fileManager.getNewColorGrid(), fileManager.getNewBackground());
        System.out.println(fileManager.getNewBackground());
    }
    
    public void deleteCanvas(String fileName){
        fileManager.deleteFile(fileName);
    }
    
    public boolean isInSavesList(String name){
        String filename = name + ".txt";
        fileManager.checkSaveFolder();
        return fileManager.isInSaves(filename);
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
