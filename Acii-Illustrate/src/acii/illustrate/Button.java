/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;

/**
 *
 * @author Cyle
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
public class Button extends JPanel
{
     public static JPanel createButtons(JFrame frame, TextCanvasController drawingArea)
     {
     // creates a button for changing the text colors using an action listener.
        // JColorChooser.showDialog() brings up a built-in window that lets the user 
        // select a color. This is stored in the currentColor variable
        JButton colorSwitchButton = new JButton("Color");
        drawingArea.addColorActionListener(colorSwitchButton, frame);

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
        
        // create reset button
        JButton resetButton = new JButton("Reset");
        drawingArea.addResetActionListener(resetButton, drawingArea, frame);
        
        // this stuff creates a jpanel for the buttons
        // and then adds it to the frame. 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));
        buttonPanel.add(charSwitchButton);
        buttonPanel.add(colorSwitchButton); 
        buttonPanel.add(backgroundSwitchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        
        return buttonPanel;
     }
     
     // Function used to update the popup menu for loading a
    // saved canvas. It uses the ArrayList of saved file names
    // to create menu buttons with those names and adds them to
    // the popup menu linked to the load button
    public static void updateLoadMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames){
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener( e -> {  
                    
                    // need to figure out how to set parent component to the frame here so that
                    // the dialog box is in the middle of the window
                    JOptionPane.showMessageDialog(frame, "Loading " + name.substring(0, name.length() - 4));
                    drawingArea.loadCanvas(name);
                    drawingArea.setBackground(drawingArea.getBackgroundColor());
                });
                menu.add(menuItem);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "No Saved Canvas");
        }
    }
    
    // Function used to update the delete popup menu
    // Creates menu items based on the saved file names similar
    // to updateLoadMenu
    public static void updateDeleteMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames) {
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener ( e -> {
                    JOptionPane.showMessageDialog(frame, "Deleting " + name.substring(0, name.length() - 4));
                    drawingArea.deleteCanvas(name);
                });
                menu.add(menuItem);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "No Saved Canvas");
        }
    }
    
    public static void updateSaveMenu(JPopupMenu menu, TextCanvasController drawingArea, JFrame frame){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames) {
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener ( e -> {
                    JOptionPane.showMessageDialog(frame, "Overwriting " + name.substring(0, name.length() - 4));
                    drawingArea.saveCanvas(name.substring(0, name.length() - 4));
                });
                menu.add(menuItem);
            }
        }
        JMenuItem menuItem = new JMenuItem("New Save");
        drawingArea.addSaveMenuItemActionListener(menuItem, frame, drawingArea);
        menu.add(menuItem);
    }
}
