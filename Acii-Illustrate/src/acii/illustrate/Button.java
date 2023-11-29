package acii.illustrate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class creates all of the buttons and button menus
 */
public class Button extends JPanel
{
    
    /**
     * This function creates all of the buttons and adds them to a JPanel
     * @param frame JFrame that contains all of the GUI elements
     * @param drawingArea TextCanvasController object that is added as a component to the main JFrame
     * @return buttonPanel JPanel object that contains all of the buttons
     */
     public static JPanel createButtons(JFrame frame, TextCanvasController drawingArea)
     {
        // creates a button for changing the text colors using an action listener.
        // JColorChooser.showDialog() brings up a built-in window that lets the user 
        // select a color. This is stored in the currentColor variable
        JButton colorSwitchButton = new JButton("Color");
        drawingArea.addColorActionListener(colorSwitchButton, frame);
        
        // create an eraser button with a popupmenu and menuItems
        JButton eraserButton = new JButton("Eraser");
        JPopupMenu eraserMenu = new JPopupMenu();
        JMenuItem eraserSmall = new JMenuItem("Small");
        JMenuItem eraserMedium = new JMenuItem("Medium");
        JMenuItem eraserLarge = new JMenuItem("Large");
        
        // add the menu itmes to the menu
        eraserMenu.add(eraserSmall);
        eraserMenu.add(eraserMedium);
        eraserMenu.add(eraserLarge);
        
        // add action listeners
        drawingArea.addEraserItemActionListener(eraserSmall, eraserMedium, eraserLarge);
        drawingArea.addEraserButtonActionListener(eraserMenu, eraserButton);
        
        
        // creates a button for changing the character using an action listener.
        // JOptionPane.showInputDialog() brings up a built-in window that lets the 
        // user enter a character. right now it allows any string input but only
        // the first character is stored in the currentCharacter variable
        JButton charSwitchButton = new JButton("Char");
        drawingArea.addCharActionListener(charSwitchButton, frame);
        
        // creates a button for changing the background color of the text canvas object.
        // Uses JColorChooser.showDialog() like before to store it in the backgroundColor variable.
        // setBackground() internally changes the background color of the text canvas object
        // and repaint() redraws it so it changes for the user.
        JButton backgroundSwitchButton = new JButton("Background");
        drawingArea.addBackgroundActionListener(backgroundSwitchButton, frame, drawingArea);
        
        // create save button
        // checks current saves incase the save name already exists and
        // asks the user if they want to overwrite
        JButton saveButton = new JButton("Save");
        JPopupMenu saveMenu = new JPopupMenu();
        drawingArea.addSaveButtonActionListener(saveButton, saveMenu, drawingArea, frame);
        
        // create load button with pop up menu of more buttons
        // for selecting the canvas to load.
        JButton loadButton = new JButton("Load");
        JPopupMenu loadMenu = new JPopupMenu();
        drawingArea.addLoadActionListener(loadButton, loadMenu, drawingArea, frame);

        // create delete button with pop up menu of more buttons
        // for selecting the canvas to delete
        JButton deleteButton = new JButton("Delete");
        JPopupMenu deleteMenu = new JPopupMenu();
        drawingArea.addDeleteActionListener(deleteButton, deleteMenu, drawingArea, frame);
        JButton pngButton = new JButton("Save as png");
        drawingArea.pngActionListener(pngButton, drawingArea, frame);
        
        // create reset button
        JButton resetButton = new JButton("Reset");
        drawingArea.addResetActionListener(resetButton, drawingArea, frame);
        
        // this stuff creates a jpanel for the buttons
        // and then adds it to the frame. 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 9));
        buttonPanel.add(charSwitchButton);
        buttonPanel.add(eraserButton);
        buttonPanel.add(colorSwitchButton); 
        buttonPanel.add(backgroundSwitchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(pngButton);
        
        return buttonPanel;
     }
     
    /**
     * Function used to update the popup menu for loading a
     * saved canvas. It uses the ArrayList of saved file names
     * to create menu buttons with those names and adds them to
     * the popup menu linked to the load button.
     * @param menu JPopupMenu object that functions as the menu for the load button
     * @param drawingArea TextCanvasController object that is added as a component to the main JFrame
     * @param frame JFrame that contains all of the GUI elements
     */ 
    public static void updateLoadMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        
        // creates an array list of the names of all current save files
        ArrayList<String> savedNames = drawingArea.getSavesList();
        
        // if the save names list is not empty...
        if (!savedNames.isEmpty()){
            
            // iterate through the save names list, creating a menu button for each name
            // along with a button listener to load the appropriate file
            for (String name : savedNames){
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener( e -> {  
                    JOptionPane.showMessageDialog(frame, "Loading " + name.substring(0, name.length() - 4));
                    drawingArea.loadCanvas(name);
                    drawingArea.setBackground(drawingArea.getBackgroundColor());
                    drawingArea.setCurrentCanvas(name);
                });
                menu.add(menuItem);
            }
        
        // if the save names list is empty, display "no saved canvas"
        }else{
            JOptionPane.showMessageDialog(frame, "No Saved Canvas");
        }
    }
    
    /**
     * Function used to update the delete popup menu.
     * Creates menu items based on the saved file names similar
     * to updateLoadMenu().
     * @param menu JPopupMenu object that functions as the menu for the delete button
     * @param drawingArea TextCanvasController object that is added as a component to the main JFrame
     * @param frame JFrame that contains all of the GUI elements
     */
    public static void updateDeleteMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames) {
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener ( e -> {
                    JOptionPane.showMessageDialog(frame, "Deleting " + name.substring(0, name.length() - 4));
                    drawingArea.deleteCanvas(name);
                    if (name.equals(drawingArea.getCurrentCanvas())){
                        drawingArea.resetTextCanvas();
                        drawingArea.setBackground(Color.white);
                        drawingArea.repaint();
                    }
                });
                menu.add(menuItem);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "No Saved Canvas");
        }
    }
    
    /**
     * Function used to update the save pop up menu.
     * Creates menu items based on the saved file names similar to
     * updateLoadMenu() and updateDeleteMenu().
     * @param menu JPopupMenu object that functions as the menu for the save button
     * @param drawingArea TextCanvasController object that is added as a component to the main JFrame
     * @param frame JFrame that contains all of the GUI elements
     */
    public static void updateSaveMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames) {
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener ( e -> {
                    JOptionPane.showMessageDialog(frame, "Overwriting " + name.substring(0, name.length() - 4));
                    drawingArea.saveCanvas(name.substring(0, name.length() - 4));
                    drawingArea.setCurrentCanvas(name);
                });
                menu.add(menuItem);
            }
        }
        JMenuItem menuItem = new JMenuItem("New Save");
        drawingArea.addSaveMenuItemActionListener(menuItem, frame, drawingArea);
        menu.add(menuItem);
    }
}
